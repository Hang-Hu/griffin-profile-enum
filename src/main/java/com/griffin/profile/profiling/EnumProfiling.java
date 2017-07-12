package com.griffin.profile.profiling;

import com.griffin.profile.common.FileOperate;
import com.griffin.profile.common.JsonConvert;
import com.griffin.profile.output.MultiPersist;
import com.griffin.profile.util.config.Config;
import com.griffin.profile.util.config.EvaluateRule;
import com.griffin.profile.util.config.Source;
import com.griffin.profile.util.enumProfile.EnumProfile;
import com.griffin.profile.util.enumProfile.EnvFile;
import com.griffin.profile.util.result._Source;
import com.griffin.profile.util.result._SourceReader;
import org.apache.griffin.measure.batch.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by xiangrchen on 7/5/17.
 */
public class EnumProfiling implements Profiling{
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumProfiling.class);

    String profileEnumPrefix=null;
    EnumProfile enumProfile=null;
    List<String> profileFieldValueList=new ArrayList<String>();
    MultiPersist multiPersist;

    public EnumProfiling(String[] argsHome){
        multiPersist =new MultiPersist();

        String enumProfilePath=argsHome[0];
        String fsTypes=null;
        String envFsType=null;
        String envFsPathORContent=null;

        String enumProfileStr= FileOperate.readFile(enumProfilePath);
        enumProfile= JsonConvert.toEntity(enumProfileStr,EnumProfile.class);

        profileEnumPrefix=enumProfile.getElasticSearch().getHitNamePrefix();
        profileFieldValueList=enumProfile.getEnumValue().getValueList();
        EnvFile envFile=enumProfile.getEnvFile();
        if (envFile.getType()!=null){
            envFsType=envFile.getType();
            if (envFsType.equals("local")){
                envFsPathORContent=envFile.getConfig().get("file.name");
            }else if (envFsType.equals("raw")){
                envFsPathORContent=envFile.getConfig().get("file.content");
            }
        }
        fsTypes=envFsType+",raw";       //default confFsType is "raw"
        //launching profile Application.main(args)
        for (String testItem:profileFieldValueList){
            Config config=new Config();
            setConfig(config,testItem,enumProfile.getSource());
            String configJson=JsonConvert.toJson(config);

            String[] args={envFsPathORContent,configJson,fsTypes};
            Application.main(args);
        }
    }

    public void setConfig(Config config,String testItem,Source source){
        config.setName(profileEnumPrefix+testItem);
        config.setType("profile");
        config.setSource(source);

        EvaluateRule evaluateRule=new EvaluateRule(1);
        evaluateRule.setRules("$source."+enumProfile.getEnumValue().getName()+" == '"+testItem+"'");
        config.setEvaluateRule(evaluateRule);
    }

    @Override
    public void executeProfiling(){
        //get profiling/enum response from es.
        try {
            String requestJson="{\n" +
                    "   \"query\" : {\n" +
                    "       \"match_phrase_prefix\" : {\n" +
                    "           \"name\":\""+profileEnumPrefix+"\"\n"+
                    "       }\n"+
                    "   },  \n"+
                    "   \"sort\" : [{\"tmst\": {\"order\": \"desc\"}}] \n"+
                    "}";

            String url="http://"+enumProfile.getElasticSearch().getIp()+":"+enumProfile.getElasticSearch().getPort() +enumProfile.getElasticSearch().getEndPoint();
            RestTemplate rest=new RestTemplate();
            String postResultStr=rest.postForObject(url,requestJson,String.class);
            genProfileResult(postResultStr);
        }catch (Exception e){
            LOGGER.warn(""+e);
        }
    }

    public void genProfileResult(String resultStr){
        Map<String,Double> enumProfileRes=new HashMap<String,Double>();

        List<_Source> _sourceList= _SourceReader.read(resultStr);
        long enumAllFeildMatched=0;
        long total=0;
        for (String testItem:profileFieldValueList){
            List<_Source> testItem_Sourcelist=new ArrayList<_Source>();
            for (_Source _source:_sourceList){
                if (_source.getName().startsWith(profileEnumPrefix+testItem)){
                    testItem_Sourcelist.add(_source);
                }
            }
            if(testItem_Sourcelist.size()>0){
//                Collections.sort(testItem_Sourcelist, new Comparator<_Source>() {
//                    @Override
//                    public int compare(_Source o1, _Source o2) {
//                        return (o1.getTmst()-o2.getTmst())>=0?-1:1;
//                    }
//                });
                Collections.sort(testItem_Sourcelist, (o1,o2)->(o1.getTmst()-o2.getTmst())>=0?-1:1);
                if (testItem_Sourcelist.get(0).getTotal()!=0){
                    enumProfileRes.put(testItem,(double)testItem_Sourcelist.get(0).getMatched()/(double)testItem_Sourcelist.get(0).getTotal());
                    enumAllFeildMatched=enumAllFeildMatched+testItem_Sourcelist.get(0).getMatched();
                    total=testItem_Sourcelist.get(0).getTotal();
                }
            }
        }
        if (total==0){
            enumProfileRes=null;
        }else {
            enumProfileRes.put("OTHER",(double)(total-enumAllFeildMatched)/(double)total);
        }
        multiPersist.out(enumProfile,enumProfileRes);
    }
}

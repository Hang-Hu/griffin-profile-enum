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

    String profileEnumPrefix;
    EnumProfile enumProfile;
    Set<String> profileFieldValueSet;
    MultiPersist multiPersist;

    Map<String,String> singleCheckResultName;

    public EnumProfiling(String[] argsHome){
        multiPersist =new MultiPersist();
        profileFieldValueSet=new HashSet<String>();
        singleCheckResultName=new HashMap<String,String>();

        String enumProfilePath=argsHome[0];
        String fsTypes=null;
        String envFsType=null;
        String envFsPathORContent=null;

        String enumProfileStr= FileOperate.readFile(enumProfilePath);
        enumProfile= JsonConvert.toEntity(enumProfileStr,EnumProfile.class);
        profileEnumPrefix=enumProfile.getElasticSearch().getHitNamePrefix();
        profileFieldValueSet=enumProfile.getEnumValue().getValueSet();
        if (profileFieldValueSet==null || profileFieldValueSet.size()==0){
            LOGGER.error("enumValue valueSet have nothing, please check your enumValue valueSet in enumProfile.json!");
            return;
        }
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
        for (String enumItem:profileFieldValueSet){
            if (enumItem==null){
//                System.out.println(enumItem);
            }
            Config config=new Config();
            setConfig(config,enumItem,enumProfile.getSource());
            singleCheckResultName.put(enumItem,config.getName());
            String configJson=JsonConvert.toJson(config);
            String[] args={envFsPathORContent,configJson,fsTypes};
            Application.main(args);
        }
    }

    public void setConfig(Config config,String enumItem,Source source){
        config.setName(profileEnumPrefix+"_"+System.currentTimeMillis()+"_"+enumItem);
        config.setType("profile");
        config.setSource(source);

        EvaluateRule evaluateRule=new EvaluateRule(1);
        if (enumItem==null){
            evaluateRule.setRules("$source."+enumProfile.getEnumValue().getName()+" == null");
        }else {
            evaluateRule.setRules("$source."+enumProfile.getEnumValue().getName()+" == '"+enumItem+"'");
        }
        config.setEvaluateRule(evaluateRule);
    }

    @Override
    public void executeProfiling(){
        //get profiling/enum response from es.
        Map<String,Double> enumProfileRes=new HashMap<String,Double>();
        try {
            long enumAllFeildMatched=0;
            long total=0;
            RestTemplate restTemplate=new RestTemplate();
            for (String enumItem:singleCheckResultName.keySet()){
                String requestJson1="{\n" +
                        "   \"query\" : {\n" +
                        "       \"match\" : {\n" +
                        "           \"name\":\""+singleCheckResultName.get(enumItem)+"\"\n"+
                        "       }\n"+
                        "   },  \n"+
                        "   \"sort\" : [{\"tmst\": {\"order\": \"desc\"}}] \n"+
                        "}";
                String url="http://"+enumProfile.getElasticSearch().getIp()+":"+enumProfile.getElasticSearch().getPort() +enumProfile.getElasticSearch().getEndPoint();
                String postResultStr=restTemplate.postForObject(url,requestJson1,String.class);
                List<_Source> _sourceList= _SourceReader.read(postResultStr);
                int count=5;
                while ((_sourceList==null || _sourceList.size()==0) && (count--)>0){
                    postResultStr=restTemplate.postForObject(url,requestJson1,String.class);
                    _sourceList= _SourceReader.read(postResultStr);
                    Thread.currentThread().sleep(1000);
                }
                if (_sourceList==null){
                    throw new Exception("can not get single value result of "+enumItem);
                }

                if (enumItem==null){
                    enumItem=enumItem+"(null object)";
                }
                enumProfileRes.put(enumItem,(double)_sourceList.get(0).getMatched()/(double)_sourceList.get(0).getTotal());
                enumAllFeildMatched=enumAllFeildMatched+_sourceList.get(0).getMatched();
                total=_sourceList.get(0).getTotal();
            }
            if (total==0){
                enumProfileRes=null;
            }else {
                enumProfileRes.put("OTHER",(double)(total-enumAllFeildMatched)/(double)total);
            }
        }catch (Exception e){
            LOGGER.error(""+e.getMessage());
        }
        //out
        multiPersist.out(enumProfile,enumProfileRes);
    }
}

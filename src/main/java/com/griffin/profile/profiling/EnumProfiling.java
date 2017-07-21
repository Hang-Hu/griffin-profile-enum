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
        //default confFsType is "raw"
        fsTypes=envFsType+",raw";
        //launching profile Application.main(args)
        profileFieldValueSet.add("OTHER");
        for (String enumItem:profileFieldValueSet){
            Config config=new Config();
            setConfig(config,enumItem,enumProfile.getSource(),profileFieldValueSet);
            singleCheckResultName.put(enumItem,config.getName());
            String configJson=JsonConvert.toJson(config);
            String[] args={envFsPathORContent,configJson,fsTypes};
            Application.main(args);
        }
    }

    public void setConfig(Config config,String enumItem,Source source,Set<String> profileFieldValueSet){
        config.setName(profileEnumPrefix+"_"+System.currentTimeMillis()+"_"+enumItem);
        config.setType("profile");
        config.setSource(source);

        EvaluateRule evaluateRule=new EvaluateRule(1);
        if (enumItem=="OTHER"){
            StringBuilder sb=new StringBuilder();
            for (String item:profileFieldValueSet){
                if (item!=null && item.equals("OTHER")){
                    continue;
                }else if(item==null){
                    sb.append(item);
                    sb.append(",");
                }else {
                    sb.append("\'"+item+"\'");
                    sb.append(",");
                }
            }
            LOGGER.info("$source."+enumProfile.getEnumValue().getName()+" NOT IN ("+sb.substring(0,sb.length()-1)+")");
            evaluateRule.setRules("$source."+enumProfile.getEnumValue().getName()+" NOT IN ( "+sb.substring(0,sb.length()-1)+" )");
        } else if (enumItem==null){
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
                if (enumItem==null){
                    //in order to distinguish null from 'null'
                    enumItem=enumItem+"(null object)";
                }
                if (_sourceList==null){
                    LOGGER.error("can not get single value result of "+enumItem);
                }else {
                    enumProfileRes.put(enumItem, (double) _sourceList.get(0).getMatched() / (double) _sourceList.get(0).getTotal());
                }
            }
        }catch (Exception e){
            LOGGER.error(""+e.getMessage());
        }
        //out
        multiPersist.out(enumProfile,enumProfileRes);
    }
}

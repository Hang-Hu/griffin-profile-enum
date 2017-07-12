package com.griffin.profile;


import com.griffin.profile.profiling.EnumProfiling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Edit Configuration:
 *  VM options: -Dspark.master=local[*] -Dhive.metastore.uris=thrift://localhost:9083
 */
public class ProfileApplication{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileApplication.class);
    private static EnumProfiling enumProfiling;
    /**
     * @param args contains enumProfile.json
     */
    public static void main(String[] args){
        LOGGER.info("===============begin================");
        if (args.length!=1){
            LOGGER.info("args length is not correct!");
            return;
        }
        enumProfiling=new EnumProfiling(args);
        enumProfiling.executeProfiling();
    }
}

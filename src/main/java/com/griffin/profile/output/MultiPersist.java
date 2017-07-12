package com.griffin.profile.output;

import com.griffin.profile.util.enumProfile.EnumProfile;
import com.griffin.profile.util.enumProfile.PersistItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by xiangrchen on 7/10/17.
 */
public class MultiPersist{
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPersist.class);

    public MultiPersist(){

    }

    public void out(EnumProfile enumProfile, Map<String, Double> enumProfileRes){
        for (PersistItem persistItem:enumProfile.getPersist()){
            Persist persist=null;
            if (persistItem.getType().equals("log")){
                persist=new LoggerPersist();
            }else if (persistItem.getType().equals("hdfs")){
                persist=new HdfsPersist();
            }else {
                LOGGER.warn("not supported persist type");
                return;
            }
            persist.out(persistItem.getConfig(),enumProfile,enumProfileRes);
        }
    }
}

package com.griffin.profile.output;

import com.griffin.profile.util.enumProfile.EnumProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by xiangrchen on 7/10/17.
 */
public class LoggerPersist implements Persist {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerPersist.class);

    @Override
    public void out(Map<String, String> config,EnumProfile enumProfile, Map<String, Double> enumProfileRes) {
        LOGGER.info("===========================================================");
        LOGGER.info(enumProfile.getEnumValue().getName()+" enum profile result:");
        if (enumProfileRes==null){
            LOGGER.info("source table is empty");
        }else {
            LOGGER.info("" + enumProfileRes);
        }
        LOGGER.info("===========================================================");
    }
}

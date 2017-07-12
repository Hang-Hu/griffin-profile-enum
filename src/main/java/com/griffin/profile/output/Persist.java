package com.griffin.profile.output;

import com.griffin.profile.util.enumProfile.EnumProfile;

import java.util.Map;

/**
 * Created by xiangrchen on 7/10/17.
 */
public interface Persist {
    void out(Map<String,String> config,EnumProfile enumProfile, Map<String, Double> enumProfileRes);
}

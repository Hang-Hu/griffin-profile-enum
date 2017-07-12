package com.griffin.profile.util.enumProfile;

import java.util.Map;

/**
 * Created by xiangrchen on 7/6/17.
 */
public class EnvFile {
    String type;
    Map<String,String> config;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public EnvFile() {
    }

    public EnvFile(String type, Map<String, String> config) {
        this.type = type;
        this.config = config;
    }
}
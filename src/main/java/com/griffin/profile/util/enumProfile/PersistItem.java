package com.griffin.profile.util.enumProfile;

import java.util.Map;

/**
 * Created by xiangrchen on 7/10/17.
 */
public class PersistItem {
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

    public PersistItem() {
    }

    public PersistItem(String type, Map<String, String> config) {
        this.type = type;
        this.config = config;
    }
}

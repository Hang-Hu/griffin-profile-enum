package com.griffin.profile.util.config;

import java.util.Map;

/**
 * Created by xiangrchen on 6/28/17.
 */
public class Source {
    String type;
    String version;
    Map<String,String> config;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public Source(String type, String version, Map<String, String> config) {
        this.type = type;
        this.version = version;
        this.config = config;
    }

    @Override
    public String toString() {
        return "Source{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", config=" + config +
                '}';
    }
}

/**
 *
 "source": {
 "type": "avro",
 "version": "1.7",
 "config": {
 "file.name": "src/test/resources/test_src.avro"
 }
 },
 */
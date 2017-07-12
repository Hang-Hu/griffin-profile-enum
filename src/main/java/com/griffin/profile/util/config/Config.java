package com.griffin.profile.util.config;

/**
 * Created by xiangrchen on 6/28/17.
 */
public class Config {
    String name;
    String type;
    Source source;
    EvaluateRule evaluateRule;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public EvaluateRule getEvaluateRule() {
        return evaluateRule;
    }

    public void setEvaluateRule(EvaluateRule evaluateRule) {
        this.evaluateRule = evaluateRule;
    }

    public Config() {
    }

    public Config(String name, String type, Source source, EvaluateRule evaluateRule) {
        this.name = name;
        this.type = type;
        this.source = source;
        this.evaluateRule = evaluateRule;
    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", source=" + source +
                ", evaluateRule=" + evaluateRule +
                '}';
    }
}

/**
 * "name": "profile-enum",
 "type": "profile",

 "source": {
 "type": "avro",
 "version": "1.7",
 "config": {
 "file.name": "src/test/resources/test_src.avro"
 }
 },

 "evaluateRule": {
 "sampleRatio": 1,
 "rules": "$source.post_code == 'c101'"
 }
 */
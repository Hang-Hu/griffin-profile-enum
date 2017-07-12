package com.griffin.profile.util.config;

/**
 * Created by xiangrchen on 6/28/17.
 */
public class EvaluateRule {
    int sampleRatio;
    String rules;

    public int getSampleRatio() {
        return sampleRatio;
    }

    public void setSampleRatio(int sampleRatio) {
        this.sampleRatio = sampleRatio;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public EvaluateRule(int sampleRatio) {
        this.sampleRatio = sampleRatio;
    }

    public EvaluateRule(int sampleRatio, String rules) {
        this.sampleRatio = sampleRatio;
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "EvaluateRule{" +
                "sampleRatio=" + sampleRatio +
                ", rules='" + rules + '\'' +
                '}';
    }
}

/**
 *
 "evaluateRule": {
 "sampleRatio": 1,
 "rules": "$source.post_code == 'c101'"
 }
 */

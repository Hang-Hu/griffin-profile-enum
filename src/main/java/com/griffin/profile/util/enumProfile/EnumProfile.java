package com.griffin.profile.util.enumProfile;

import com.griffin.profile.util.config.Source;

import java.util.List;

/**
 * Created by xiangrchen on 7/3/17.
 */
public class EnumProfile {
    Source source;
    EnumValue enumValue;
    ElasticSearch elasticSearch;
    EnvFile envFile;
    List<PersistItem> persist;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public EnumValue getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(EnumValue enumValue) {
        this.enumValue = enumValue;
    }

    public ElasticSearch getElasticSearch() {
        return elasticSearch;
    }

    public void setElasticSearch(ElasticSearch elasticSearch) {
        this.elasticSearch = elasticSearch;
    }

    public EnvFile getEnvFile() {
        return envFile;
    }

    public void setEnvFile(EnvFile envFile) {
        this.envFile = envFile;
    }

    public List<PersistItem> getPersist() {
        return persist;
    }

    public void setPersist(List<PersistItem> persist) {
        this.persist = persist;
    }

    public EnumProfile() {
    }

    public EnumProfile(Source source, EnumValue enumValue, ElasticSearch elasticSearch, EnvFile envFile, List<PersistItem> persist) {
        this.source = source;
        this.enumValue = enumValue;
        this.elasticSearch = elasticSearch;
        this.envFile = envFile;
        this.persist = persist;
    }
}
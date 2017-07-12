package com.griffin.profile.util.enumProfile;

/**
 * Created by xiangrchen on 7/10/17.
 */
public class ElasticSearch {
    String ip;
    int port;
    String endPoint;
    String hitNamePrefix;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getHitNamePrefix() {
        return hitNamePrefix;
    }

    public void setHitNamePrefix(String hitNamePrefix) {
        this.hitNamePrefix = hitNamePrefix;
    }

    public ElasticSearch() {
    }

    public ElasticSearch(String ip, int port, String endPoint, String hitNamePrefix) {
        this.ip = ip;
        this.port = port;
        this.endPoint = endPoint;
        this.hitNamePrefix = hitNamePrefix;
    }
}

/***
 "elasticSearch":{
 "ip":"10.149.247.156",
 "port":39200,
 "endPoint":"/griffin/accuracy/_search?pretty&filter_path=hits.hits._source&size=100",
 "hitNamePrefix":"profileEnum"
 },
 */
package com.griffin.profile.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Created by xiangrchen on 6/15/17.
 */
public class JsonConvert {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConvert.class);
//    private static final JsonConvert jsonConvert = new JsonConvert();

//    public static JsonConvert getJsonConvertInstance(){
//        return jsonConvert;
//    }


    public static String toJson(Object obj){
        ObjectMapper mapper=new ObjectMapper();
        String jsonStr=null;
        try {
            jsonStr=mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("", e);
        }
        return jsonStr;
    }

    public static <T>T toEntity(String jsonStr,Class<T> type) {
        if (jsonStr==null || jsonStr.length()==0){
            LOGGER.warn("jsonStr "+type+" is empty!");
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, type);
    }

    public static <T>T toEntity(String jsonStr,Type type) {
        if (jsonStr==null || jsonStr.length()==0){
            LOGGER.warn("jsonStr "+type+" is empty!");
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, type);
    }
}

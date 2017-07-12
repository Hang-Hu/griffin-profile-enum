package com.griffin.profile.util.result;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.griffin.profile.common.JsonConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangrchen on 7/6/17.
 */
public class _SourceReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(_SourceReader.class);
    public static List<_Source> read(String esResultStr){
        List<_Source> _SourceList=new ArrayList<_Source>();

        Type mapType1=new TypeToken<Map<String,JsonObject>>(){}.getType();
        Map<String,JsonObject> hitsMap1=JsonConvert.toEntity(esResultStr,mapType1);
//        LOGGER.info(""+hitsMap1);

        Type mapType2=new TypeToken<Map<String,JsonArray>>(){}.getType();
        Map<String,JsonArray> hitsMap2= JsonConvert.toEntity(hitsMap1.get("hits").toString(),mapType2);

        Type listType=new TypeToken<List<JsonObject>>(){}.getType();
        List<JsonObject> hitList=JsonConvert.toEntity(hitsMap2.get("hits").toString(),listType);

        for (JsonObject jsonObject:hitList){
            Type mapType3=new TypeToken<Map<String,JsonObject>>(){}.getType();
            Map<String,JsonObject> _sourceMap=JsonConvert.toEntity(jsonObject.toString(),mapType3);

            Type mapType4=new TypeToken<Map<String,String>>(){}.getType();
            Map<String,String> map=JsonConvert.toEntity(_sourceMap.get("_source").toString(),mapType4);

            _Source _source=new _Source();
            _source.setName(map.get("name"));
            _source.setTmst(Long.parseLong(map.get("tmst")));
            _source.setTotal(Long.parseLong(map.get("total")));
            _source.setMatched(Long.parseLong(map.get("matched")));

            _SourceList.add(_source);
        }
        return _SourceList;
    }
}

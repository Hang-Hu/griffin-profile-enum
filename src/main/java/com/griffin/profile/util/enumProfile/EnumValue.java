package com.griffin.profile.util.enumProfile;

import java.util.List;

/**
 * Created by xiangrchen on 7/3/17.
 */
public class EnumValue {
    String name;
    List<String> valueList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public EnumValue() {
    }

    public EnumValue(String name, List<String> valueList) {
        this.name = name;
        this.valueList = valueList;
    }
}
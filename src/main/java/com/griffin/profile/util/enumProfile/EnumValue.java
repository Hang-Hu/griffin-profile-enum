package com.griffin.profile.util.enumProfile;

import java.util.Set;

/**
 * Created by xiangrchen on 7/3/17.
 */
public class EnumValue {
    String name;
    Set<String> valueSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getValueSet() {
        return valueSet;
    }

    public void setValueSet(Set<String> valueSet) {
        this.valueSet = valueSet;
    }

    public EnumValue() {
    }

    public EnumValue(String name, Set<String> valueSet) {
        this.name = name;
        this.valueSet = valueSet;
    }
}
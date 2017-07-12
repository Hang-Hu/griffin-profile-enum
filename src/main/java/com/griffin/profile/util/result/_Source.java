package com.griffin.profile.util.result;

/**
 * Created by xiangrchen on 6/29/17.
 */
public class _Source {
    String name;
    long tmst;
    long total;
    long matched;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTmst() {
        return tmst;
    }

    public void setTmst(long tmst) {
        this.tmst = tmst;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getMatched() {
        return matched;
    }

    public void setMatched(long matched) {
        this.matched = matched;
    }
    public _Source(){}

    public _Source(String name, long tmst, long total, long matched) {
        this.name = name;
        this.tmst = tmst;
        this.total = total;
        this.matched = matched;
    }

    @Override
    public String toString() {
        return "_Source{" +
                "name='" + name + '\'' +
                ", tmst=" + tmst +
                ", total=" + total +
                ", matched=" + matched +
                '}';
    }
}

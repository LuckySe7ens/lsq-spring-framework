package com.lsq.context.bean;

/**
 * Created by liushiquan on 2019/1/7.
 */
public class DefaultEnvironment extends AbstractEnvironment {
    public String getString(String key) {
        return pro.getProperty(key);
    }
}

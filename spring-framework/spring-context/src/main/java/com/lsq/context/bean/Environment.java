package com.lsq.context.bean;

/**
 * Created by liushiquan on 2019/1/4.
 */
public interface Environment {
    /**
     * 初始化环境变量
     */
    void initEnvironment(String appProPath);

    String getString(String key);
}

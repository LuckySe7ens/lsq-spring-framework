package com.lsq.context.bean;

/**
 * Created by liushiquan on 2019/1/4.
 */
public interface ApplicationContext {

    String DEFAULT_APP_PROPERTIES_FILENAME = "application.properties";

    Object getBean();
}

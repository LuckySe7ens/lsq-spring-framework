package com.lsq.context.bean;

import java.util.List;

/**
 * Created by liushiquan on 2019/1/4.
 */
public interface ApplicationContext {

    Object getBean(String beanName);

    Object getBean(Class<?> clazz);

    List<Object> getBeans(Class<?> clazz);
}

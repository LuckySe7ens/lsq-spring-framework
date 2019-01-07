package com.lsq.test;

import com.lsq.context.bean.ApplicationContext;
import com.lsq.context.bean.DefaultApplicationContext;

/**
 * Created by Administrator on 2019/1/7.
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = DefaultApplicationContext.startUp();
        TestController bean = (TestController)appContext.getBean(TestController.class);
        bean.test();
    }
}

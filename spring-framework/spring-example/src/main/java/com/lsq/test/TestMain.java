package com.lsq.test;

import com.lsq.context.bean.ApplicationContext;
import com.lsq.context.bean.DefaultApplicationContext;
import com.lsq.test.controller.TestController;

/**
 * Created by Administrator on 2019/1/7.
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = DefaultApplicationContext.startUp();
        TestController bean = (TestController)context.getBean(TestController.class);
        bean.test();
    }
}

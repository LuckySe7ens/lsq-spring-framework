package com.lsq.test;

import com.lsq.context.annotation.Autowired;
import com.lsq.context.annotation.Controller;

/**
 * Created by liushiquan on 2019/1/7.
 */
@Controller
public class TestController {

    @Autowired
    private TestService service;

    public void test(){
        service.sayHello();
    }
}

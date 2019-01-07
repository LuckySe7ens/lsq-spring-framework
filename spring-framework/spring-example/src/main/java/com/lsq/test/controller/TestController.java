package com.lsq.test.controller;

import com.lsq.context.annotation.Autowired;
import com.lsq.context.annotation.Controller;
import com.lsq.test.service.TestService;

/**
 * Created by liushiquan on 2019/1/7.
 */
@Controller
public class TestController {

    @Autowired
    private TestService service;

    public void test(){
        service.test();
    }
}

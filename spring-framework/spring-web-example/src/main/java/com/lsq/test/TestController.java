package com.lsq.test;

import com.lsq.context.annotation.Autowired;
import com.lsq.context.annotation.Controller;
import com.lsq.web.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {
	@Autowired
	private com.lsq.test.TestServices service;
	
	@RequestMapping("hello")
	public String test(){
		return service.test();
	}
}

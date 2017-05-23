package com.alibaba.dubbo.demo.provider;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class DemoController {

	@RequestMapping("say")
	@ResponseBody
	public String sayHello(String name) {
		return "Hello " + name;
	}

}

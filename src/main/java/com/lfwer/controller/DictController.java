package com.lfwer.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lfwer.model.Dict;
import com.lfwer.service.DictService;

@Controller
@RequestMapping("dict")
public class DictController {

	@Autowired
	private DictService dictService;

	@RequestMapping("getDicts")
	@ResponseBody
	public List<Dict> getDicts(@RequestParam("type") String type, @RequestParam("pid") String pid) {
		return dictService.getDicts(type, pid);
	}

	@RequestMapping("getTime")
	@ResponseBody
	public JSONPObject getTime(String callback) {
		return new JSONPObject(callback, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}

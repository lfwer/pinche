package com.lfwer.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lfwer.model.Dict;
import com.lfwer.service.DictService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("dict")
@Api(value = "字典管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictController {

	@Autowired
	private DictService dictService;

	@RequestMapping("getDicts")
	@ResponseBody
	@ApiOperation(value = "获取数据字典列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Dict> getDicts(@RequestParam("type") String type, @RequestParam("pid") String pid) {
		return dictService.getDicts(type, pid);
	}

	@RequestMapping("getTime")
	@ResponseBody
	@ApiOperation(value = "获取服务器时间", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTime(String callback) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 输出标签数据，添加到静态页中
	 * 
	 * @param type
	 *            字典类型，参考dict表type
	 * @param pid
	 *            父id
	 * @param tagType
	 *            标签类型，支持select、raido、checkbox
	 * @param name
	 *            标签name值
	 * @return
	 */
	@RequestMapping(value = "getTagData/{type}/{pid}/{tagType}/{name}", produces = "application/json; charset=utf-8")
	@ResponseBody
	@ApiOperation(value = "输出标签数据", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTagData(@PathVariable String type, @PathVariable String pid, @PathVariable String tagType,
			@PathVariable String name) {
		List<Dict> list = getDicts(type, pid);
		StringBuffer buffer = new StringBuffer();
		for (Dict dict : list) {
			switch (tagType) {
			case "select":
				buffer.append("<option value=\"").append(dict.getId()).append("\">").append(dict.getName())
						.append("</option>");
				break;
			case "radio":
				buffer.append("<input name=\"").append(name).append("\" type=\"radio\" value=\"").append(dict.getId())
						.append("\" />").append(dict.getName());
				break;
			case "checkbox":
				buffer.append("<input name=\"").append(name).append("\" type=\"checkbox\" value=\"")
						.append(dict.getId()).append("\" />").append(dict.getName());
				break;
			case "button":
				buffer.append("<button type=\"button\" name=\"").append(name).append("\" class=\"btn btn-default\" value=\"")
				.append(dict.getId()).append("\">").append(dict.getName()).append("</button>");
				break;
			default:
				buffer.append("<option value=\"").append(dict.getId()).append("\">").append(dict.getName())
						.append("</option>");
				break;
			}
		}
		return buffer.toString();
	}

}

package com.lfwer.service;

import java.util.List;

import com.lfwer.model.Dict;

public interface DictService {


	public List<Dict> getDicts(String type, String pid);
	
	public Dict getDict(String type,String id);
	
	public String getName(String type,String id);
 }

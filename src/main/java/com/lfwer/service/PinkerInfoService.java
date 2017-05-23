package com.lfwer.service;

import java.util.List;

import com.lfwer.model.PinkerInfo;
import com.lfwer.model.User;

public interface PinkerInfoService {
	
	public PinkerInfo getPinkerInfo(Integer id);
	
	public void savePinkerInfo(PinkerInfo result);
	
	public void updateLookCount(Integer id);
	
	public void removePinkerInfo(Integer id,User user);
	
	@SuppressWarnings("rawtypes")
	public List getPageInfo(PinkerInfo result,Integer page,String date);
 }

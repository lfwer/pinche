package com.lfwer.service;

import java.util.List;
import com.lfwer.model.CarOwnerInfo;
import com.lfwer.model.User;

public interface CarOwnerInfoService {
	
	public CarOwnerInfo getCarOwnerInfo(Integer id);
	
	public void saveCarOwnerInfo(CarOwnerInfo result);
	
	public Integer updateLookCount(Integer id);
	
	public void removeCarOwnerInfo(Integer id,User user);
	
	public List<Object[]> getPageInfo(CarOwnerInfo result, Integer page,String date);
 }

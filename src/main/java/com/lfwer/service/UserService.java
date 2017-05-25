package com.lfwer.service;

import com.lfwer.model.User;

public interface UserService {

	public User getUser(Integer id);
	
	public void updateByHql(String hql,Object[] param);
	 
 }

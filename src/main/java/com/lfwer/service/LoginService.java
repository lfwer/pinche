package com.lfwer.service;

import java.util.List;

import com.lfwer.common.Valid;
import com.lfwer.model.User;
import com.lfwer.sys.BaseService;

public interface LoginService extends BaseService {

	public Valid genSMS(String phone);

	public boolean validatePhone(String phone);

	public boolean validateUsername(String username);

	public boolean validateNickName(String nickName, Integer id);

	public boolean validateSMS(String phone, String smsCode);

	public Integer saveUser(User user);

	public void updateUser(User user);

	public boolean validatePassword(String username, String password);

	public List<User> find(String hql, Object[] param);

	public void retrievePwd(String phone, String password);
	
	public void updateUserPhoto(Integer id,String photoSmall,String photoLarge);
	
	public void updateCardPhoto(Integer id,String smallFilename,String largeFilename,int type);
	
	public void updateDrivingBookPhoto(Integer id,String smallFilename,String largeFilename);
}

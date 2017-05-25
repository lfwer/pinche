package com.lfwer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lfwer.model.User;
import com.lfwer.service.UserService;
import com.lfwer.sys.BaseDao;
import com.lfwer.sys.BaseServiceImpl;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	@Autowired
	private BaseDao<User> userDao;

	public User getUser(Integer id) {
		return userDao.get(User.class, id);
	}

	public void updateByHql(String hql, Object[] param) {
		if (param == null) {
			userDao.executeHql(hql);
		} else {
			userDao.executeHql(hql, param);
		}
	}

}

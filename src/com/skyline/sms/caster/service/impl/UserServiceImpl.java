package com.skyline.sms.caster.service.impl;

import java.util.List;

import com.skyline.sms.caster.dao.UserDao;
import com.skyline.sms.caster.dao.impl.UserDaoImpl;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserServiceImpl(){
		userDao = new UserDaoImpl();
	}

	@Override
	public List<TUser> findUser(TUser user) throws Exception {
		return userDao.findUser(user);
	}

}

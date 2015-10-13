package com.skyline.sms.caster.service.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

import com.skyline.sms.caster.dao.Page;
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
	public List<TUser> findUsers(TUser user, Page page) throws Exception {
		return userDao.findByDetachedCriteria(DetachedCriteria.forClass(TUser.class)
				//.createCriteria("TGroups", JoinType.LEFT_OUTER_JOIN)
				.setFetchMode("TGroups", FetchMode.JOIN), page);
	}

	@Override
	public void saveOrUpdateUsers(List<TUser> users) throws Exception {
		userDao.batchUpdate(users);
		
	}
	
	

}

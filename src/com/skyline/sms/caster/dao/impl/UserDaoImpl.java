package com.skyline.sms.caster.dao.impl;

import java.util.List;

import com.skyline.sms.caster.dao.HibernateDao;
import com.skyline.sms.caster.dao.UserDao;
import com.skyline.sms.caster.pojo.TUser;

public class UserDaoImpl extends HibernateDao<TUser> implements UserDao {

	@Override
	public List<TUser> findUser(TUser user) throws Exception {
		return findAll();
	}

	@Override
	public void saveOrUpdateUsers(List<TUser> users) throws Exception {
		batchUpdate(users);
	}

	
}

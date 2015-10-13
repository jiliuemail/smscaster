package com.skyline.sms.caster.dao;

import java.util.List;

import com.skyline.sms.caster.pojo.TUser;

public interface UserDao {
	
	public List<TUser> findUser(TUser user) throws Exception;
	
	public void saveOrUpdateUsers(List<TUser> users) throws Exception;
}

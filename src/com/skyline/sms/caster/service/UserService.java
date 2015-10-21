package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TUser;

public interface UserService {
	
	List<TUser> findUsers(TUser user, Page page) throws Exception;
	
	List<TUser> findUsersByName(TUser user, Page page) throws Exception;
	
	TUser findUsersById(TUser user) throws Exception;
	
	void saveOrUpdateUsers(List<TUser> users) throws Exception;
	
	void deleteUser(TUser user) throws Exception;

}

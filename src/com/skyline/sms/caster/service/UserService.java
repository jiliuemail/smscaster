package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TUser;

public interface UserService {
	
	List<TUser> findUsers(TUser user, Page page) throws Exception;
	
	void saveOrUpdateUsers(List<TUser> users) throws Exception;

}

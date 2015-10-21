package com.skyline.sms.caster.ui.data.storer;

import java.util.List;

import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.data.UIDataStorer;
import com.skyline.sms.caster.util.DialogUtil;

public class UserDataStorer extends UIDataStorer<TUser>{
	
	private UserService userService = new UserServiceImpl();
	
	public UserDataStorer() {
		super(DialogUtil.getMainFrame());
	}
	
	@Override
	protected void submitUpdatedData(List<TUser> updatedData) throws Exception {
		userService.saveOrUpdateUsers(updatedData);
	}
	
	@Override
	protected void submitDeletedData(TUser deletedData) throws Exception {
		userService.deleteUser(deletedData);
	}
}
package com.skyline.sms.caster.ui.data.storer;

import java.util.List;

import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.service.GroupService;
import com.skyline.sms.caster.service.impl.GroupServiceImpl;
import com.skyline.sms.caster.ui.data.UIDataStorer;
import com.skyline.sms.caster.util.DialogUtil;

public class GroupDataStorer extends UIDataStorer<TGroup>{
	
	private GroupService groupService = new GroupServiceImpl();
	
	public GroupDataStorer() {
		super(DialogUtil.getMainFrame());
	}
	
	@Override
	protected void submitUpdatedData(List<TGroup> updatedData) throws Exception {
		groupService.saveOrUpdateGroups(updatedData);
	}
	
	@Override
	protected void submitDeletedData(TGroup deletedData) throws Exception {
		groupService.deleteGroup(deletedData);
	}
}

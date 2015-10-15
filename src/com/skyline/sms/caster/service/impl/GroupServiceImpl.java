package com.skyline.sms.caster.service.impl;

import java.util.List;

import com.skyline.sms.caster.dao.GroupDao;
import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.dao.impl.GroupDaoImpl;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.service.GroupService;

public class GroupServiceImpl implements GroupService {
	
	private GroupDao groupDao = new GroupDaoImpl();

	@Override
	public List<TGroup> findGroups(TGroup group, Page page) {
		return groupDao.findByEntity(group, page);
	}

}

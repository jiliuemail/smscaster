package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TGroup;

public interface GroupService {
	
	public List<TGroup> findGroups(TGroup group, Page page);
	
	public void saveOrUpdateGroups(List<TGroup> groups);

}

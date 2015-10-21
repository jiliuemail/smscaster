package com.skyline.sms.caster.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.skyline.sms.caster.dao.GroupDao;
import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.dao.impl.GroupDaoImpl;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.service.GroupService;

public class GroupServiceImpl implements GroupService {
	
	private GroupDao groupDao = new GroupDaoImpl();

	@Override
	public List<TGroup> findGroups(TGroup group, Page page) {
		return groupDao.findByDetachedCriteria(
				DetachedCriteria.forClass(TGroup.class)
				.add(Example.create(group).enableLike(MatchMode.ANYWHERE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				, page);
	}

	@Override
	public TGroup findGroupById(TGroup group) {
		if (group == null || group.getId() == null) {
			return group;
		}
		return groupDao.findById(group.getId(), "TUsers");
	}
	
	@Override
	public void saveOrUpdateGroups(List<TGroup> groups) {
		groupDao.batchUpdate(groups);
	}
	
	@Override
	public void deleteGroup(TGroup group) {
		if (group == null || group.getId() == null) {
			return;
		}
		groupDao.deleteById(group.getId());;
	}
}

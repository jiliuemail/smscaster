package com.skyline.sms.caster.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

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
				.createCriteria("TUsers", JoinType.LEFT_OUTER_JOIN)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				, page);
	}

	
	@Override
	public void saveOrUpdateGroups(List<TGroup> groups) {
		groupDao.batchUpdate(groups);
	}
}

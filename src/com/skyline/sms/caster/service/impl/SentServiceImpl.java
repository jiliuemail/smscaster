package com.skyline.sms.caster.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.dao.SentDao;
import com.skyline.sms.caster.dao.impl.SentDaoImpl;
import com.skyline.sms.caster.pojo.TMessageSent;
import com.skyline.sms.caster.service.SentService;

public class SentServiceImpl implements SentService{
	
	private SentDao sentDao = new SentDaoImpl();
	
	@Override
	public TMessageSent findMessageSentsById(TMessageSent messageSent) {
		if (messageSent == null || messageSent.getId() == null) {
			return null;
		}
		return sentDao.findById(messageSent.getId());
	}

	@Override
	public List<TMessageSent> findMessageSents(TMessageSent messageSent, Page page) {
		return sentDao.findByDetachedCriteria(
				DetachedCriteria.forClass(TMessageSent.class)
				.add(Example.create(messageSent).enableLike(MatchMode.ANYWHERE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				, page);
	}
}

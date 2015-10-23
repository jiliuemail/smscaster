package com.skyline.sms.caster.service;

import java.util.List;

import org.hibernate.hql.ast.origin.hql.parse.HQLParser.additiveExpression_return;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TMessageSent;

public interface SentService {
	
	public TMessageSent findMessageSentsById(TMessageSent messageSent);

	public List<TMessageSent> findMessageSents(TMessageSent messageSent, Page page);
	
	public void add(TMessageSent msg);
}

package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TMessageSent;

public interface SentService {
	
	public TMessageSent findMessageSentsById(TMessageSent messageSent);

	public List<TMessageSent> findMessageSents(TMessageSent messageSent, Page page);
}

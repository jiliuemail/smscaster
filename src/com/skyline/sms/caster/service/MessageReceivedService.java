package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.pojo.TMessageReceive;

public interface MessageReceivedService {

	public void saveOrUpdate(List<TMessageReceive> msgList) throws Exception;
	
	
	public void add(TMessageReceive msg);
}

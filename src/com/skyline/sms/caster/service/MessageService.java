package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.pojo.TMessage;

public interface MessageService {
	
	public void saveOrUpdate(List<TMessage> msgList) throws Exception;
	
	public List<TMessage> getByPage();
	
	public void delById(int id) throws Exception;
}

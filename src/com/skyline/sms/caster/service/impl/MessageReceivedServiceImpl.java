package com.skyline.sms.caster.service.impl;

import java.util.List;

import com.skyline.sms.caster.dao.MessageReceivedDao;
import com.skyline.sms.caster.dao.impl.MessageReceivedDaoImpl;
import com.skyline.sms.caster.pojo.TMessageReceive;
import com.skyline.sms.caster.service.MessageReceivedService;

public class MessageReceivedServiceImpl implements MessageReceivedService {
	
	private MessageReceivedDao msgReceivedDao;


	public MessageReceivedServiceImpl() {
		super();
		this.msgReceivedDao = new MessageReceivedDaoImpl();
	}




	@Override
	public void saveOrUpdate(List<TMessageReceive> msgList) throws Exception {
		// TODO Auto-generated method stub
		msgReceivedDao.batchUpdate(msgList);
		
	}


	@Override
	public void add(TMessageReceive msg) {
		// TODO Auto-generated method stub
		msgReceivedDao.insert(msg);
	}

}

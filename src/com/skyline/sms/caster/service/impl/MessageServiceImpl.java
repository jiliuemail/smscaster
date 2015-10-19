package com.skyline.sms.caster.service.impl;

import java.util.List;

import com.skyline.sms.caster.dao.MessageDao;
import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.dao.impl.MessageDaoImpl;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.MessageService;

public class MessageServiceImpl implements MessageService {

	private MessageDao msgDao;
	private Page page;
	
	public MessageServiceImpl() {
		super();
		msgDao=new MessageDaoImpl();
		page = new Page();
	}

	@Override
	public void saveOrUpdate(List<TMessage> msgList) throws Exception {
		// TODO Auto-generated method stub
		msgDao.batchUpdate(msgList);
	}

	@Override
	public List<TMessage> getByPage() {
		// TODO Auto-generated method stub
		
		return msgDao.findByPage(page);
	}

	@Override
	public void delById(int id) throws Exception {
		// TODO Auto-generated method stub
		msgDao.deleteById(id);
		
	}

}

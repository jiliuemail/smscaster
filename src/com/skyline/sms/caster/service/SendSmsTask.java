package com.skyline.sms.caster.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.connector.sendSmsExecutor;
import com.skyline.sms.pojo.Message;


public class SendSmsTask {
	
	private List<Message> messages; //短信队列;
	private Set<Port> ports; //发送消息的端口
	private List<Message> sentMessages; //已发短信
	

	
	public SendSmsTask(List<Message> messages, Set<Port> ports	) {
		this.messages = Collections.synchronizedList(messages);
		this.ports = ports;
		this.sentMessages=Collections.synchronizedList(new ArrayList<Message>());  //新建一空的list 来保存已发短信
	}

	
	public void start(){
		
		for(Port port :ports){
			new Thread(new sendSmsExecutor(port,messages)).start();
		}
	}
	
}

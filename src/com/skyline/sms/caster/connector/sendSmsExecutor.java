package com.skyline.sms.caster.connector;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skyline.sms.caster.util.StringUtil;
import com.skyline.sms.pojo.Message;

public class sendSmsExecutor implements Runnable{
	private static Logger logger =LoggerFactory.getLogger(sendSmsExecutor.class);
	
	private Port port;  // 没法做到面对接口编程,疑问ReadObserver 的原因
	private List<Message> messages;

	

	public sendSmsExecutor(Port port, List<Message> messages) {
		this.port = port;
		this.messages = messages;

	}


	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("sendSms from [{}] start",port.getPortName());
		for(Message message:messages){

			try {
				
				port.writeString("AT+CMGS=\""+StringUtil.toUnicode(message.getContacter().getPhoneNumber())+"\"\n" );

				Thread.sleep(1500);
				System.out.println("what is the response from observer");
				if(port.getResponse().contains(">")){
					System.out.println("inputing the sms content");
					port.writeString(StringUtil.toUnicode(message.getContent().getContent()));
					port.writeInt(0x1A);
				}else{
					System.out.println("esc");
					port.writeInt(0x1B);
					}
				
				if(port.getResponse().contains("+CMGS:")){
					System.out.println("发送成功");
				}
				
			/*	if(port.writeString(message.getSentMessage())){  //要发送成功才能移除这条信息.
					messages.remove(message);

				}*/
				

			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				//需要通知主线程,或者上一层....,how ?非运行时异常可以如何通知呢?throw runtimeException ?
			}finally{
				try {
					port.writeInt(0x1B);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		

		System.out.println("task finish");
	}






}

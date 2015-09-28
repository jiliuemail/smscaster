package com.skyline.sms.caster.connector;

import java.util.List;
import java.util.Set;

import com.skyline.sms.pojo.Message;

public class sendSmsExecutor implements Runnable{

	private Port port;
	private List<Message> messages;
	


	public sendSmsExecutor(Port port, List<Message> messages) {
		this.port = port;
		this.messages = messages;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		for(Message message:messages){
			
			try {
				
				
				if(port.writeString(message.getSentMessage())){  //要发送成功才能移除这条信息.
					messages.remove(message);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				//需要通知主线程,或者上一层....,how ?非运行时异常可以如何通知呢?throw runtimeException ?
			}

		}
		
		
		
	}

}

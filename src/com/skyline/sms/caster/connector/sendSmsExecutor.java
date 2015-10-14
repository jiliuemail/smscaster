package com.skyline.sms.caster.connector;

import java.util.List;

import com.skyline.sms.caster.util.LogUtil;
import com.skyline.sms.caster.util.StringUtil;
import com.skyline.sms.pojo.Message;

public class sendSmsExecutor implements Runnable{

	
	private Port port;  // 没法做到面对接口编程,疑问ReadObserver 的原因
	private List<Message> messages;

	

	public sendSmsExecutor(Port port, List<Message> messages) {
		this.port = port;
		this.messages = messages;

	}


	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	//	LogUtil.info("sendSms from [{}] start",port.getPortName());
		for(Message message:messages){

			try {
				String sms="AT+CMGS=\""+StringUtil.toUnicode(message.getContacter().getPhoneNumber())+"\"\n";
				port.writeBytes(sms.getBytes());
				//port.writeString( "AT+CMGS=\""+StringUtil.toUnicode(message.getContacter().getPhoneNumber())+"\"\n");

				Thread.sleep(1500);
				System.out.println("what is the response from observer");
				if(port.getResponse().contains(">")){
					System.out.println("inputing the sms content");
					port.writeBytes(StringUtil.toUnicode(message.getContent().getContent()).getBytes());

					port.writeBytes(new byte[]{ (byte)0x1A});
					//port.writeString(StringUtil.toUnicode(message.getContent().getContent()));
					//port.writeInt(0x1A);
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
				

			}catch (Exception e) {}


		}
	}
}

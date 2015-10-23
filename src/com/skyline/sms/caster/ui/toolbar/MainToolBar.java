package com.skyline.sms.caster.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JToolBar;

import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.pojo.TMessageSent;
import com.skyline.sms.caster.service.MessageReceivedService;
import com.skyline.sms.caster.service.MessageService;
import com.skyline.sms.caster.service.PortService;
import com.skyline.sms.caster.service.SentService;
import com.skyline.sms.caster.service.impl.MessageReceivedServiceImpl;
import com.skyline.sms.caster.service.impl.MessageServiceImpl;
import com.skyline.sms.caster.service.impl.PortServiceImpl;
import com.skyline.sms.caster.service.impl.SentServiceImpl;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.content.PhonesPanel;
import com.skyline.sms.caster.util.LogUtil;

public class MainToolBar extends JToolBar {
	private  static int taskCount=0;
	private ImageButton startButton;
	private ImageButton sendButton;
	private ImageButton receiveButton;
	
	
	private MessageService msgService= new MessageServiceImpl();
	private SentService msgSentService =new SentServiceImpl();
	
	public MainToolBar(){
		startButton =  new ImageButton("sms.caster.label.button.start");
		startButton.setImagePath("resource/image/start_button.png");
		
		add(startButton);
		
		sendButton =  new ImageButton("sms.caster.label.button.send");
		sendButton.setImagePath("resource/image/send_off.png");
		sendButton.addActionListener(new sendSmsListener());
		
		add(sendButton);
		
		receiveButton =  new ImageButton("sms.caster.label.button.receive");
		receiveButton.setImagePath("resource/image/receive.png");
		
		add(receiveButton);
	}
	
	
	class sendSmsListener implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			List<TMessage> smsList= new CopyOnWriteArrayList<>(msgService.getAll());
			Set<String> portNames=PhonesPanel.getInstance().getSelectedPortNames();

			for(String portName:portNames){
				try {
					Port port = JsscPort.getInstance(portName);
					new Thread(new  SendSmsTask(smsList,port)).start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					LogUtil.error(e1);
					PhonesPanel.getInstance().updatePortStatus(portName, e1.getMessage());
				}
			}
	
		}
	}
	
	class SendSmsTask implements Runnable{

		private List<TMessage> smsList;
		private Port port;
		
//		private PortService portService;
		
		 public SendSmsTask( List<TMessage> smsList,Port port) {
			 this.smsList=smsList;
			 this.port=port;
			 LogUtil.info("task cout is "+(++taskCount));
		}
		
		@Override
		public void run() {
			LogUtil.info("smsList length is "+smsList.size());
			
			 PortService portService= PortServiceImpl.getInstance(port);
	
			try {
				portService.init();   
			} catch (Exception e1) {
				LogUtil.error(e1);
				PhonesPanel.getInstance().updatePortStatus(port.getPortName(), e1.getMessage());
			}
			
			
			for(TMessage sms:smsList){
				LogUtil.info("sms number is "+sms.getNumber());
				try {
					PhonesPanel.getInstance().updatePortStatus(port.getPortName(),"sending");
					ExecuteResult result =portService.sendSms(sms);//出错提示和成功移动到已发送短信
					
					PhonesPanel.getInstance().updatePortStatus(port.getPortName(),result.isOK()+"");
					//假如发送成功....
					if(result.isOK()){
						TMessageSent msgSent = new TMessageSent(sms);
						Date now =new Date();
						msgSent.setSentDate(now);
						msgSent.setCreateDate(now);
						msgSent.setUpdateDate(now);
						msgSent.setPortName(port.getPortName());
						msgSent.setStatus(1);
						msgSentService.add(msgSent);
						
						//删除
		//				msgService.delById(sms.getId());
					}

					
				} catch (Exception e) {
					LogUtil.error(e);
					PhonesPanel.getInstance().updatePortStatus(port.getPortName(), e.getMessage());
				}
				
				

			}
			
		}
		
	}
	
	
}

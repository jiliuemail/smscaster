package com.skyline.sms.caster.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JToolBar;

import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.MessageService;
import com.skyline.sms.caster.service.PortService;
import com.skyline.sms.caster.service.impl.MessageServiceImpl;
import com.skyline.sms.caster.service.impl.PortServiceImpl;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.content.PhonesPanel;
import com.skyline.sms.caster.util.LogUtil;

public class MainToolBar extends JToolBar {
	private  static int taskCount=0;
	private ImageButton startButton;
	private ImageButton sendButton;
	private ImageButton receiveButton;
	
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
		private MessageService msgService= new MessageServiceImpl();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			List<TMessage> smsList= new CopyOnWriteArrayList<>(msgService.getAll());
			Set<String> portNames=PhonesPanel.getInstance().getSelectedPortNames();

			for(String portName:portNames){
				PortService portService=null;
				try {
					Port port = JsscPort.getInstance(portName);
					 portService = PortServiceImpl.getInstance(port);    //需要更新UI 上的状态

					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				new Thread(new  SendSmsTask(smsList,portService)).start();
			}
			
			
		}
		
	}
	
	class SendSmsTask implements Runnable{

		private List<TMessage> smsList;
		private PortService portService;
		
		 public SendSmsTask( List<TMessage> smsList,PortService portService) {
			 this.smsList=smsList;
			 this.portService=portService;
			 LogUtil.info("task cout is "+(++taskCount));
		}
		
		@Override
		public void run() {
			LogUtil.info("smsList length is "+smsList.size());
			
			try {
				portService.init();   //初始化失败则如何处理??? 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			for(TMessage sms:smsList){
				LogUtil.info("sms number is "+sms.getNumber());
				try {
					
					ExecuteResult result =portService.sendSms(sms);//出错提示和成功移动到已发送短信

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	
}

package com.skyline.sms.caster.test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.ATCommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.CtrlZCommand;
import com.skyline.sms.caster.cmd.message.CMGF;
import com.skyline.sms.caster.cmd.message.CMGS;
import com.skyline.sms.caster.cmd.message.CSCS;
import com.skyline.sms.caster.cmd.message.CSMP;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.connector.ReadObserver;
import com.skyline.sms.caster.service.SendSmsTask;
import com.skyline.sms.caster.util.StringUtil;
import com.skyline.sms.pojo.Contacter;
import com.skyline.sms.pojo.Content;
import com.skyline.sms.pojo.Message;

public class SendSmsTest {

	
	public static void main(String[] args) throws Exception{
		Logger logger =LoggerFactory.getLogger(SendSmsTest.class);

		Port port = JsscPort.getInstance("/dev/ttyXRUSB3");
		
	//	ReadObserver executorReader=new ReadObserver((JsscPort)port);  //添加监听器.一个端口一个

		Set<Port> ports=new HashSet<>();
		ports.add(port);
		
		//初始化短信
		
		Contacter contacter= new Contacter("18589040855", "predisw");
		Content content = new Content("我是predisw,[王]!");
		Message sms=new Message(contacter, content);
		
		List smsList=new ArrayList<Message>();
		smsList.add(sms);
		
		
		CMGF cmgf= new CMGF();
		cmgf.setValue("1");
		
		CSCS cscs = new CSCS();
		cscs.setValue("\"UCS2\"");

		CSMP csmp= new CSMP();
		csmp.setValue("17,71,0,8");
		


		
		
		

		CommandExecutor atExecutor = new ATCommandExecutor(ports);
		atExecutor.set(cmgf);

		atExecutor.set(cscs);

		atExecutor.set(csmp);


		Thread.sleep(1000);

		
		
//		SendSmsTask sendSmsTask = new SendSmsTask(smsList, ports,executorReader);
//		sendSmsTask.start();
		
		

	//	Thread.sleep(3000);
	//	System.exit(-1);
		
	}
	

}

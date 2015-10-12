package com.skyline.sms.caster.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.cmd.atcmd.ATCommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.atcmd.SetCommand;
import com.skyline.sms.caster.cmd.message.CMGF;
import com.skyline.sms.caster.cmd.message.CSCS;
import com.skyline.sms.caster.cmd.message.CSMP;
import com.skyline.sms.caster.cmd.message.CSQ;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.service.SendSmsTask;
import com.skyline.sms.pojo.Contacter;
import com.skyline.sms.pojo.Content;
import com.skyline.sms.pojo.Message;

public class SendSmsTest {

	
	public static void main(String[] args) throws Exception{

		Port port = JsscPort.getInstance("/dev/ttyXRUSB3");
		Set<Port> ports=new HashSet<>();
		ports.add(port);
		

		//初始化短信
		
		Contacter contacter= new Contacter("18589040855", "predisw");
		Content content = new Content("我是predisw,[王]!");
		Message sms=new Message(contacter, content);
		
		List smsList=new ArrayList<Message>();
		smsList.add(sms);
		

		
		Command cmgf= new CMGF("1");
		cmgf.setCommandType(CommandType.SET);
		//cmgf.setValue("1");
		
		Command cscs = new SetCommand(new CSCS("\"UCS2\""));
		//cscs.setValue("\"UCS2\"");


		Command csmp = CommandFactory.forSet(new CSMP("17,71,0,8"));
		//csmp.setValue("17,71,0,8");
		


		
		
		

		CommandExecutor atExecutor = new ATCommandExecutor(port);
		System.out.println(atExecutor.execute(cmgf).getResult());

		System.out.println(atExecutor.execute(cscs).getResult());

		System.out.println(atExecutor.execute(csmp).isOK());
		
		Command cmgf2= CommandFactory.forSet(new CMGF("2"));
		System.out.println(atExecutor.execute(cmgf2).isOK());
	//	Thread.sleep(1000);

		
		
//		SendSmsTask sendSmsTask = new SendSmsTask(smsList, ports);
//		sendSmsTask.start();
		
		

	//	Thread.sleep(3000);
	//	System.exit(-1);
		
	}
	


}

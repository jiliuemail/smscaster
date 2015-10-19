package com.skyline.sms.caster.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.atcmd.SetCommand;
import com.skyline.sms.caster.cmd.message.CMGF;
import com.skyline.sms.caster.cmd.message.CSCS;
import com.skyline.sms.caster.cmd.message.CSMP;
import com.skyline.sms.caster.cmd.message.CSQ;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.JsscPortList;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.executor.ATCommandExecutor;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.PortService;
import com.skyline.sms.caster.service.SendSmsTask;
import com.skyline.sms.caster.service.impl.PortServiceImpl;
import com.skyline.sms.caster.util.LogUtil;
import com.skyline.sms.pojo.Contacter;
import com.skyline.sms.pojo.Content;
import com.skyline.sms.pojo.Message;

public class SendSmsTest {

	
	public static void main(String[] args) throws Exception{

		Port port = JsscPort.getInstance("/dev/ttyXRUSB3");
		
		PortService portService = PortServiceImpl.getInstance(port);
		
		TMessage sms = new TMessage(0, "我是Predisw", "18589040855");
		
		portService.init();
		ExecuteResult result =	portService.sendSms(sms);

		LogUtil.info(result.getResult());

		
		
		//	SendSmsTask sendSmsTask = new SendSmsTask(smsList, ports);
	//	sendSmsTask.start();
		
		

		
		

	}
	


}

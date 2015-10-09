package com.skyline.sms.caster.test;

import java.lang.Thread.State;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.atcmd.SetCommand;
import com.skyline.sms.caster.cmd.message.CMGF;
import com.skyline.sms.caster.cmd.message.CSCS;
import com.skyline.sms.caster.cmd.message.CSMP;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.task.CmdTask;

public class CmdTaskTest {
	public static void main(String[] args) throws Exception {
	
		Port port = JsscPort.getInstance("/dev/ttyXRUSB3");
		CmdTask task= CmdTask.getInstance(port);
		
		//启动task,可以在启动smsCaster 的时候启动所有端口的task.在其他service 里就不需要再启动了.
		new Thread(task).start();
		
		Command cmgf= new CMGF("1");
		cmgf.setCommandType(CommandType.SET);

		
		Command cscs = new SetCommand(new CSCS("\"UCS2\""));



		Command csmp = CommandFactory.forSet(new CSMP("17,71,0,8"));

		task.add(cmgf);
		task.add(csmp);
		task.add(cscs);
		
		
		
		
		
	}
}

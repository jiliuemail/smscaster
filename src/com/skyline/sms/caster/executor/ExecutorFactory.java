package com.skyline.sms.caster.executor;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.ATCommandExecutor;
import com.skyline.sms.caster.connector.Port;


public class ExecutorFactory {
	private Port port;
	private static Map<String,CommandExecutor> map = new HashMap<String, CommandExecutor>();
	
	public static CommandExecutor getInstance(Port port){
		 		
		CommandExecutor executor=map.get(port.getPortName());
		if(executor==null){
			synchronized (port.getPortName()) {
				executor=map.get(port.getPortName());
				 if(executor==null){
					 executor=new ATCommandExecutor(port);
					 map.put(port.getPortName(), executor);
				 }
			}
		}
		
		return executor;
	}
	

	
}

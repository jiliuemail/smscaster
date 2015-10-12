package com.skyline.sms.caster.service;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;

public class PortService {
	private Port port;
	private CommandExecutor executor;
	
	
	private PortService(String portName) throws Exception {
		port = JsscPort.getInstance(portName);
	}
	
	private static Map<String,PortService> map=new HashMap<>();
	
	public static PortService getInstance(String portName) throws Exception{
		PortService portService = map.get(portName);
		
		if(portService==null){
			synchronized (portName) {
				portService=map.get(portName);
				if(portService==null){
					portService = new PortService(portName);
					map.put(portName, portService);
				}
			}
		}
		
		return portService;
	}
	
	
	public String getPortStatus(){
		
		
		return null;
	}
}

package com.skyline.sms.caster.service;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.message.CPIN;
import com.skyline.sms.caster.cmd.message.CREG;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.executor.ATCommandExecutor;


public class PortService {
	private Port port;
	private CommandExecutor executor;
	
	
	private PortService(Port port) throws Exception {
		this.port = port;
		executor=ATCommandExecutor.getInstance(port);
	}
	
	private static Map<String,PortService> map=new HashMap<>();
	
	public static PortService getInstance(Port port) throws Exception{
		String portName=port.getPortName();
		PortService portService = map.get(portName);
		
		if(portService==null){
			synchronized (portName) {
				portService=map.get(portName);
				if(portService==null){
					portService = new PortService(port);
					map.put(portName, portService);
				}
			}
		}
		
		return portService;
	}
	
	
	public synchronized String getPortStatus() throws Exception{

		String message="";
		Command cpin=CommandFactory.forGet(new CPIN());
		Command creg=CommandFactory.forGet(new CREG());
		ExecuteResult result;
		result = executor.execute(cpin);
		System.out.println("xxxxxxxx"+port.getPortName() +"result'ok is "+result.isOK()+" error is "+result.isError()+" response is "+result.getResult());
		if(result.isOK()){
			
			result=executor.execute(creg);
			if(result.getResult().contains(",1")){
				message="Ready";
			}else{
				message="has not register to the operator";
			}

		}else if(result.isError()){
			message="Please Insert the simcard";
		}
	
		return message;
	}
	
	
	public void getPhoneInfo(){
		
	}
	
}

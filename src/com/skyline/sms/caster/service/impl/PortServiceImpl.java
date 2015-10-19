package com.skyline.sms.caster.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.message.CMGS;
import com.skyline.sms.caster.cmd.message.CPIN;
import com.skyline.sms.caster.cmd.message.CREG;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.executor.ATCommandExecutor;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.PortService;
import com.skyline.sms.caster.util.StringUtil;


public class PortServiceImpl implements PortService {
	private Port port;
	private CommandExecutor executor;
	
	
	private PortServiceImpl(Port port) throws Exception {
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
					portService = new PortServiceImpl(port);
					map.put(portName, portService);
				}
			}
		}
		
		return portService;
	}
	
	@Override
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
	
	@Override
	public synchronized ExecuteResult execute(Command cmd) throws Exception{
		 ExecuteResult result=executor.execute(cmd);
		 return result;
	}
	
	
	public synchronized void sendSms(TMessage sms){
		String number = "\""+StringUtil.toUnicode(sms.getNumber())+"\"";
		String content=sms.getMessage();
		
		Command CMGS= CommandFactory.forSet(new CMGS(number));
		
		
	}
	
	
	
}

package com.skyline.sms.caster.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.atcmd.SetCommand;
import com.skyline.sms.caster.cmd.message.CMGF;
import com.skyline.sms.caster.cmd.message.CMGS;
import com.skyline.sms.caster.cmd.message.CPIN;
import com.skyline.sms.caster.cmd.message.CREG;
import com.skyline.sms.caster.cmd.message.CSCS;
import com.skyline.sms.caster.cmd.message.CSMP;
import com.skyline.sms.caster.cmd.message.CtrlZCommand;
import com.skyline.sms.caster.cmd.message.EscCommand;
import com.skyline.sms.caster.cmd.message.SMSContentCommand;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.executor.ATCommandExecutor;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.PortService;
import com.skyline.sms.caster.util.LogUtil;
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
	
	//设置字符集为ucs2,cmgf=1 为text 模式,等...
	@Override
	public synchronized void init() throws Exception{
		Command cmgf= CommandFactory.forSet(new CMGF("1"));
		Command cscs = new SetCommand(new CSCS("\"UCS2\""));
		Command csmp = CommandFactory.forSet(new CSMP("17,71,0,8"));
		
		executor.execute(cmgf);
		executor.execute(csmp);
		executor.execute(cscs);

		
		
		
	} 
	
	@Override
	public synchronized ExecuteResult sendSms(TMessage sms) throws Exception{
		String number = "\""+StringUtil.toUnicode(sms.getNumber())+"\"";
		String content=StringUtil.toUnicode(sms.getMessage());
		
		Command CMGS= CommandFactory.forSet(new CMGS(number));
		Command SMSContentCommand = CommandFactory.forStream(new SMSContentCommand(content));
		Command ctrlZCommand =CommandFactory.forStream(new CtrlZCommand());
		Command escCommand = CommandFactory.forStream(new EscCommand());
	 	ExecuteResult result=null;
		 try{


		 result=executor.execute(CMGS);
				if(result.getResult().contains(">")){   //会被接收短信的提示所打断吗,或者用for 循环执行多次...
					 result=executor.execute(SMSContentCommand);
					result=executor.execute(ctrlZCommand);
					LogUtil.info("ctrlz "+result.getResult());
					
				}else{
					executor.execute(escCommand);
					
				}
		 }catch (Exception e){
			executor.execute(escCommand);
			 LogUtil.error(e);
			 throw e;
		 }		
		 
		 return result;
		 
	}
	
	
	
}

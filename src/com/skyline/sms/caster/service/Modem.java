package com.skyline.sms.caster.service;

import java.util.List;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.ATCommandExecutor;
import com.skyline.sms.caster.cmd.message.CMGS;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.pojo.Message;
import com.skyline.sms.pojo.Params;

public class Modem {
	
/**
 * 注意,这里只是打开端口,没有关闭!!
 * @param ports
 * @param params
 * @return
 * @throws Exception 
 */
	public static void openAndInit( List<Port> ports,Params params) throws Exception{
		
		for(Port port:ports){
			port.openPort();
			port.setParams(params.getBaudRate(),params.getDataBits(),params.getStopBits(),params.getParity());
			ports.remove(port);//如果打开了就从List 中移除
		}

	}
	
	/**
	 * 
	 * @param ports 
	 * @return
	 * @throws Exception 
	 */
	public static void close( List<Port> ports) throws Exception{
		
		for(Port port:ports){
			port.closePort();
			ports.remove(port);//如果打开了就从List 中移除
			}

	}
	
	public static boolean sendSms(Port port,Message message) throws Exception{
	//	ATCommand CMGF=new CMGF();
	//	ATCommand CMGS = new CMGS(message);
	//	return	port.writeString(CMGS.get()+CMGS.getCommandParam());
		
		Command cmgs = new CMGS(message);
		CommandExecutor executor = new ATCommandExecutor();
		executor.set(cmgs);
		
		String response="";
		if(port.writeString("AT+CMGF=1")){
			response=port.readString();
			System.out.println("the response from AT+CMGF=1 is "+response);
			if(port.writeString(message.getSentMessage())){
				response=port.readString();
				System.out.println("the response from AT+CMGS is "+response);
				return true;
			}
		}

		return false;

	}
	
	public static String  receiveSms(Port port) throws Exception{
		return "";
	}
	
}

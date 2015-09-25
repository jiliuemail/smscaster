package com.skyline.sms.caster.test;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.ATCommandExecutor;
import com.skyline.sms.caster.cmd.atcmd.CtrlZCommand;
import com.skyline.sms.caster.cmd.message.CMGF;
import com.skyline.sms.caster.cmd.message.CMGS;
import com.skyline.sms.caster.cmd.message.CSCS;
import com.skyline.sms.caster.cmd.message.CSMP;
import com.skyline.sms.caster.connector.ExecutorReader;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.Port;

public class SendSmsTest {
	
	public static void main(String[] args) throws Exception{
		
		Port port = JsscPort.getInstance("/dev/ttyXRUSB3");
		
		ExecutorReader executorReader=new ExecutorReader((JsscPort)port);  //添加监听器.一个端口一个

		Set<Port> ports=new HashSet<>();
		ports.add(port);
		
		
		CMGF cmgf= new CMGF();
		cmgf.setValue("1");
		
		CSCS cscs = new CSCS();
		cscs.setValue("\"UCS2\"");
		
		CSMP csmp= new CSMP();
		csmp.setValue("17,71,0,8");
		
		CMGS cmgs = new CMGS();
		
		String message="\"00310038003500380039003000340030003800350035\"";
		cmgs.setValue(message);

		CommandExecutor atExecutor = new ATCommandExecutor(ports);
//		atExecutor.set(cmgf);

//		atExecutor.set(cscs);
//		atExecutor.set(csmp);
		
		atExecutor.set(cmgs);
		Thread.sleep(500);
//		port.writeString("at+cmgs=\"00310038003500380039003000340030003800350035\"\n\r");
		

	//	atExecutor.stream(new CtrlZCommand());
		
	//	ByteBuffer bb = ByteBuffer.wrap("4F60597D".getBytes());
	//	bb.put(new byte[]{(byte)0x1A});
	//	port.writeBytes(bb.array());
		port.writeString(string2Unicode("你好"));
		port.writeInt(0x1A);
//		System.out.print(string2Unicode("你好"));


		Thread.sleep(1500);
		System.exit(-1);
		
	}
	
	public static String string2Unicode(String string) {
		 
	    StringBuffer unicode = new StringBuffer();
	 
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	 
	        // 转换为unicode
	        unicode.append( Integer.toHexString(c));
	    }
	 
	    return unicode.toString();
	}
}

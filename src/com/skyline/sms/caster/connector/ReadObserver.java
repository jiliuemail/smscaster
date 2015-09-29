package com.skyline.sms.caster.connector;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * 监听者类除非是port 的内部类,要不无法在port 类内当作一个成员变量
 * @author predisw
 *
 */
public class ReadObserver implements SerialPortEventListener {
	
	private JsscPort port;
	private String response="";
	private static Logger logger=LoggerFactory.getLogger(ReadObserver.class);


	public JsscPort getPort() {
		return port;
	}




	public String getResponse() {
		return response;
	}




	public ReadObserver(JsscPort port) throws Exception {
		super();
		this.port = port;
		port.addEventListener(this,SerialPort.MASK_RXCHAR);

	}




	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		if(event.isRXCHAR()){
			try {
				
				response=port.readString();  //光标的位置会移动到这个字符流的最后,所以再次port.reading 返回空.
				logger.debug("the response from [{}]'s observer is [{}]",port.getPortName(),response);
				
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}

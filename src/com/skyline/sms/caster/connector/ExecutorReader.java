package com.skyline.sms.caster.connector;

import java.util.Set;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ExecutorReader implements SerialPortEventListener {
	
	private JsscPort port;
	
	


	public ExecutorReader(JsscPort port) throws Exception {
		super();
		this.port = port;
		port.addEventListener(this,SerialPort.MASK_RXCHAR);
	}




	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		if(event.isRXCHAR()){
			try {
				System.out.println(port.readString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}

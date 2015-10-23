package com.skyline.sms.caster.connector;

import java.util.regex.Pattern;

import jssc.SerialPortList;

public class JsscPortList {
	
	
	public static String[] getPortNames(){
		String[] portNames={};
		//--------系统为Linux..
		if(System.getProperty("os.name").startsWith("Linux")){
			Pattern pattern = Pattern.compile("(ttyXRUSB|ttyS|ttyUSB|ttyACM|ttyAMA|rfcomm)[0-9]{1,3}");
			portNames=SerialPortList.getPortNames(pattern);
			return portNames;
		}
		
		//----系统为windows..........
		if(System.getProperty("os.name").startsWith("Windows")){
	//		Pattern pattern = Pattern.compile("(ttyXRUSB|ttyS|ttyUSB|ttyACM|ttyAMA|rfcomm)[0-9]{1,3}");
			portNames=SerialPortList.getPortNames();
		}
		return portNames;
	}
}

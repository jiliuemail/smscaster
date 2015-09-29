package com.skyline.sms.caster.connector;

import jssc.SerialPort;
import jssc.SerialPortEventListener;

public interface  Port  {

//	public  Port getInstance(String portName) throws Exception;
	public  boolean openPort() throws Exception;
	public  boolean closePort() throws Exception;
	public  boolean setParams(int baudRate, int dataBits,int stopBits,  int parity)     throws Exception;
	public   boolean setDTR(boolean enabled) throws Exception;
	public  boolean setRTS(boolean enabled) throws Exception;
	public  boolean writeBytes(byte[] buffer)  throws Exception;
	public   boolean writeString(java.lang.String string) throws Exception;
	public   boolean writeInt(int singleInt) throws Exception;
	
	public  byte[] readBytes()    throws Exception;
	public  byte[] readBytes(int byteCount)  throws Exception;
	public  byte[] readBytes(int byteCount, int timeout)  throws Exception;
	public  String readString()    throws Exception;
	public  String readString(int byteCount, int timeout)  throws Exception;
	
	public  boolean isOpened();
	public String getPortName();

	String getResponse();
	
	


	
	
	
}

package com.skyline.sms.caster.connector;

import jssc.SerialPort;

public abstract class Port  {

	
	public abstract boolean openPort() throws Exception;
	public abstract boolean closePort() throws Exception;
	public abstract boolean setParams(int baudRate, int dataBits,int stopBits,  int parity)     throws Exception;
	public abstract  boolean setDTR(boolean enabled) throws Exception;
	public abstract boolean setRTS(boolean enabled) throws Exception;
	public abstract boolean writeBytes(byte[] buffer)  throws Exception;
	public abstract  boolean writeString(java.lang.String string) throws Exception;
	
	public abstract byte[] readBytes()    throws Exception;
	public abstract byte[] readBytes(int byteCount)  throws Exception;
	public abstract byte[] readBytes(int byteCount, int timeout)  throws Exception;
	public abstract String readString()    throws Exception;
	public abstract String readString(int byteCount, int timeout)  throws Exception;
	
	public abstract boolean isOpened();

	
	
	
}

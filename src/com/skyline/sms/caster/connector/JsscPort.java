package com.skyline.sms.caster.connector;

import jssc.SerialPort;

public class JsscPort extends Port{

	 private SerialPort serialPort;
	
	public JsscPort(String portName){
		this.serialPort=new SerialPort(portName);
	} 
	 
	@Override
	public boolean openPort() throws Exception {
		// TODO Auto-generated method stub
		
		return serialPort.openPort();
	}

	@Override
	public boolean closePort() throws Exception {
		// TODO Auto-generated method stub
		return serialPort.closePort();
	}

	@Override
	public boolean setParams(int baudRate, int dataBits, int stopBits,
			int parity) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.setParams(baudRate, dataBits, stopBits, parity);
	}

	@Override
	public boolean setDTR(boolean enabled) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.setDTR(enabled);
	}

	@Override
	public boolean setRTS(boolean enabled) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.setRTS(enabled);
	}

	@Override
	public boolean writeBytes(byte[] buffer) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.writeBytes(buffer);
	}

	@Override
	public boolean writeString(String string) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.writeString(string);
	}

	@Override
	public byte[] readBytes() throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readBytes();
	}

	@Override
	public byte[] readBytes(int byteCount) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readBytes(byteCount);
	}

	@Override
	public byte[] readBytes(int byteCount, int timeout) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readBytes(byteCount, timeout);
	}

	@Override
	public String readString() throws Exception {
		// TODO Auto-generated method stub
		return readString();
	}

	@Override
	public String readString(int byteCount, int timeout) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readString(byteCount, timeout);
	}

	@Override
	public boolean isOpened() {
		// TODO Auto-generated method stub
		return serialPort.isOpened();
	}
	
	
}

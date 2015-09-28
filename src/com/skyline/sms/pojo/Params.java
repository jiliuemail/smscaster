package com.skyline.sms.pojo;

public class Params {
	
	private int baudRate;
	private int dataBits;
	private int stopBits;
	private int parity;
	

	public Params(int baudRate, int dataBits, int stopBits, int parity) {
		super();
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		this.parity = parity;
	}


	public int getBaudRate() {
		return baudRate;
	}


	public int getDataBits() {
		return dataBits;
	}


	public int getStopBits() {
		return stopBits;
	}


	public int getParity() {
		return parity;
	}
}

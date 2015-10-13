package com.skyline.sms.caster.test;

import java.util.Arrays;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortTest {
	public static void main(String[] args) throws SerialPortException, InterruptedException {
		SerialPort port = new SerialPort("/dev/ttyXRUSB3");
		
		port.openPort();
		port.setParams(115200, 8, 1, 0);
		port.writeString("AT");
		Thread.sleep(100);
		System.out.println(port.readString());
		System.out.println(Arrays.toString(port.getLinesStatus()));
	}

}

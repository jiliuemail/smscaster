package com.skyline.sms.caster.test;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.skyline.sms.caster.connector.JsscPortList;

import jssc.SerialPortList;



public class GetPortsTest {
	public static void main(String[] args) {

		String[] portNames=JsscPortList.getPortNames();
		System.out.println(Arrays.toString(portNames));
	}
}

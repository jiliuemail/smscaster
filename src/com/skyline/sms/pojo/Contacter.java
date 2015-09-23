package com.skyline.sms.pojo;

public class Contacter {

	private String phoneNumber;
	private String name;
	
	
	public Contacter(String phoneNumber, String name) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getName() {
		return name;
	}
	
}

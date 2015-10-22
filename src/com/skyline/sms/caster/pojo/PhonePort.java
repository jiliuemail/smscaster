package com.skyline.sms.caster.pojo;

public class PhonePort implements Comparable<PhonePort> {

	private boolean choosed;
	private String portName;
	private String status;
	
	public PhonePort() {
		// TODO Auto-generated constructor stub
	}
	
	public PhonePort(String portName){
		this.choosed=false;
		this.portName=portName;
		this.status="";
	}
	
	public boolean getChoosed() {
		return choosed;
	}
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(PhonePort o) {
		// TODO Auto-generated method stub
		return this.portName.compareTo(o.getPortName());
		
	}
	
}

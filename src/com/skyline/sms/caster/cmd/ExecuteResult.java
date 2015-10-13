package com.skyline.sms.caster.cmd;

public class ExecuteResult {
	
	private String result="";
	private String value="";
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isOK(){
		return this.result.contains("OK");
	}
	
	public boolean isError(){
		return this.result.contains("ERROR");
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;

	}
	
	public String getErrorMessage(){
		
		return "";// TODO
	}
	

}

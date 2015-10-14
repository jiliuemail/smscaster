package com.skyline.sms.caster.cmd;

public class ExecuteResult {
	
	private String result="";

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

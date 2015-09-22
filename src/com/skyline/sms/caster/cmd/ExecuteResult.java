package com.skyline.sms.caster.cmd;

public class ExecuteResult {
	
	private String result;
	
	public boolean isOK(){
		return "OK".equals(result);
	}
	
	public boolean isError(){
		return !isOK();
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

package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.atcmd.ATCommand;
import com.skyline.sms.pojo.Content;
import com.skyline.sms.pojo.Message;

public class CMGS extends ATCommand {

	private String value;
	
	
	
	@Override
	protected String getCommandParam() {
		// TODO Auto-generated method stub
		
		return value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	private void setValue(String value) {
		this.value = value;
	}
	
	

}

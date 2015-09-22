package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.sms.ATCommand;

public class CSMS extends ATCommand {
	
	private String service;

	@Override
	protected String getCommandParam() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}

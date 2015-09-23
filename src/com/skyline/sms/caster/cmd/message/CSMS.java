package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.atcmd.ATCommand;

public class CSMS extends ATCommand {
	
	private String service;

	@Override
	public String getCommandParam() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}

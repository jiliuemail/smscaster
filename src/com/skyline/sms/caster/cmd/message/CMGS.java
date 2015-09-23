package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.atcmd.ATCommand;
import com.skyline.sms.pojo.Content;
import com.skyline.sms.pojo.Message;

public class CMGS extends ATCommand {

	private Message sms;
	


	public CMGS(Message sms) {
		super();
		this.sms = sms;
	}



	@Override
	public String getCommandParam() {
		// TODO Auto-generated method stub
		
		return sms.getSentMessage();
	}

}

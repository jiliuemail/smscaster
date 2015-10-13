package com.skyline.sms.caster.cmd.message;

import java.util.List;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.atcmd.ATCommand;

public class ValueCommand extends ATCommand implements Command {
	
	private String value;

	
	public ValueCommand(){}
	
	public ValueCommand(String value){
		this.value = value;
	}

	@Override
	protected String getCommandParam() {
		return value;
	}


	@Override
	public String formatResult(String str) {
		// TODO Auto-generated method stub
		return null;
	}


}

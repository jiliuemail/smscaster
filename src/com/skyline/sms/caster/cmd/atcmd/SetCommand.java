package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;

public class SetCommand extends CommandDelegate {

	
	public SetCommand(Command command) {
		super(command);
	}
	

	@Override
	public void setCommandType(CommandType commandType) {
		getCommand().setCommandType(CommandType.SET);
	}

	@Override
	public CommandType geCommandType() {
		return CommandType.SET;
	}

}

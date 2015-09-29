package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;

public class CheckCommand extends CommandDelegate {

	
	public CheckCommand(Command command) {
		super(command);
	}

	@Override
	public void setCommandType(CommandType commandType) {
		getCommand().setCommandType(CommandType.CHECK);
	}

	@Override
	public CommandType geCommandType() {
		return CommandType.CHECK;
	}

}

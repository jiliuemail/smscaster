package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;

public class GetCommand extends CommandDelegate {

	
	public GetCommand(Command command) {
		super(command);
	}

	@Override
	public void setCommandType(CommandType commandType) {
		getCommand().setCommandType(CommandType.GET);
	}

	@Override
	public CommandType geCommandType() {
		return CommandType.GET;
	}

}

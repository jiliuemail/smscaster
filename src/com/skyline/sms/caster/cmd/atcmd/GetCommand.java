package com.skyline.sms.caster.cmd.atcmd;

import java.util.List;

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
	public CommandType getCommandType() {
		return CommandType.GET;
	}



}

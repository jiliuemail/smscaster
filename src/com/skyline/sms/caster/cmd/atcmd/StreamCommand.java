package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;

public class StreamCommand extends CommandDelegate {

	
	public StreamCommand(Command command) {
		super(command);
	}

	@Override
	public void setCommandType(CommandType commandType) {
		getCommand().setCommandType(CommandType.STREAM);
	}

	@Override
	public CommandType getCommandType() {
		return CommandType.STREAM;
	}

}

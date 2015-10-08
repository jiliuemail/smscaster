package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.AbstractCommand;
import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;

public class CtrlZCommand extends AbstractCommand implements Command{
	
	private static byte[] VALUE = new byte[]{(byte)0x1A};
	
	@Override
	public byte[] stream() {
		return VALUE;
	}

	@Override
	public CommandType getCommandType() {
		return CommandType.STREAM;
	}

	
	
	

}

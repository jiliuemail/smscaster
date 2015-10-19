package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.AbstractCommand;
import com.skyline.sms.caster.cmd.CommandType;

public class EscCommand extends AbstractCommand {
	private static byte[] esc=new byte[]{(byte)0x1B};
	
	@Override
	public byte[] stream() {
		// TODO Auto-generated method stub
		return esc;
	}
	
	@Override
	public CommandType getCommandType() {
		// TODO Auto-generated method stub
		return CommandType.STREAM;
	}
}

package com.skyline.sms.caster.cmd.atcmd;

public class CtrlZCommand extends ATCommand{
	
	private static byte[] VALUE = new byte[]{(byte)0x1A};
	
	@Override
	public byte[] stream() {
		return VALUE;
	}

}

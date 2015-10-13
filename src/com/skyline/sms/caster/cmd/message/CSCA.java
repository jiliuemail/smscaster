package com.skyline.sms.caster.cmd.message;

public class CSCA extends ValueCommand{
	@Override
	public String formatResult(String str) {
		// TODO Auto-generated method stub
		return str.substring(str.indexOf("++CSCA:"),str.indexOf("\n"));
		
	}
}

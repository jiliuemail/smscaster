package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.AbstractCommand;
import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.util.StringUtil;

public class UnicodeCommand extends AbstractCommand implements Command {
	
	
	private String value="";
	

	public UnicodeCommand(String value) {
		super();
		this.value = value;
	}

	private String toUnicode(){
	  return StringUtil.toUnicode(value);
	}

	@Override
	public String check() {
		return toUnicode();
	}

	@Override
	public String get() {
		return toUnicode();
	}

	@Override
	public String set() {
		return toUnicode();
	}


	@Override
	public CommandType geCommandType() {
		return CommandType.SET;
	}
	
	

}

package com.skyline.sms.caster.cmd.message;

import com.skyline.sms.caster.cmd.AbstractCommand;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.util.StringUtil;

public class SMSContentCommand 	extends AbstractCommand {
	private String smsContent;
	
	public SMSContentCommand(String smsContent) {
		super();
		this.smsContent = smsContent;
	}

	@Override
	public byte[] stream() {
		// TODO Auto-generated method stub
		return this.smsContent.getBytes();
	}

	//如果使用工厂方法创建对象,则这个覆盖的方法则是多此一举. 可否去掉工厂方法,然后所有命令都覆盖如下方法呢?!!,不能......,方法的类型是变动的...
	@Override
	public CommandType getCommandType() {
		// TODO Auto-generated method stub
		return CommandType.STREAM;
	}
}

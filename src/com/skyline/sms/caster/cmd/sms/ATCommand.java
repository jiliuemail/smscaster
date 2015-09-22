package com.skyline.sms.caster.cmd.sms;

import com.skyline.sms.caster.cmd.Command;

/**
 *  测试 AT+<x>=?
 *  查询 AT+<x>?
 *  设置 AT+<x>=<...>
 *
 */
public abstract class ATCommand implements Command {
	
	private static String COMMAND_PREFIX = "AT+";

	
	@Override
	public String check() {
		return content() + COMMAND_CHECK;
	}

	@Override
	public String get() {
		return content() + COMMAND_GET;
	}

	@Override
	public String set() {
		return content() + COMMAND_SET + getCommandParam();
	}

	protected String content() {
		return COMMAND_PREFIX + getClass().getSimpleName().toUpperCase();
	}
	
	// 设置命令的参数
	protected abstract String getCommandParam();

}

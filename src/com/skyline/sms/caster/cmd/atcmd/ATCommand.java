package com.skyline.sms.caster.cmd.atcmd;

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
		return COMMAND_PREFIX + getClass().getSimpleName().toUpperCase();  //测试是否只获取到父类名
	}
	
	// 设置命令的参数
	public abstract String getCommandParam();

}

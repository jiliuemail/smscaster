package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.AbstractCommand;

/**
 *  测试 AT+<x>=?
 *  查询 AT+<x>?
 *  设置 AT+<x>=<...>
 *
 */
public abstract class ATCommand extends AbstractCommand {
	
	private static String COMMAND_PREFIX = "AT+";
	private static String COMMAND_SUBFIX = "\n";


	@Override
	public String check() {
		return content() + COMMAND_CHECK+COMMAND_SUBFIX;
	}

	@Override
	public String get() {
		return content() + COMMAND_GET+COMMAND_SUBFIX;
	}

	@Override
	public String set() {
		return content() + COMMAND_SET + getCommandParam()+COMMAND_SUBFIX;
	}

	protected String content() {
		return COMMAND_PREFIX + getClass().getSimpleName().toUpperCase(); 
	}
	
	// 设置命令的参数
	protected abstract  String getCommandParam();

	
	@Override
	public byte[] stream() {
		String setContent = set();
		return (setContent == null ? new byte[0] : setContent.getBytes());
	}
	
	@Override
	public abstract String formatResult(String str);
	
	
	
}

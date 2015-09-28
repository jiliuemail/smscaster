package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;

/**
 *  测试 AT+<x>=?
 *  查询 AT+<x>?
 *  设置 AT+<x>=<...>
 *
 */
public  class ATCommand implements Command {
	
	private static String COMMAND_PREFIX = "AT+";
	private static String COMMAND_SUBFIX = "\n";
	private String value="";
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

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
	protected  String getCommandParam(){
		return  this.value;
	}


	@Override
	public byte[] stream() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

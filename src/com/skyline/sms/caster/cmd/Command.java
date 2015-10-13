package com.skyline.sms.caster.cmd;

/**
 * 命令接口
 *
 */
public interface Command {
	
	public static String COMMAND_CHECK = "=?";
	
	public static String COMMAND_GET = "?";

	public static String COMMAND_SET = "=";
	
	/**
	 * 获取测试命令
	 * @return 命令内容
	 */
	String check();
	
	/**
	 * 获取查询命令
	 * @return 命令内容
	 */
	String get();
	
	/**
	 * 获取设置命令
	 * @return 命令内容
	 */
	String set();

	byte[] stream();
	
	public void setCommandType(CommandType commandType);
	
	public CommandType getCommandType();
	
	public String formatResult(String str);
}

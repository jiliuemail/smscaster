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

	/**
	 * 获取原始命令，不带参数
	 * @return
	 */
	String origin();
	
	/**
	 * 获取字节命令
	 * @return
	 */
	byte[] stream();
	

	int getTimeout();
	
	String getName();
	

	/**
	 * 设置命令的类型
	 * @param commandType 命令的类型
	 * @see CommandType
	 */
	public void setCommandType(CommandType commandType);
	
	/**
	 * 获取命令的类型
	 * @return 命令的类型
	 * @see CommandType
	 */
	public CommandType getCommandType();

}

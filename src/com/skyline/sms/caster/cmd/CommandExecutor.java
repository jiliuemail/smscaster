package com.skyline.sms.caster.cmd;

/**
 * 命令执行器
 * 可以对命令进行测试、查询、设置
 * 
 */
public interface CommandExecutor {
	
	public ExecuteResult check(Command cmd) throws Exception;
	
	public ExecuteResult get(Command cmd) throws Exception;
	
	public ExecuteResult set(Command cmd) throws Exception;
	
}

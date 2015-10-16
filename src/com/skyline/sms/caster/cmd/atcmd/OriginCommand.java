package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandType;

/**
 * 无视传进来的命令类型,固定设置为Origin,获取的时候也是固定获取为Origin.
 * 即通过这个类型限定命令的类型.
 * @author predisw
 *
 */
public class OriginCommand extends CommandDelegate {

	public OriginCommand(Command command) {
		super(command);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void setCommandType(CommandType commandType) {
		// TODO Auto-generated method stub
		command.setCommandType(CommandType.ORIGIN);
	}

	@Override
	public CommandType getCommandType() {
		// TODO Auto-generated method stub
		return CommandType.ORIGIN;
	}

}

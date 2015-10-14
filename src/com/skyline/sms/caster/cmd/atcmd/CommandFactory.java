package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;

public class CommandFactory {
	
	private CommandFactory(){}
	
	
	public static GetCommand forGet(Command command){
		return new GetCommand(command);
	}
	
	public static SetCommand forSet(Command command){
		return new SetCommand(command);
	}
	
	public static CheckCommand forCheck(Command command){
		return new CheckCommand(command);
	}
	
	public static StreamCommand forStream(Command command){
		return new StreamCommand(command);
	}

	public static OriginCommand forOrigin(Command command){
		return new OriginCommand(command);
	}
}

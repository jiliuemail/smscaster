package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;

public abstract class CommandDelegate implements Command {
	
	protected Command command;
	

	public CommandDelegate(Command command) {
		super();
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}


	@Override
	public String check() {
		return command.check();
	}

	@Override
	public String get() {
		return command.get();
	}

	@Override
	public String set() {
		return command.set();
	}

	@Override
	public byte[] stream() {
		return command.stream();
	}

	@Override
	public String origin() {
		// TODO Auto-generated method stub
		return command.origin();
	}


}

package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;

public abstract class CommandDelegate implements Command {
	
	private Command command;
	

	public CommandDelegate(Command command) {
		super();
		this.command = command;
	}

	protected Command getCommand() {
		return command;
	}

	protected void setCommand(Command command) {
		this.command = command;
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

	

}

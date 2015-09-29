package com.skyline.sms.caster.cmd.atcmd;

import java.util.Set;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.Port;

public class ATCommandExecutor implements CommandExecutor {
	
	private Set<Port> ports;
	
	

	public ATCommandExecutor(Set<Port> ports) {
		super();
		this.ports = ports;
	}
	
	

	@Override
	public ExecuteResult execute(Command cmd) throws Exception {
		switch (cmd.geCommandType()) {
		case CHECK:
			return check(cmd);
		case GET:
			return get(cmd);
		case SET:
			return set(cmd);
		case STREAM :
			return stream(cmd);
		default:
			return null;
		}
	}



	public ExecuteResult check(Command cmd) throws Exception {
		return execute(cmd.check());
	}

	public ExecuteResult get(Command cmd) throws Exception {
		return execute(cmd.get());
	}

	public ExecuteResult set(Command cmd) throws Exception {
		return execute(cmd.set());
	}

	
	
	protected ExecuteResult execute(String cmdContent) throws Exception {
		
		for(Port port:ports){
			port.writeString(cmdContent);
			Thread.sleep(100);
		}
		
		return new ExecuteResult().setResult("ok");

	}

	public ExecuteResult stream(Command cmd) throws Exception {
		for(Port port:ports){
			port.writeBytes(cmd.stream());
		}
		
		return new ExecuteResult().setResult("ok");
	}

}

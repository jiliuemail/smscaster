package com.skyline.sms.caster.cmd.atcmd;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.Port;

public class ATCommandExecutor implements CommandExecutor {
	
	private Port  port;



	public ATCommandExecutor(Port port) {
		super();
		this.port=port;
	}
	
	

	

	@Override
	public ExecuteResult execute(Command cmd) throws Exception {

		
		switch (cmd.getCommandType()) {
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
		ExecuteResult result=new ExecuteResult();

			synchronized(port.getObj()) {
				port.writeBytes(cmdContent.getBytes());
		//		port.writeString(cmdContent);
			port.getObj().wait();  //jsscport 中的监听器来激活这个线程.
			result.setResult(port.getResponse());
			}

		
		return result;

	}

	public ExecuteResult stream(Command cmd) throws Exception {

			port.writeBytes(cmd.stream());


		
		return null;
	}

}

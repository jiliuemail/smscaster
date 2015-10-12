package com.skyline.sms.caster.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.Port;

public class ATCommandExecutor implements CommandExecutor,Runnable{
	
	private Port  port;
	private BlockingQueue<Command> cmdQueue;
	private static Map<String,CommandExecutor> map = new HashMap<String, CommandExecutor>();
	private ExecuteResult result;

	private ATCommandExecutor(Port port) {
		super();
		this.port=port;
		this.cmdQueue=new LinkedBlockingQueue<>();
	}
	
	public static CommandExecutor getInstance(Port port){
 		
		CommandExecutor executor=map.get(port.getPortName());
		if(executor==null){
			synchronized (port.getPortName()) {
				executor=map.get(port.getPortName());
				 if(executor==null){
					 executor=new ATCommandExecutor(port);
					 map.put(port.getPortName(), executor);
				 }
			}
		}
		
		return executor;
	}
	
/*	@Override
	public ExecuteResult execute(Command cmd)  throws Exception{
		cmdQueue.put(cmd);

		return result;
	}*/
	
	
	

	public synchronized ExecuteResult execute(Command cmd) throws Exception {

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
		
		synchronized(port.getObj()) {
			ExecuteResult result=new ExecuteResult();
		
			port.writeString(cmdContent);
			port.getObj().wait();  //jsscport 中的监听器来激活这个线程.
			
			result.setResult(port.getResponse());
			return result;
		}

	}

	public ExecuteResult stream(Command cmd) throws Exception {

			port.writeBytes(cmd.stream());


		
		return null;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	/*	while(true){
			try {
				Command cmd=cmdQueue.take();
				result=executeCmd(cmd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
	}





}

package com.skyline.sms.caster.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.CommandType;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.util.LogUtil;

public class ATCommandExecutor implements CommandExecutor,Callable<ExecuteResult>{
	
	private Port  port;
	private static Map<String,CommandExecutor> map = new HashMap<String, CommandExecutor>();
	private Command cmd;
	private ExecutorService executorService;
	
	private ATCommandExecutor(Port port) {
		super();
		this.port=port;
		executorService = Executors.newSingleThreadExecutor();
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
	
	@Override
	public ExecuteResult execute(Command cmd)  throws Exception{
		this.cmd=cmd;
		long start=System.currentTimeMillis();
		Future<ExecuteResult> cmdResult =executorService.submit((Callable<ExecuteResult>) getInstance(port));
		ExecuteResult result =cmdResult.get();
		LogUtil.info( cmd.getName()+" cost " +(System.currentTimeMillis()-start)+" ms");
		return result;
	}
	
	
	

	private  ExecuteResult executeCmd(Command cmd) throws Exception {

		switch (cmd.getCommandType()) {
		case CHECK:
			return check(cmd);
		case GET:
			return get(cmd);
		case SET:
			return set(cmd);
		case STREAM :
			return stream(cmd);
		case ORIGIN:
			return origin(cmd);
		default:
			return null;
		}
	}



	private ExecuteResult check(Command cmd) throws Exception {
		return execute(cmd.check().getBytes());
	}

	private ExecuteResult get(Command cmd) throws Exception {
		return execute(cmd.get().getBytes());
	}

	private ExecuteResult set(Command cmd) throws Exception {
		return execute(cmd.set().getBytes());
	}


	private ExecuteResult origin(Command cmd) throws Exception{
		return execute(cmd.origin().getBytes());
	}
	
	
	private ExecuteResult stream(Command cmd) throws Exception {
		
		return execute(cmd.stream());
	}


	
	private  ExecuteResult execute(byte[] cmdContent) throws Exception {

		synchronized(port.getObj()) {

			ExecuteResult result=new ExecuteResult();
			port.setResponse("");  //设置返回为空字符串.因为port 是一直存在的,假如没有更新response ,response 也会一直存在.
			
//			port.writeString(cmdContent);
			boolean exeResult=port.writeBytes(cmdContent);
			LogUtil.info(cmd.getName()+" execute status is "+exeResult);
			port.getObj().wait((long)cmd.getTimeout());  //jsscport 中的监听器来激活这个线程.或者超过300ms 就自动激活
			result.setResult(port.getResponse());
			return result;
		}

	}


	


	@Override
	public ExecuteResult call() throws Exception {
		// TODO Auto-generated method stub
		ExecuteResult result=executeCmd(cmd); 
		return result;
	}





}

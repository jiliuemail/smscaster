package com.skyline.sms.caster.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.Port;
import com.skyline.sms.caster.executor.ATCommandExecutor;


public class CmdTask implements Runnable {
	private Port port;
	private BlockingQueue<Command> queue;
	private boolean interrupted; //需要添加volatile 吗?
	private ExecuteResult result;
	private static Map<String,CmdTask> map= new HashMap<>();
	
	
	private CmdTask(Port port){
		this.port=port;
		queue=new LinkedBlockingQueue<Command>();
		this.interrupted=false;
	}
	
	//工厂方法获取......Task实例
	public static CmdTask  getInstance(Port port){

		CmdTask task=map.get(port.getPortName());
		if(task==null){
			synchronized (port.getPortName()) {
				task=map.get(port.getPortName());
				if(task==null){
					task=new CmdTask(port);
					map.put(port.getPortName(), task);
				}
			}
		}
		
		return map.get(port.getPortName());
	}
	
	
	//添加单个命令
	public void add(Command cmd) throws InterruptedException{
		queue.put(cmd);
	}
	
	//添加命令队列
	public void add(BlockingQueue<Command> queue) throws InterruptedException{
		Iterator<Command> cmds=queue.iterator();
		while(cmds.hasNext()){
			this.queue.put(cmds.next());
		}
	}
	
	//获取执行的单一命令的结果
	public ExecuteResult getResult(){
		return result;
	}
	
	
	
	//-----任务----
	@Override
	public void run() {
		CommandExecutor executor = ATCommandExecutor.getInstance(port);
		Command cmd=null;
		
		while(!interrupted){
			try {
				cmd=queue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				interrupted=true;
			}

			try {
			result =	executor.execute(cmd); //如果没有发送成功,or 等待时间比较长则??
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

	
}

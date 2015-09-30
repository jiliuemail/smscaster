package com.skyline.sms.caster.executor;

import java.util.List;
import java.util.Set;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.ATCommandExecutor;
import com.skyline.sms.caster.connector.Port;

public class TaskExecutor extends ATCommandExecutor  implements Runnable  {

	private List<Command> cmds;

	public TaskExecutor(Port port,List<Command> cmds) {
		super(port);
		this.cmds=cmds;
	}

		@Override
	public void run() {
		// TODO Auto-generated method stub
		for(Command cmd:cmds){
			try {
				execute(cmd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}

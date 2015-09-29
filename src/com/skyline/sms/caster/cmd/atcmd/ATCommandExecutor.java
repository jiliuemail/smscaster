package com.skyline.sms.caster.cmd.atcmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.DeviceConnector;
import com.skyline.sms.caster.connector.Port;

public class ATCommandExecutor implements CommandExecutor {
	
	private Set<Port> ports;
	
	

	public ATCommandExecutor(Set<Port> ports) {
		super();
		this.ports = ports;
	}

	@Override
	public ExecuteResult check(Command cmd) throws Exception {
		return execute(cmd.check());
	}

	@Override
	public ExecuteResult get(Command cmd) throws Exception {
		return execute(cmd.get());
	}

	@Override
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

	@Override
	public ExecuteResult stream(Command cmd) throws Exception {
		for(Port port:ports){
			port.writeBytes(cmd.stream());
			
		}
		
		return new ExecuteResult().setResult("ok");
	}

}

package com.skyline.sms.caster.cmd.sms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.CommandExecutor;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.connector.DeviceConnector;

public class SMSCommandExecutor implements CommandExecutor {
	
	protected DeviceConnector deviceConnector;

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
		OutputStream out = deviceConnector.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		writer.write(cmdContent);
		InputStream in = deviceConnector.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer resultBuffer = new StringBuffer();
		String resultTemp;
		while((resultTemp = reader.readLine()) != null){
			resultBuffer.append(resultTemp);
		}
		ExecuteResult executeResult = new ExecuteResult();
		executeResult.setResult(resultBuffer.toString());
		return executeResult;
	}

}

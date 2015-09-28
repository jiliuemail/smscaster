package com.skyline.sms.caster.connector;

import java.io.InputStream;
import java.io.OutputStream;

public interface DeviceConnector {
	
	public String readCommandResult(String commandContent);
	
	public void writeCommand(String commandContent) throws Exception;
	

}

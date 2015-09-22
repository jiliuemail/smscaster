package com.skyline.sms.caster.connector;

import java.io.InputStream;
import java.io.OutputStream;

public interface DeviceConnector {
	
	InputStream getInputStream();
	
	OutputStream getOutputStream();

}

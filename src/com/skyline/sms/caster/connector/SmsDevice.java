package com.skyline.sms.caster.connector;

import java.util.Set;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SmsDevice implements DeviceConnector {
	
	private Set<Port> allPorts;
	private Set<Port> activePorts;
	
	public void setActivePorts(Set<Port> ports) throws Exception{
		
		for(Port port:ports){
			port.openPort();
			port.setParams(115200, 8, 1, 0);
			activePorts.add(port);
		}
		
	}
	
	
	public void  init(Set<Port> ports){
	
	}
	
	public void close(Set<Port> ports){
		
	}
	
	@Override
	public String readCommandResult(String commandContent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void writeCommand(String commandContent)  {
		// TODO Auto-generated method stub
		
	
		
		
	}

}

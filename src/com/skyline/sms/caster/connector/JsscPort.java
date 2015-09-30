package com.skyline.sms.caster.connector;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.util.LogUtil;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class JsscPort implements Port{
	private static int count=0;
	private SerialPort serialPort;
	private SerialPortReader  observer=new SerialPortReader();
	private Object obj; //通过obj.wait() 和obj.notifyAll 在多线程间通讯 

	private static Map<String,Port> portMap= new HashMap<String, Port>();
	
	private  JsscPort(String portName) throws SerialPortException{
		serialPort=new SerialPort(portName);
		serialPort.openPort();
		serialPort.setParams(115200, 8, 1, 0);  //只初始化一次.也是默认的初始化
		serialPort.addEventListener(observer ,SerialPort.MASK_RXCHAR);  //添加监听
		obj=new Object();

	};

	/**
	 * 返回某个端口单个实例,并用(115200, 8, 1, 0)进行默认初始化
	 * @throws Exception 
	 */

	public static Port getInstance(String portName) throws Exception {
		// TODO Auto-generated method stub
		 Port port=portMap.get(portName);

		 if(port==null){
				synchronized (portName) {
					if(port==null){
						port=new JsscPort(portName);
						portMap.put(portName, port);
						count++;
					}
				}
			}

		LogUtil.info("there are "+count+"  port instances now"+"port name is "+port.getPortName());

			return port;


	}

	
	@Override
	public String getPortName(){
		return serialPort.getPortName();
	}

	 
	@Override
	public boolean openPort() throws Exception {
		// TODO Auto-generated method stub
		
		return serialPort.openPort();
	}

	@Override
	public boolean closePort() throws Exception {
		// TODO Auto-generated method stub
		return serialPort.closePort();
	}

	@Override
	public boolean setParams(int baudRate, int dataBits, int stopBits,
			int parity) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.setParams(baudRate, dataBits, stopBits, parity);
	}

	@Override
	public boolean setDTR(boolean enabled) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.setDTR(enabled);
	}

	@Override
	public boolean setRTS(boolean enabled) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.setRTS(enabled);
	}

	@Override
	public boolean writeBytes(byte[] buffer) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.writeBytes(buffer);
	}

	@Override
	public boolean writeString(String string) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.writeString(string);
	}

	@Override
	public byte[] readBytes() throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readBytes();
	}

	@Override
	public byte[] readBytes(int byteCount) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readBytes(byteCount);
	}

	@Override
	public byte[] readBytes(int byteCount, int timeout) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readBytes(byteCount, timeout);
	}

	@Override
	public String readString() throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readString();
	}

	@Override
	public String readString(int byteCount, int timeout) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.readString(byteCount, timeout);
	}

	@Override
	public boolean isOpened() {
		// TODO Auto-generated method stub
		return serialPort.isOpened();
	}

	public void addEventListener(SerialPortEventListener listener,    int mask)     throws Exception{
		
		serialPort.addEventListener(listener, mask);

	}

	@Override
	public boolean writeInt(int singleInt) throws Exception {
		// TODO Auto-generated method stub
		return serialPort.writeInt(singleInt);


	}



	@Override
	public String getResponse(){
		return observer.getResponse();
	}
	
	
@Override
public Object getObj(){
	return obj;
}







	//内部类:监听者
	private  class SerialPortReader implements SerialPortEventListener{
	
		private   String response;
		
		public String getResponse(){
			return response;
		}
		
		@Override
		public void serialEvent(SerialPortEvent event) {
			// TODO Auto-generated method stub
			if(event.isRXCHAR()){
				try {
					
					response=serialPort.readString();  //光标的位置会移动到这个字符流的最后,所以再次port.reading 返回空.
					LogUtil.debug("the response from [{0}]'s observer is [{1}]",serialPort.getPortName(),response);
					LogUtil.info("response is "+response);
					
					
				} catch (Exception e) {
					LogUtil.error(e);

				}finally{
					synchronized (obj) {
						obj.notifyAll();
					}
	

				}
			}
		}
		
	}

	
}

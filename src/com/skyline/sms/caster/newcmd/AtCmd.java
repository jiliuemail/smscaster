package com.skyline.sms.caster.newcmd;

import java.util.ArrayList;
import java.util.List;

public  class AtCmd implements Cmd {
	List<byte[]> list;
	
	//适合单命令的
	public AtCmd(String value){
		new ArrayList<byte[]>().add(value.getBytes());
	}
	
	//适合多次写入的at命令
	public AtCmd(List<byte[]> list){
		this.list=list;
	}
	
	

	
	@Override
	public List<byte[]> getValue() {
		// TODO Auto-generated method stub
		return list;
	}

//	protected abstract void setValue(List<byte[]> content);

}

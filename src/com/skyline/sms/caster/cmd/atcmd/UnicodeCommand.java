package com.skyline.sms.caster.cmd.atcmd;

import com.skyline.sms.caster.cmd.Command;

public class UnicodeCommand implements Command {
	
	private String value="";
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private String toUnicode(){
		
	    StringBuffer unicode = new StringBuffer();
		 
	    for (int i = 0; i < value.length(); i++) {
	 
	        // 取出每一个字符
	        char c = value.charAt(i);
	 
	        // 转换为unicode
	        unicode.append( Integer.toHexString(c));
	    }
	 
	    return unicode.toString();

	}

	@Override
	public String check() {
		return toUnicode();
	}

	@Override
	public String get() {
		return toUnicode();
	}

	@Override
	public String set() {
		return toUnicode();
	}

	@Override
	public byte[] stream() {
		// TODO Auto-generated method stub
		return null;
	}

}

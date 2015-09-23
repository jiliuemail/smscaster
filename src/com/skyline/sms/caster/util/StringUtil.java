package com.skyline.sms.caster.util;

public class StringUtil {
	
	private StringUtil(){}
	
	public static boolean hasText(String text){
		return text != null && (text.trim().length() > 0);
	}

}

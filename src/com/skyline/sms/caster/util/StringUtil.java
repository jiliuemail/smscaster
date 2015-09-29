package com.skyline.sms.caster.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static String toUnicode(String str){
	
		    StringBuffer unicode = new StringBuffer();
			 
		    for (int i = 0; i < str.length(); i++) {
		 
		        // 取出每一个字符
		        char c = str.charAt(i);
		 
		        // 转换为unicode
		        String hexString=Integer.toHexString(c);

		        if(Integer.parseInt(hexString, 16)>=0 && Integer.parseInt(hexString, 16)<256){hexString="00"+hexString;}
		        unicode.append(hexString);
		  
		    }
		 
		    return unicode.toString();


		
	}
	
	public static boolean hasText(String text){
		return text != null && (text.trim().length() > 0);
	}
	
	public static String concattValue(Integer...values){
		List<Integer> paramList = new ArrayList<Integer>();
		for (int i = 0; i < values.length; i++) {
			Integer value = values[i];
			if (value != null) {
				paramList.add(value);
			}
		}
		String param = paramList.toArray().toString(); //这里有问题,返回的类似:[Ljava.lang.Object;@2bdcd04

		if (StringUtil.hasText(param)) {
			param = param.substring(0,param.length()-1);
		}
		return param;
	}
	
	public static String concattValue(String...values){
		List<String> paramList = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			if (StringUtil.hasText(value)) {
				paramList.add(value);
			}
		}
		String param = paramList.toArray().toString();  
		if (StringUtil.hasText(param)) {
			param = param.substring(1,param.length()-1);
		}
		return param;
	}
}



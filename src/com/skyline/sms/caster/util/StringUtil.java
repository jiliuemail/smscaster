<<<<<<< HEAD
package com.skyline.sms.caster.util;

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
}



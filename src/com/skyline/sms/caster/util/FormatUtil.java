package com.skyline.sms.caster.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private FormatUtil(){}
	
	public static String formatToString(Object target){
		if (target == null) {
			return "null";
		}
		Class valueType = target.getClass();
		if (Date.class.equals(valueType)) {
			return DATE_FORMAT.format(target);
		}else {
			return target.toString();
		}
	}

	
}

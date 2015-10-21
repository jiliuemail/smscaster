package com.skyline.sms.caster.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.skyline.sms.caster.core.MessageBundle;

public class FormatUtil {
	
	public static DateFormat DATE_FORMAT;
	static{
		DATE_FORMAT = new SimpleDateFormat(MessageBundle.getMessage("sms.caster.constants.format.date"));
	}
	
	private FormatUtil(){}
	
	public static String formatToString(Object target){
		if (target == null) {
			return "";
		}
		Class<?> valueType = target.getClass();
		if (Date.class.isAssignableFrom(valueType)) {
			return DATE_FORMAT.format(target);
		}else {
			return target.toString();
		}
	}

	public static Date formatToDate(String target) throws ParseException{
		return DATE_FORMAT.parse(target);
	}
}

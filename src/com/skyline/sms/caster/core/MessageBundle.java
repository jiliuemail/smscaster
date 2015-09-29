package com.skyline.sms.caster.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.skyline.sms.caster.util.IOUtil;

public class MessageBundle
{
	private static final String FILE_NAME_PREFIX = "message";
	private static final String FILE_NAME_EXTENSION = ".properties";
	private static final String UNDER_LINE = "_";
	
	
	private static ResourceBundle resourceBundle;
	
	
	public static void buildMessageBundle(Locale locale) throws FileNotFoundException, IOException{
		InputStream input = null;
		try {
			input = findResourceFileAsInputStream(locale);
			resourceBundle = new PropertyResourceBundle(input);
		}finally{
			IOUtil.quietCloseStream(input);
		}
	}
	
	
	private static InputStream findResourceFileAsInputStream(Locale locale) throws FileNotFoundException{
		if (locale == null) {
			locale = Locale.getDefault();
		}
		StringBuilder fnb = new StringBuilder();
		fnb.append(FILE_NAME_PREFIX).append(UNDER_LINE)
			.append(locale.getLanguage()).append(UNDER_LINE).append(locale.getCountry()).append(FILE_NAME_EXTENSION);
		InputStream inputStream = IOUtil.getResource(fnb.toString());
		
		if (inputStream == null) {
			inputStream = IOUtil.getResource(FILE_NAME_PREFIX + FILE_NAME_EXTENSION);
		}
		if (inputStream == null) {
			throw new FileNotFoundException("Can not found message resource file : " + FILE_NAME_PREFIX + FILE_NAME_EXTENSION);
		}
		return inputStream;
	}
	
	
	public static String getMessage(String messageKey) {
		try {
			return resourceBundle.getString(messageKey);
		} catch (MissingResourceException e) {
			return messageKey;
		}
	}
	
	public static String getMessage(String messageKey, Object ... arguments) {
		try {
			return MessageFormat.format(resourceBundle.getString(messageKey), arguments);
		} catch (MissingResourceException e) {
			return messageKey;
		}
	}

}
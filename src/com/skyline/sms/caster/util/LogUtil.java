package com.skyline.sms.caster.util;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

/**
 * 
 * 打印日志的工具类
 *
 */
public class LogUtil {
	
	private LogUtil(){}
	
	private static Logger logger = Logger.getLogger(LogUtil.class);
	
	public static void debug(Exception exception){
		if (logger.isDebugEnabled()) {
			logger.debug(exception.getMessage(), exception);
		}
	}
	
	public static void trace(Exception exception){
		if (logger.isTraceEnabled()) {
			logger.trace(exception.getMessage(), exception);
		}
	}
	
	public static void info(Exception exception){
		if (logger.isInfoEnabled()) {
			logger.info(exception.getMessage(), exception);
		}
	}
	
	public static void warn(Exception exception){
		logger.warn(exception.getMessage(), exception);
	}

	public static void error(Exception exception){
		logger.error(exception.getMessage(), exception);
	}
	
	
	public static void debug(String message){
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}
	
	public static void trace(String message){
		if (logger.isTraceEnabled()) {
			logger.trace(message);
		}
	}
	
	public static void info(String message){
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}
	
	public static void warn(String message){
		logger.warn(message);
	}

	public static void error(String message){
		logger.error(message);
	}
	
	public static void debug(String message, Object...arguments){
		if (logger.isDebugEnabled()) {
			logger.debug(MessageFormat.format(message, arguments));
		}
	}
	
	public static void trace(String message, Object...arguments){
		if (logger.isTraceEnabled()) {
			logger.trace(MessageFormat.format(message, arguments));
		}
	}
	
	public static void info(String message, Object...arguments){
		if (logger.isInfoEnabled()) {
			logger.info(MessageFormat.format(message, arguments));
		}
	}
	
	public static void warn(String message, Object...arguments){
		logger.warn(MessageFormat.format(message, arguments));
	}

	public static void error(String message, Object...arguments){
		logger.error(MessageFormat.format(message, arguments));
	}
}

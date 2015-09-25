package com.skyline.sms.caster.util;

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
}

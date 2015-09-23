package com.skyline.sms.caster.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO 工具类
 *
 */
public class IOUtil {
	
	private static ClassLoader classLoader ;
	
	static{
		classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = IOUtil.class.getClassLoader();
		}
	}

	private IOUtil() {}
	
	/**
	 * 关闭IO流，如果有异常则抛出
	 * @param stream IO流
	 * @throws IOException
	 */
	public static void closeStream(Closeable stream) throws IOException{
		if (stream != null) {
			stream.close();
		}
	}
	
	/**
	 * 关闭IO流，打印异常
	 * @param stream IO流
	 */
	public static void quietCloseStream(Closeable stream){
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				LogUtil.error(e);
			}
		}
	}

	/**
	 * 获取文件流
	 * @param filePath
	 * @return
	 */
	public static InputStream getResource(String filePath){
		return classLoader.getResourceAsStream(filePath);
	}
}

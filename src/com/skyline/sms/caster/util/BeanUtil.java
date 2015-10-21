package com.skyline.sms.caster.util;

/**
 * Bean对象的工具类
 * 
 * @author linyn
 *
 * @since 2015年10月20日
 */
public class BeanUtil {
	
	private BeanUtil(){}
	
	
	/**
	 * 判断两个对象是否相同
	 * @param src
	 * @param dest
	 * @return true：相同，false：不同
	 */
	public static boolean equals(Object src, Object dest){
		if (src == null && dest == null) {return true;}
		if (src == null) {return false;}
		if (dest == null) {return false;}
		return src.equals(dest);
	}
	

}

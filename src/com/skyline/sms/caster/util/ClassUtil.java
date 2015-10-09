package com.skyline.sms.caster.util;

import java.lang.reflect.Method;

public class ClassUtil {
	
	private ClassUtil(){}
	
	public static Object getPropertyValue(Object target, String propertyName){
		if (!StringUtil.hasText(propertyName)) {
			return null;
		}
		StringBuilder getMethodName = new StringBuilder();
		getMethodName.append("get").append(propertyName.substring(0, 1).toUpperCase())
			.append(propertyName.substring(1));
		Class<?> targetClass = target.getClass();
		try {
			Method getMethod = targetClass.getMethod(getMethodName.toString(), new Class[]{});
			return getMethod.invoke(target, new Object[]{});
		} catch (NoSuchMethodException e) {
			LogUtil.warn(e);
			return null;
		} catch (Exception e) {
			LogUtil.error(e);
			return null;
		}
	}
	
	public static void setPropertyValue(Object target, String propertyName, Object value){
		if (!StringUtil.hasText(propertyName)) {
			return ;
		}
		StringBuilder setMethodName = new StringBuilder();
		setMethodName.append("set").append(propertyName.substring(0, 1).toUpperCase())
			.append(propertyName.substring(1));
		Class<?> targetClass = target.getClass();
		try {
			Method[] methods = targetClass.getMethods();
			Method setMethod = null;
			for (Method method : methods) {
				if (method.getName().equals(setMethodName.toString())) {
					setMethod = method;
					break;
				}
			}
			if (setMethod != null) {
				setMethod.invoke(target, new Object[]{value});
			}
		}  catch (Exception e) {
			LogUtil.error(e);
		}
	}

}

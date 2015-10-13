package com.skyline.sms.caster.util;

import java.util.Collection;

public class CollectionUtil {
	
	private CollectionUtil(){}
	
	public static boolean hasElements(Collection<?> collection){
		return (collection != null && !collection.isEmpty());
	}

}

package com.skyline.sms.caster.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionUtil {
	
	private CollectionUtil(){}
	
	public static boolean hasElements(Collection<?> collection){
		return (collection != null && !collection.isEmpty());
	}

	public static <E> List<E> setToList(Set<E> set){
		List<E> list = new ArrayList<E>();
		if (hasElements(set)) {
			list.addAll(set);
		}
		return list;
	}
	
	public static <E> void putElement(Collection<E> collection, E element){
		if (collection == null || element == null) {
			return;
		}
		if (collection.contains(element)) {
			return;
		}
		collection.add(element);
	}
	
	public static <E> void putCollection(Collection<E> srCollection, Collection<E> newCollection){
		if (srCollection == null || newCollection == null) {
			return;
		}
		if (hasElements(newCollection)) {
			for (E e : newCollection) {
				putElement(srCollection, e);
			}
		}
	}
}

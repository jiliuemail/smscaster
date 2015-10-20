package com.skyline.sms.caster.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.skyline.sms.caster.collection.SetList;

/**
 * 集合的工具类
 * 
 * @author linyn
 *
 * @since 2015年10月10日
 */
public class CollectionUtil {
	
	private CollectionUtil(){}
	
	/**
	 * 判断集合中是否有元素
	 * @param collection 集合
	 * @return true：存在元素，false：是个空集合
	 */
	public static boolean hasElements(Collection<?> collection){
		return (collection != null && !collection.isEmpty());
	}
	
	/**
	 * 判断是否空集合
	 * @param collection 集合
	 * @return true：是个空集合，false：存在元素
	 * @see #hasElements(Collection)
	 */
	public static boolean isEmpty(Collection<?> collection){
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 将一个Set转成List
	 * @param set
	 * @return List
	 */
	public static <E> List<E> setToList(Set<E> set){
		return new SetList<E>(set);
	}
	
	/**
	 * 向集合中添加元素，如果元素已经存在则不添加
	 * @param collection
	 * @param element
	 * @return true：添加成功，false：没有添加
	 */
	public static <E> boolean putElement(Collection<E> collection, E element){
		if (collection == null || element == null) {
			return false;
		}
		if (collection.contains(element)) {
			return false;
		}
		collection.add(element);
		return true;
	}
	
	/**
	 * 向集合中添加一个集合，如果元素已经存在则不添加
	 * @param srcCollection 原集合
	 * @param newCollection 需要添加的集合
	 * @return true：添加成功，false：没有添加
	 * @see #putElement(Collection, Object)
	 */
	public static <E> boolean putCollection(Collection<E> srcCollection, Collection<E> newCollection){
		if (srcCollection == null || newCollection == null) {
			return false;
		}
		boolean updated = false;
		if (hasElements(newCollection)) {
			for (E e : newCollection) {
				updated = putElement(srcCollection, e) || updated;
			}
		}
		return updated;
	}
	
	/**
	 * 判断两个集合是否相同
	 * @param src
	 * @param dest
	 * @return true：相同，false：不同
	 */
	public static <E> boolean equals(Collection<E> src, Collection<E> dest){
		if (src == null && dest == null) {return true;}
		if (src == null) {return false;}
		if (dest == null) {return false;}
		if (src.size() != dest.size()) {return false;}
		boolean equal = true;
		for (E e : dest) {
			equal = equal && src.contains(e);
			if(!equal) break;
		}
		return equal;
	}
}

package com.skyline.sms.caster.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class SetList<E> implements Set<E>, List<E> {
	
	private Set<E> elements;
	
	private List<E> list;
	
	public SetList(Set<E> set) {
		if (set == null) {
			elements = new HashSet<E>();
			list = new ArrayList<E>();
		}else {
			elements = set;
			list = new ArrayList<E>(elements);
		}
	}
	
	public SetList(List<E> list) {
		if (list == null) {
			this.list = new ArrayList<E>();
			elements = new HashSet<E>();
		}else{
			this.list = list;
			elements = new HashSet<E>(this.list);
			this.list.clear();
			this.list.addAll(elements);
		}
	}

	@Override
	public synchronized int size() {
		return elements.size();
	}

	@Override
	public synchronized boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public synchronized boolean contains(Object o) {
		return elements.contains(o);
	}

	@Override
	public synchronized Iterator<E> iterator() {
		return elements.iterator();
	}

	public synchronized Object[] toArray() {
		return elements.toArray();
	}

	public synchronized <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}

	public synchronized boolean add(E e) {
		return elements.add(e) && list.add(e);
	}

	public synchronized boolean remove(Object o) {
		return elements.remove(o) && list.remove(o);
	}

	public synchronized boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	public synchronized boolean addAll(Collection<? extends E> c) {
		boolean modified = elements.addAll(c);
		if (modified) {
			list.clear();
			modified =list.addAll(elements);
		}
		return modified;
	}

	public synchronized boolean retainAll(Collection<?> c) {
		return elements.retainAll(c) && list.retainAll(c);
	}

	public synchronized boolean removeAll(Collection<?> c) {
		return elements.removeAll(c) && list.removeAll(c);
	}

	public synchronized void clear() {
		elements.clear();
		list.clear();
	}

	public synchronized boolean equals(Object o) {
		return elements.equals(o);
	}

	public synchronized int hashCode() {
		return elements.hashCode();
	}

	public synchronized boolean addAll(int index, Collection<? extends E> c) {
		return addAll(c);
	}

	public synchronized E get(int index) {
		return list.get(index);
	}

	public synchronized E set(int index, E element) {
		elements.add(element);
		return list.set(index, element);
	}

	public synchronized void add(int index, E element) {
		boolean modifed = elements.add(element);
		if (modifed) {
			list.add(index, element);
		}
	}

	public synchronized E remove(int index) {
		E oldElement = list.get(index);
		boolean modifed = elements.remove(oldElement);
		if (modifed) {
			return list.remove(index);
		}
		return null;
	}

	public synchronized int indexOf(Object o) {
		return list.indexOf(o);
	}

	public synchronized int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public synchronized ListIterator<E> listIterator() {
		return list.listIterator();
	}

	public synchronized ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}


	

}

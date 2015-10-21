package com.skyline.sms.caster.ui.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListDataListener;

import com.skyline.sms.caster.util.CollectionUtil;

/**
 * 保存List数据的列表
 * 
 * @author linyn
 *
 * @since 2015年10月14日
 */
public class DataList<E> extends JList<E> {
	
	private DataListModel<E> listModel;
	
	public DataList(){
		this(null);
	}

	public DataList(List<E> data) {
		if (data == null) {
			data = new ArrayList<E>();
		}
		listModel = new DataListModel<E>();
		setModel(listModel);
		setData(data);
	}
	
	/**
	 * 设置列表中的数据
	 * @param data
	 */
	public void setData(List<E> data){
		listModel.setData(data);
		updateUI();
	}
	
	/**
	 * 获取列表中的数据
	 * @return
	 */
	public List<E> getData(){
		return listModel.getData();
	}
	
	/**
	 * 添加列表中的单个数据项
	 * @param item
	 */
	public void addItem(E item){
		listModel.addItem(item);
		updateUI();
	}
	
	/**
	 * 添加列表中的多个数据项
	 * @param items
	 */
	public void addItems(List<E> items){
		listModel.addItems(items);
		updateUI();
	}
	
	/**
	 * 删除列表中的单个数据项
	 * @param item
	 */
	public void removeItem(E item){
		listModel.removeItem(item);
		updateUI();
	}
	
	public void removeAllItem(){
		listModel.removeAllItem();
		updateUI();
	}

	public boolean isEmpty(){
		return listModel.getData().isEmpty();
	}
	
	public void addListDataListener(ListDataListener l){
		listModel.addListDataListener(l);
	}
	
	class DataListModel<T> extends AbstractListModel<T>{
		
		private List<T> data;
		
		public void setData(List<T> data){
			this.data = data;
			if (CollectionUtil.hasElements(data)) {
				int lastIndex = data.size() - 1;
				fireContentsChanged(this, 0, lastIndex);
			}
		}
		
		public List<T> getData() {
			return data;
		}
		
		public void addItem(T item){
			if(CollectionUtil.putElement(data, item)){
				int itemIndex = data.indexOf(item);
				fireIntervalAdded(this, itemIndex, itemIndex);
			}
		}
		
		public void addItems(List<T> items){
			if(CollectionUtil.putCollection(data, items)){
				int firstIndex = data.indexOf(items.get(0));
				int lastIndex = data.indexOf(items.get(items.size()-1));
				fireIntervalAdded(this, firstIndex, lastIndex);
			}
		}
		
		public void removeItem(T item){
			int itemIndex = data.indexOf(item);
			if(data.remove(item)){
				fireIntervalRemoved(this, itemIndex, itemIndex);
			}
		}
		
		public void removeAllItem(){
			if (data.isEmpty()) {
				return;
			}
			int lastIndex = data.size() - 1;
			data.clear();
			fireIntervalRemoved(this, 0, lastIndex);
		}

		public int getSize(){
			 return data.size();
		}
		
		public T getElementAt(int index){
			if (index < 0 || index >= getSize()) {
				return null;
			}
			return data.get(index);
		}
	}

}

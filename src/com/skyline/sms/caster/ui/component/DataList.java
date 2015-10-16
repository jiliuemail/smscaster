package com.skyline.sms.caster.ui.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import com.skyline.sms.caster.util.CollectionUtil;

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
	
	public void setData(List<E> data){
		listModel.setData(data);
		updateUI();
	}
	
	public List<E> getData(){
		return listModel.getData();
	}
	
	public void addItem(E item){
		CollectionUtil.putElement(listModel.getData(), item);
		updateUI();
	}
	
	public void addItems(List<E> items){
		CollectionUtil.putCollection(listModel.getData(), items);
		updateUI();
	}
	
	public void removeItem(E item){
		listModel.getData().remove(item);
		updateUI();
	}
	
	public void removeAllItem(){
		listModel.getData().clear();
		updateUI();
	}

	public boolean isEmpty(){
		return listModel.getData().isEmpty();
	}
	
	class DataListModel<T> extends AbstractListModel<T>{
		
		private List<T> data;
		
		public void setData(List<T> data){
			this.data = data;
		}
		
		public List<T> getData() {
			return data;
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

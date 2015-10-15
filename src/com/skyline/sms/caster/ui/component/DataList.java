package com.skyline.sms.caster.ui.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

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
		setData(data);
		setModel(listModel);
	}
	
	public void setData(List<E> data){
		listModel.setData(data);
	}
	
	

	class DataListModel<T> extends DefaultListModel<T>{
		
		private List<T> data;
		
		public void setData(List<T> data){
			this.data = data;
		}
		
		  public int getSize(){
			  return data.size();
		  }

		  public T getElementAt(int index){
			  return data.get(index);
		  }
		
	}

}

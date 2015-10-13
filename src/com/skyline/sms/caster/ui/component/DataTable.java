package com.skyline.sms.caster.ui.component;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.util.ClassUtil;

public class DataTable<T> extends JTable {
	
	private List<T> updateRecords;
	
	private DataTabelMedel tabelMedel;
	
	public DataTable(List<String> columnNames, List<String> fields){
		this(null, columnNames, fields);
	}
	
	public DataTable(List<T> data, List<String> columnNames, List<String> fields){
		updateRecords = new ArrayList<T>();
		tabelMedel = new DataTabelMedel();
		setData(data);
		selectColumns(columnNames);
		setColumnFields(fields);
		setModel(tabelMedel);
	}
	
	public void setData(List<T> data){
		tabelMedel.setData(data);
	}
	
	public void selectColumns(List<String> columnNames){
		tabelMedel.selectColumns(columnNames);
	}

	public List<String> getColumnNames(){
		return tabelMedel.getColumnNames();
	}
	
	public void setColumnFields(List<String> fields){
		tabelMedel.setColumnFields(fields);
	}
	
	public List<T> getUpdateRecords() {
		return updateRecords;
	}

	public void clearUpdateRecords(){
		updateRecords.clear();
	}

	class DataTabelMedel extends DefaultTableModel{ // AbstractTableModel{

		private List<T> data;
		private List<String> columnMetas;
		private List<String> columnFields;
		
		public void setData(List<T> data){
			this.data = data;
		}
		
		public void selectColumns(List<String> columnNames){
			columnMetas = columnNames;
		}

		public List<String> getColumnNames(){
			return columnMetas;
		}
		
		
		public void setColumnFields(List<String> columnFields) {
			this.columnFields = columnFields;
		}

		@Override
		public String getColumnName(int column) {
			return MessageBundle.getMessage(columnMetas.get(column));
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public int getColumnCount() {
			return columnMetas.size();
		}
		

		public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
			if (newValue == null) {
				return;
			}
			Object oldValue = getValueAt(rowIndex, columnIndex);
			if (newValue.equals(oldValue)) {
				return;
			}
			T target = data.get(rowIndex);
			ClassUtil.setPropertyValue(target, columnFields.get(columnIndex), newValue);
			if (!updateRecords.contains(target)) {
				updateRecords.add(target);
			}
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = ClassUtil.getPropertyValue(data.get(rowIndex), columnFields.get(columnIndex));
			return (value == null ? "" : value);
		}
		
	}
}

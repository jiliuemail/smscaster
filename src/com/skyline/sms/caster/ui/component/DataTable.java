package com.skyline.sms.caster.ui.component;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.ui.data.DataStorer;
import com.skyline.sms.caster.util.ClassUtil;
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.FormatUtil;

public class DataTable<T> extends JTable {
	
	private List<T> updateRecords;
	
	private DataTabelMedel tabelMedel;
	
	private int lastEditRowIndex;
	
	private DataStorer<T> dataStorer;
	
	public DataTable(List<String> columnNames, List<String> fields){
		this(null, columnNames, fields);
	}
	
	public DataTable(List<T> data, List<String> columnNames, List<String> fields){
		updateRecords = new ArrayList<T>();
		tabelMedel = new DataTabelMedel();
		setDefaultRenderer(Date.class, new DateTableCellRenderer());
		setDefaultRenderer(Timestamp.class, new DateTableCellRenderer());
		setData(data);
		selectColumns(columnNames);
		setColumnFields(fields);
		setModel(tabelMedel);
	}
	
	public void setData(List<T> data){
		if (data == null) {
			data = new ArrayList<T>();
		}
		tabelMedel.setData(data);
		updateUI();
	}
	
	public T getRowData(int row){
		return tabelMedel.getData().get(row);
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
	
	public void addDataStorer(DataStorer<T> dataStorer){
		this.dataStorer = dataStorer;
	}
	
	protected List<T> getUpdateRecords() {
		return updateRecords;
	}

	protected void clearUpdateRecords(){
		updateRecords.clear();
	}
	
	public void notifyUpdateRecords(){
		T updateRowData = tabelMedel.getData().get(lastEditRowIndex);
		CollectionUtil.putElement(updateRecords, updateRowData);
		updateUI();
		
		if (dataStorer != null && dataStorer.updateData(updateRecords)) {
			clearUpdateRecords();
		}
			
	}
	
	public void addNewRecord(T rowData){
		tabelMedel.addRowData(rowData);
		lastEditRowIndex = tabelMedel.getData().indexOf(rowData);
		updateUI();
	}
	
	public void cancelNewRecord(T rowData){
		if (rowData == null) {
			return ;
		}
		tabelMedel.getData().remove(rowData);
		lastEditRowIndex = -1;
		updateUI();
	}
	
	public boolean editCellAt(int row, int column, EventObject e) {
		lastEditRowIndex = row;
		return super.editCellAt(row, column, e);
	}

	

	public int getLastEditRowIndex() {
		return lastEditRowIndex;
	}

	
	class DateTableCellRenderer extends DefaultTableCellRenderer {
		protected void setValue(Object value) {
			setText(FormatUtil.formatToString(value));
		}
	}


	class DataTabelMedel extends DefaultTableModel{ // AbstractTableModel{

		private List<T> data;
		private List<String> columnMetas;
		private List<String> columnFields;
		
		private Class<?> entityClass;
		
		public void setData(List<T> data){
			this.data = data;
			if (getRowCount() > 0) {
				entityClass = data.get(0).getClass();
			}else{
				entityClass = Object.class;
			}
		}
		
		public void addRowData(T rowData){
			if (rowData == null) {
				return;
			}
			if (getRowCount() == 0) {
				entityClass = rowData.getClass();
			}
			data.add(rowData);
		}
		
		public List<T> getData() {
			return data;
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
		
		public Class<?> getColumnClass(int columnIndex) {
			Class<?> columnClass = ClassUtil.getPropertyType(entityClass, columnFields.get(columnIndex));
			return (columnClass == null ? Object.class : columnClass);
					
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

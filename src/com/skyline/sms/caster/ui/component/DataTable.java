package com.skyline.sms.caster.ui.component;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.ui.data.DataStorer;
import com.skyline.sms.caster.util.ClassUtil;
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.FormatUtil;

/**
 * 支持存放List数据的JTable
 * 
 * @author linyn
 *
 * @since 2015年9月28日
 */
public class DataTable<T> extends JTable {
	
	
	private DataTabelMedel tabelMedel;
	
	
	public DataTable(List<String> columnNames, List<String> fields){
		this(null, columnNames, fields);
	}
	
	public DataTable(List<T> data, List<String> columnNames, List<String> fields){
		tabelMedel = new DataTabelMedel(data, columnNames, fields);
		setDefaultRenderer(Date.class, new DateTableCellRenderer());
		setDefaultRenderer(Timestamp.class, new DateTableCellRenderer());
		setModel(tabelMedel);
	}
	
	/**
	 * 设置表格中的数据
	 * @param data
	 */
	public void setData(List<T> data){
		if (data == null) {
			data = new ArrayList<T>();
		}
		tabelMedel.setData(data);
		updateUI();
	}
	
	public T getRowData(int row){
		if (row < 0) {
			return null;
		}
		return tabelMedel.getData().get(row);
	}
	
	public void selectColumns(List<String> columnNames){
		tabelMedel.setColumnNames(columnNames);
		updateUI();
	}

	public List<String> getColumnNames(){
		return tabelMedel.getColumnNames();
	}
	
	public void setColumnFields(List<String> fields){
		tabelMedel.setColumnFields(fields);
		updateUI();
	}
	
	public void setDataStorer(DataStorer<T> dataStorer){
		tabelMedel.setDataStorer(dataStorer);
	}
	
	/**
	 * 返回当前选择行数据
	 * @return 当前选择行数据，如果没有选择行则返回NULL
	 */
	public T getSelectedRowData(){
		return getRowData(getSelectedRow());
	}
	
	/**
	 * 通知更新此行数据
	 * @param rowData
	 */
	public void notifyUpdateRowData(T rowData){
		tabelMedel.updateRowData(rowData);
		updateUI();
	}
	
	/**
	 * 通知更新所有已经修改的数据
	 * 如果设置了 DataStorer，数据库的数据会被同时更新
	 * @param rowData
	 */
	public void notifyUpdateRowDatas(){
		tabelMedel.notifyUpdateData();
		updateUI();
	}
	
	/**
	 * 通知添加此行数据（新数据）
	 * @param rowData
	 */
	public void notifyAddRowData(T rowData){
		tabelMedel.addRowData(rowData);
		updateUI();
	}
	
	/**
	 * 通知取消此行数据（新数据），取消添加新数据
	 * @param rowData
	 */
	public void notifyCancelRowData(T rowData){
		tabelMedel.cancelRowData(rowData);
		updateUI();
	}
	
	/**
	 * 通知删除此行数据
	 * 如果设置了 DataStorer，数据库的数据会被删除
	 * @param rowData
	 */
	public void notifyDeleteRowData(T rowData){
		tabelMedel.removeRowData(rowData);
		updateUI();
	}

	public List<T> getData() {
		return tabelMedel.getData();
	}

	public DataStorer<T> getDataStorer() {
		return tabelMedel.getDataStorer();
	}

	/**
	 * 根据列号设置该列是否可以编辑
	 * @param columnIndex 列号
	 * @param editable true：可编辑， false：不可编辑
	 */
	public void setColumnEditable(int columnIndex, boolean editable) {
		tabelMedel.setColumnEditable(columnIndex, editable);
	}

	/**
	 * 根据列名设置该列是否可以编辑
	 * @param fieldName 列名
	 * @param editable true：可编辑， false：不可编辑
	 */
	public void setColumnEditable(String fieldName, boolean editable) {
		tabelMedel.setColumnEditable(fieldName, editable);
	}

	/**
	 * 根据列号获取该列是否可以编辑
	 * @param columnIndex 列号
	 * @return true：可编辑， false：不可编辑
	 */
	public boolean getColumnEditable(int columnIndex) {
		return tabelMedel.getColumnEditable(columnIndex);
	}

	/**
	 * 根据列名获取该列是否可以编辑
	 * @param fieldName 列名
	 * @return true：可编辑， false：不可编辑
	 */
	public boolean getColumnEditable(String fieldName) {
		return tabelMedel.getColumnEditable(fieldName);
	}

	public String getColumnName(int column) {
		return tabelMedel.getColumnName(column);
	}

	/**
	 * 获取行数
	 */
	public int getRowCount() {
		return tabelMedel.getRowCount();
	}

	/**
	 * 获取列数
	 */
	public int getColumnCount() {
		return tabelMedel.getColumnCount();
	}
	
	/**
	 * 获取当前表格是否可以编辑
	 * @return  <code>true<code>:可编辑，默认值，<code>false<code>:不可以编辑（只读）
	 */
	public boolean isEditable(){
		return tabelMedel.isEditable();
	}
	
	/**
	 * 设置当前表格是否可以被编辑
	 * @param editable <code>true<code>:可编辑，默认值，<code>false<code>:不可以编辑（只读）
	 */
	public void setEditable(boolean editable) {
		tabelMedel.setEditable(editable);
	}
	
	public void addTableModelListener(TableModelListener l) {
		tabelMedel.addTableModelListener(l);
	}
	
	/**
	 * 固定列宽
	 * @param columnIndex 列号
	 * @param columnWidth 列宽
	 */
	public void setColumWidth(int columnIndex, int columnWidth){
		if (columnIndex < 0 || columnIndex > getColumnCount()) {
			return;
		}else{
			TableColumn column = getColumnModel().getColumn(columnIndex);
			column.setMaxWidth(columnWidth);
			column.setMinWidth(columnWidth);
			repaint();
		}
	}
	
	/**
	 * 获取列宽
	 * @param columnIndex 列号
	 * @return 列宽
	 */
	public int getColumnWidth(int columnIndex){
		if (columnIndex < 0 || columnIndex > getColumnCount()) {
			return 0;
		}else{
			return getColumnModel().getColumn(columnIndex).getWidth();
		}
	}
	
//////////////////////
//	TableModel		
//////////////////////
	
	class DateTableCellRenderer extends DefaultTableCellRenderer {
		protected void setValue(Object value) {
			setText(FormatUtil.formatToString(value));
		}
	}
	

	class DataTabelMedel extends AbstractTableModel{

		private List<T> data;
		private List<T> updateRecords;
		private List<String> columnMetas;
		private List<String> columnFields;
		
		private Class<?> entityClass;
		private DataStorer<T> dataStorer;
		
		private boolean editable = true;
		private Map<String, Boolean> editableMap;
		
		public DataTabelMedel(List<T> data, List<String> columnNames, List<String> fields) {
			updateRecords = new ArrayList<T>();
			editableMap = new HashMap<String, Boolean>();
			setColumnNames(columnNames);
			setColumnFields(fields);
			setData(data);
		}
		
		public void setData(List<T> data){
			this.data = data;
			if (getRowCount() > 0) {
				entityClass = data.get(0).getClass();
			}else{
				entityClass = Object.class;
			}
			fireTableDataChanged();
		}
		
		public void addRowData(T rowData){
			int rowIndex = putRowData(rowData);
			fireTableRowsInserted(rowIndex, rowIndex);
		}
		
		public void removeRowData(T rowData){
			if (rowData == null) {
				return ;
			}
			int rowIndex = data.indexOf(rowData);
			data.remove(rowData);
			updateRecords.remove(rowData);
			notifyDeleteData(rowData);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}
		
		public void cancelRowData(T rowData){
			if (rowData == null) {
				return ;
			}
			int rowIndex = data.indexOf(rowData);
			data.remove(rowData);
			updateRecords.remove(rowData);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}
		
		public void updateRowData(T rowData){
			int rowIndex = putRowData(rowData);
			notifyUpdateData();
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
		
		protected int putRowData(T rowData){
			if (rowData == null) {
				return -1;
			}
			if (getRowCount() == 0) {
				entityClass = rowData.getClass();
			}
			CollectionUtil.putElement(updateRecords, rowData);
			CollectionUtil.putElement(data, rowData);
			return data.indexOf(rowData);
		}
		
		public void notifyUpdateData(){
			if (dataStorer != null && dataStorer.updateData(updateRecords)) {
				updateRecords.clear();
			}
		}
		
		private void notifyDeleteData(T rowData){
			if (dataStorer != null) {
				dataStorer.deleteData(rowData);
			}
		}
		
		public List<T> getData() {
			return data;
		}

		public DataStorer<T> getDataStorer() {
			return dataStorer;
		}

		public void setDataStorer(DataStorer<T> dataStorer) {
			this.dataStorer = dataStorer;
		}

		public boolean isEditable() {
			return editable;
		}

		public void setEditable(boolean editable) {
			this.editable = editable;
		}
		
		public void setColumnEditable(int columnIndex, boolean editable){
			if (columnIndex < 0 || columnIndex >= getColumnCount()) {
				return;
			}
			String columnField = columnFields.get(columnIndex);
			editableMap.put(columnField, editable);
		}
		
		public void setColumnEditable(String fieldName, boolean editable){
			setColumnEditable(columnFields.indexOf(fieldName), editable);
		}
		
		public boolean getColumnEditable(int columnIndex){
			if (columnIndex < 0 || columnIndex >= getColumnCount()) {
				return false;
			}
			Boolean columnEditable = editableMap.get(columnFields.get(columnIndex));
			return (columnEditable == null ? editable : columnEditable) ;
		}
		
		public boolean getColumnEditable(String fieldName){
			return getColumnEditable(columnFields.indexOf(fieldName));
		}

		public void setColumnNames(List<String> columnNames){
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
			return columnFields.size();
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// 暂时不支持修改日期，因为注册日期或创建日期都是不可以修改的
			if (Date.class.isAssignableFrom(getColumnClass(columnIndex))) {
				return false;
			}
			return getColumnEditable(columnIndex);
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
		
		public void fireTableDataChanged(){
			fireTableChanged(new TableModelEvent(this, 0,  getRowCount() - 1, columnFields.size()));
		}
		
	}
	

}

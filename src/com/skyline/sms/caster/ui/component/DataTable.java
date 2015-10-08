package com.skyline.sms.caster.ui.component;


import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.skyline.sms.caster.util.ClassUtil;

public class DataTable<T> extends JTable {
	
	
	private DataTabelMedel tabelMedel;
	
	public DataTable(List<String> columnNames){
		tabelMedel = new DataTabelMedel();
		selectColumns(columnNames);
		setModel(tabelMedel);
	}
	
	public DataTable(List<T> data, List<String> columnNames){
		tabelMedel = new DataTabelMedel();
		setData(data);
		selectColumns(columnNames);
		setModel(tabelMedel);
	}
	
	private void intTableHeader(){
		//JTableHeader tableHeader = getTableHeader();
		//tableHeader.
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
	
	class DataTabelMedel extends DefaultTableModel{ // AbstractTableModel{

		private List<T> data;
		private List<String> columnMetas;
		
		public void setData(List<T> data){
			this.data = data;
		}
		
		public void selectColumns(List<String> columnNames){
			columnMetas = columnNames;
		}

		public List<String> getColumnNames(){
			return columnMetas;
		}
		
		
		
		@Override
		public String getColumnName(int column) {
			return columnMetas.get(column);
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public int getColumnCount() {
			return columnMetas.size();
		}
		
		

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = ClassUtil.getPropertyValue(data.get(rowIndex), columnMetas.get(columnIndex));
			return (value == null ? "" : value);
		}
		
	}
}

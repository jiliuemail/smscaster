package com.skyline.sms.caster.ui.content;


import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.skyline.sms.caster.util.ClassUtil;

public class DataTable<T> extends JTable {
	
	
	private DataTabelMedel tabelMedel;
	
	public DataTable(){
		tabelMedel = new DataTabelMedel();
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
	
	class DataTabelMedel extends AbstractTableModel{

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
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return columnMetas.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = ClassUtil.getPropertyValue(data.get(rowIndex), columnMetas.get(columnIndex));
			return (value == null ? "" : value);
		}
		
	}
}

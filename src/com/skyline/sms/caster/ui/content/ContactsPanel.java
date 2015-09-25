package com.skyline.sms.caster.ui.content;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.skyline.sms.caster.ui.component.DataTable;

public class ContactsPanel extends JPanel {
	
	private DataTable<Object> table;
	private JButton searchButton;

	public ContactsPanel(DataTable<Object> table) {
		super();
		this.table = table;
		initTable(); //test
	}
	
	private void initTable(){
		List<Object> data = new ArrayList<Object>();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("name");
		columnNames.add("number");
		table = new DataTable<Object>(data, columnNames);
		add(table);
	}
	
	private void initSearchButton(){
		searchButton = new JButton("Search");
	}

}

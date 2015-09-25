package com.skyline.sms.caster.ui.content;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.skyline.sms.caster.ui.component.DataTable;

public class ContactsPanel extends JPanel {
	
	private DataTable<Object> table;
	private JButton searchButton;

	public ContactsPanel() {
		super();
		initTable();
	}
	
	private void initTable(){
		table = new DataTable<Object>();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("name");
		columnNames.add("number");
		table.selectColumns(columnNames);
		add(table);
	}
	
	private void initSearchButton(){
		searchButton = new JButton("Search");
	}

}

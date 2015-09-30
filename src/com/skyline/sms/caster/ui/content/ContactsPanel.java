package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.util.LogUtil;

public class ContactsPanel extends JPanel {
	
	private JScrollPane scrollPane;
	private DataTable<TUser> table;
	private JButton searchButton;
	
	private UserService userService = new UserServiceImpl();

	public ContactsPanel(DataTable<TUser> table) {
		super();
		setLayout(new BorderLayout());
		
		this.table = table;
		initTable(); //test
		scrollPane = new JScrollPane(this.table);
		scrollPane.setSize(600, 400); //test
		add(scrollPane, BorderLayout.CENTER);
		initSearchButton();
	}
	
	private void initTable(){
		//List<Object> data = new ArrayList<Object>();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("userName");
		columnNames.add("number");
		table = new DataTable<TUser>(columnNames);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		//scrollPane.add(table);
		
	}
	
	private void initSearchButton(){
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					table.setData(userService.findUser(null));
					repaint();
				} catch (Exception e1) {
					LogUtil.error(e1);
				}
				
			}
		});
		
		add(searchButton, BorderLayout.NORTH);
	}

}

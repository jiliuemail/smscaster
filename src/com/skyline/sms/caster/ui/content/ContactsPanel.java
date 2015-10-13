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
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.Toast;
import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.LogUtil;

public class ContactsPanel extends ContentPanel {
	
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private DataTable<TUser> table;
	private JButton searchButton;
	private JButton saveButton;
	
	private Toast loadDataToast;
	
	private UserService userService = new UserServiceImpl();

	public ContactsPanel(String title) {
		super(title);
		initTable();
		initTabelPanel();
		initToast();
		initSearchButton();
		initSaveButton();
	}
	
	private void initTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("sms.caster.label.tabel.header.user.name");
		columnNames.add("sms.caster.label.tabel.header.user.number");
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("userName");
		columnFields.add("number");
		
		table = new DataTable<TUser>(columnNames, columnFields);
		table.setCellSelectionEnabled(true);
		//table.setPreferredScrollableViewportSize(new Dimension(200,30));
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		
	}
	
	private void initTabelPanel(){
		scrollPane = new JScrollPane(this.table);
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		setContent(tablePanel);
	}
	
	private void initToast(){
		loadDataToast = new Toast(DialogUtil.getMainFrame(), "sms.caster.message.toast.data.load");
	}
	
	private void initSearchButton(){
		searchButton = new ImageButton("sms.caster.label.button.search");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		
		addToolButton(searchButton);
	}
	
	private void updateTable(){
		try {
			table.setData(userService.findUser(null));
			table.updateUI();
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSearchError(tablePanel);
		}
	}
	
	private void initSaveButton(){
		saveButton = new ImageButton("sms.caster.label.button.save");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getUpdateRecords().size() > 0) {
						userService.saveOrUpdateUsers(table.getUpdateRecords());
						table.clearUpdateRecords();
						DialogUtil.showSaveOK(tablePanel);
					}else{
						DialogUtil.showToast("sms.caster.message.toast.nodata.update");
					}

				} catch (Exception e1) {
					LogUtil.error(e1);
					DialogUtil.showSaveError(tablePanel);
				}
				
			}
		});
		
		addToolButton(saveButton);
	}
	
	@Override
	public void beforeDisplay() {
		loadDataToast.setVisible(true);
	}

	@Override
	public void afterDisplay() {
		updateTable();
		loadDataToast.setVisible(false);
	}
	

	
}

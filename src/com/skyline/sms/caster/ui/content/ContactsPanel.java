package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.table.TableCellEditor;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.ImagePanel;
import com.skyline.sms.caster.ui.component.InputComboBox;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.ui.component.Toast;
import com.skyline.sms.caster.ui.data.UIDataStorer;
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.FormatUtil;
import com.skyline.sms.caster.util.LogUtil;

public class ContactsPanel extends ContentPanel {
	
	private static final String TABLE_HEADER_USER_NUMBER = "sms.caster.label.table.header.user.number";
	private static final String TABLE_HEADER_USER_NAME = "sms.caster.label.table.header.user.name";
	private static final String TABLE_HEADER_USER_CREATE_DATE = "sms.caster.label.table.header.user.createDate";
	private static final String TABLE_HEADER_USER_GROUP = "sms.caster.label.table.header.user.group";
	private JPanel outerPanel;
	private JSplitPane layoutPanel;
	
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private DataTable<TUser> usersTable;
	
	private JPanel insertPanel;
	private ImagePanel personPanel;
	private InputPanel insertInputPanel;
	
	private JPanel submitPanel;
	private ImageButton submitButton;
	private ImageButton cancelButton;
	
	private InputTextField nameInput;
	private InputTextField numberInput;
	private InputTextField createDateInput;
	private InputComboBox<TGroup> groupList;
	
	private ImageButton searchButton;
	private ImageButton addButton;
	private ImageButton saveButton;
	
	private Toast loadDataToast;
	
	private UserService userService = new UserServiceImpl();
	
	private TUser editUser;

	public ContactsPanel(String title) {
		super(title);
		initTable();
		initTabelPanel();
		initInsertPanel();
		initOuterPanel();
		initToast();
		initSearchButton();
		initAddButton();
		initSaveButton();
	}
	
	private void initTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(TABLE_HEADER_USER_NAME);
		columnNames.add(TABLE_HEADER_USER_NUMBER);
		columnNames.add(TABLE_HEADER_USER_CREATE_DATE);
		columnNames.add(TABLE_HEADER_USER_GROUP);
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("userName");
		columnFields.add("number");
		columnFields.add("createDate");
		columnFields.add("TGroups");
		
		usersTable = new DataTable<TUser>(columnNames, columnFields);
		usersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					syncSelectedRowData(usersTable.getSelectedRow());
				}
			}
			
		 });
		
		usersTable.addDataStorer(new UserDataStorer());
		
		
		
	}
	
	private void syncSelectedRowData(int row){
		if (row < 0) {
			editUser = null;
		}else{
			editUser = usersTable.getRowData(row);
		}
		fireInsertInput();
	}
	
	private void initTabelPanel(){
		scrollPane = new JScrollPane(this.usersTable);
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = usersTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
							syncSelectedRowData(usersTable.getLastEditRowIndex());
						}
						usersTable.clearSelection();
				}
			}
		});
	}
	
	
	private void initInsertPanel(){
		insertPanel = new JPanel();
		insertPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 100));
		
		personPanel = new ImagePanel("resource/image/contacts_big.png");
		personPanel.setPreferredSize(new Dimension(100, 100));
		leftPanel.add(personPanel);
		
		insertPanel.add(leftPanel, BorderLayout.WEST);
		initInsertInputPanel();
		insertPanel.add(insertInputPanel, BorderLayout.CENTER);
		initSubmitPanel();
		insertPanel.add(submitPanel, BorderLayout.SOUTH);
	}
	
	private void initInsertInputPanel(){
		insertInputPanel = new InputPanel();
		nameInput = new InputTextField(TABLE_HEADER_USER_NAME, 30);
		numberInput = new InputTextField(TABLE_HEADER_USER_NUMBER, 30);
		createDateInput = new InputTextField(TABLE_HEADER_USER_CREATE_DATE, 30);
		createDateInput.setEnabled(false);
		groupList = new InputComboBox<TGroup>(TABLE_HEADER_USER_GROUP);
		groupList.setFieldWidth(UIConstants.COMPONENT_WIDTH_UNIT * 50);
		insertInputPanel.addInputField(nameInput);
		insertInputPanel.addInputField(numberInput);
		insertInputPanel.addInputField(createDateInput);
		insertInputPanel.addInputField(groupList);
		disEnableInsertInput();
	}
	
	private void fireInsertInput(){
		if (editUser == null) {
			disEnableInsertInput();
		}else{
			enableInsertInput();
			nameInput.getInputField().setText(editUser.getUserName());
			numberInput.getInputField().setText(editUser.getNumber());
			createDateInput.getInputField().setText(FormatUtil.formatToString(editUser.getCreateDate()));
			Set<TGroup> groups = editUser.getTGroups();
			if (CollectionUtil.hasElements(groups)) {
				for (TGroup group : groups) {
					groupList.getInputField().addItem(group);
				}
			}
		}
	}
	
	private void disEnableInsertInput(){
		nameInput.setEnabled(false);
		numberInput.setEnabled(false);
		groupList.setEnabled(false);
	}
	
	private void enableInsertInput(){
		nameInput.setEnabled(true);
		numberInput.setEnabled(true);
		groupList.setEnabled(true);
	}
	
	private void initSubmitPanel(){
			initSubmitButton();
			initCancelButton();
			submitPanel = new JPanel();
			submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, UIConstants.COMPONENT_WIDTH_UNIT, UIConstants.COMPONENT_HEIGHT_UNIT/2));
			submitPanel.setPreferredSize(new Dimension(UIConstants.COMPONENT_WIDTH_UNIT * 12, UIConstants.COMPONENT_HEIGHT_UNIT*4));
			submitPanel.add(submitButton);
			submitPanel.add(cancelButton);
	}
	
	private void initSubmitButton(){
		submitButton = new ImageButton("sms.caster.label.button.submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (editUser != null) {
					editUser.setUserName(nameInput.getInputField().getText());
					editUser.setNumber(numberInput.getInputField().getText());
					usersTable.notifyUpdateRecords();
				}
			}
		});
	}
	
	private void initCancelButton(){
		cancelButton = new ImageButton("sms.caster.label.button.cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (editUser != null && editUser.getId() == null) {
					usersTable.cancelNewRecord(editUser);
					editUser = null;
					disEnableInsertInput();
				}else {
					fireInsertInput();
				}
			}
		});
	}
	
	private void initOuterPanel(){
		layoutPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		layoutPanel.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				layoutPanel.setDividerLocation(0.6);  
			}
		});
		layoutPanel.add(tablePanel, JSplitPane.TOP);
		layoutPanel.add(insertPanel, JSplitPane.BOTTOM);
		layoutPanel.setEnabled(false);
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		outerPanel.add(layoutPanel);
		setContent(outerPanel);
		
	}
	
	private void initToast(){
		loadDataToast = new Toast(DialogUtil.getMainFrame(), "sms.caster.message.toast.data.load");
	}
	
	private void initSearchButton(){
		searchButton = new ImageButton("sms.caster.label.button.search");
		searchButton.setImagePath("resource/image/search.png");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		
		addToolButton(searchButton);
	}
	
	private void initAddButton(){
		addButton = new ImageButton("sms.caster.label.button.add");
		addButton.setImagePath("resource/image/addContacts.png");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (editUser != null && editUser.getId() == null) {
					return;
				}
				editUser = new TUser();
				editUser.setCreateDate(new Date());
				editUser.setReceive(0);
				usersTable.addNewRecord(editUser);
				fireInsertInput();
			}
		});
		
		addToolButton(addButton);
	}
	
	private void updateTable(){
		try {
			usersTable.setData(userService.findUsers(new TUser(), new Page()));
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSearchError(tablePanel);
		}
	}
	
	private void initSaveButton(){
		saveButton = new ImageButton("sms.caster.label.button.save");
		saveButton.setImagePath("resource/image/save.png");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				usersTable.notifyUpdateRecords();
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
	
	
	class UserDataStorer extends UIDataStorer<TUser>{
		
		public UserDataStorer() {
			super(DialogUtil.getMainFrame());
		}
		
		@Override
		protected void submitUpdatedData(List<TUser> updatedData) throws Exception {
			userService.saveOrUpdateUsers(updatedData);
		}
	}
	
}

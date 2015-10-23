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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.CheckBoxsPanel;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.ImagePanel;
import com.skyline.sms.caster.ui.component.InputCheckBoxs;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.ui.data.storer.UserDataStorer;
import com.skyline.sms.caster.util.BeanUtil;
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.FormatUtil;
import com.skyline.sms.caster.util.LogUtil;
import com.skyline.sms.caster.util.StringUtil;

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
	
	private JPanel searchPanel;
	private InputTextField userNameInput;
	private InputTextField userNumberInput;
	
	private JPanel insertPanel;
	private ImagePanel personPanel;
	private InputPanel insertInputPanel;
	
	private JPanel submitPanel;
	private ImageButton submitButton;
	private ImageButton cancelButton;
	
	private InputTextField nameInput;
	private InputTextField numberInput;
	private InputTextField createDateInput;
	private InputCheckBoxs<TGroup> groupList;
	
	private ImageButton searchButton;
	private ImageButton addButton;
	private ImageButton saveButton;
	private ImageButton delButton;
	
	private UserService userService = new UserServiceImpl();
	
	private TUser editUser;
	private boolean modified = false;
	private CheckBoxsPanel<TGroup> checkBoxs;

	public ContactsPanel(String title) {
		super(title);
		initTable();
		initSearchPanel();
		initTabelPanel();
		initInsertPanel();
		initOuterPanel();
		initSearchButton();
		initAddButton();
		initSaveButton();
		initDelButton();
	}
	
	private void initTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(TABLE_HEADER_USER_NAME);
		columnNames.add(TABLE_HEADER_USER_NUMBER);
		columnNames.add(TABLE_HEADER_USER_CREATE_DATE);
		//columnNames.add(TABLE_HEADER_USER_GROUP);
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("userName");
		columnFields.add("number");
		columnFields.add("createDate");
		//columnFields.add("TGroups");
		
		usersTable = new DataTable<TUser>(columnNames, columnFields);
		usersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					syncSelectedRowData(usersTable.getSelectedRowData());
				}
			}
		 });
		
		usersTable.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (TableModelEvent.UPDATE == e.getType()) {
					syncSelectedRowData(usersTable.getSelectedRowData());
				}
				
			}
		});
		
		usersTable.setDataStorer(new UserDataStorer());
	}
	
	private void syncSelectedRowData(TUser user){
		try {
			editUser = userService.findUsersById(user);
		} catch (Exception e) {
			DialogUtil.showSearchError(DialogUtil.getMainFrame());
			editUser = user;
		}
		fireInsertInput();
	}
	
	private void initSearchPanel(){
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		userNameInput = new InputTextField(TABLE_HEADER_USER_NAME, 50);
		userNumberInput = new InputTextField(TABLE_HEADER_USER_NUMBER, 50);
		searchPanel.add(userNameInput);
		searchPanel.add(userNumberInput);
	}
	
	private void initTabelPanel(){
		scrollPane = new JScrollPane(this.usersTable);
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(searchPanel, BorderLayout.NORTH);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = usersTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
							syncSelectedRowData(usersTable.getSelectedRowData());
						}
						usersTable.clearSelection();
						disEnableInsertInput();
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
		disEnableInsertInput();
	}
	
	private void initInsertInputPanel(){
		insertInputPanel = new InputPanel();
		nameInput = new InputTextField(TABLE_HEADER_USER_NAME, 30);
		numberInput = new InputTextField(TABLE_HEADER_USER_NUMBER, 30);
		createDateInput = new InputTextField(TABLE_HEADER_USER_CREATE_DATE, 30);
		createDateInput.setEnabled(false);
		checkBoxs = new CheckBoxsPanel<TGroup>();
		checkBoxs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modified = true;
			}
		});
		groupList = new InputCheckBoxs<TGroup>(TABLE_HEADER_USER_GROUP, checkBoxs);
		groupList.setFieldWidth(UIConstants.COMPONENT_WIDTH_UNIT * 50);
		insertInputPanel.addInputField(nameInput);
		insertInputPanel.addInputField(numberInput);
		insertInputPanel.addInputField(createDateInput);
		insertInputPanel.addInputField(groupList);
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
				checkBoxs.removeAllItem();
				checkBoxs.addCheckItems(groups);
			}
		}
	}
	
	private void disEnableInsertInput(){
		nameInput.setEnabled(false);
		numberInput.setEnabled(false);
		groupList.setEnabled(false);
		checkBoxs.setEnabled(false);
		submitButton.setEnabled(false);
		cancelButton.setEnabled(false);
	}
	
	private void enableInsertInput(){
		nameInput.setEnabled(true);
		numberInput.setEnabled(true);
		groupList.setEnabled(true);
		checkBoxs.setEnabled(true);
		submitButton.setEnabled(true);
		cancelButton.setEnabled(true);
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
					String oldUserName = editUser.getUserName();
					String newUserName = nameInput.getInputField().getText();
					if (!BeanUtil.equals(oldUserName, newUserName)) {
						modified = true;
						editUser.setUserName(newUserName);
					}
					String oldNumber = editUser.getNumber();
					String newNumber = numberInput.getInputField().getText();
					if (!BeanUtil.equals(oldNumber, newNumber)) {
						modified = true;
						editUser.setNumber(newNumber);
					}
					editUser.setTGroups(checkBoxs.getSelectedItems());
					if (modified) {
						usersTable.notifyUpdateRowData(editUser);
						modified = false;
					}else {
						DialogUtil.showToast("sms.caster.message.toast.nodata.update");
					}
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
					usersTable.notifyCancelRowData(editUser);
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
				usersTable.notifyAddRowData(editUser);
				fireInsertInput();
			}
		});
		
		addToolButton(addButton);
	}
	
	private void updateTable(){
		try {
			TUser user = new TUser();
			String userName = userNameInput.getInputField().getText();
			if (StringUtil.hasText(userName)) {
				user.setUserName(userName);
			}
			String userNumber = userNumberInput.getInputField().getText();
			if (StringUtil.hasText(userNumber)) {
				user.setNumber(userNumber);
			}
			usersTable.setData(userService.findUsers(user, new Page()));
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
				usersTable.notifyUpdateRowDatas();
			}
		});
		
		addToolButton(saveButton);
	}
	
	private void initDelButton(){
		delButton = new ImageButton("sms.caster.label.button.delete");
		delButton.setImagePath("resource/image/delContacts.png");
		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (usersTable.getSelectedRowData() == null) {
					DialogUtil.showToast("sms.caster.message.toast.nodata.selected");
				}else{
					if (DialogUtil.showConfirmDialog("sms.caster.message.dialog.constacts.delete.confirm")) {
						usersTable.notifyDeleteRowData(editUser);
					}
				}
			}
		});
		
		addToolButton(delButton);
	}
	
	@Override
	public void beforeDisplay() {
		DialogUtil.showLoadDataToast();
	}

	@Override
	public void afterDisplay() {
		updateTable();
		DialogUtil.hiddenLoadDataToast();
	}
	
}

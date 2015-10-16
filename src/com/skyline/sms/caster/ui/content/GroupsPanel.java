package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.table.TableCellEditor;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.GroupService;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.GroupServiceImpl;
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
import com.skyline.sms.caster.util.LogUtil;

public class GroupsPanel extends ContentPanel {
	
	private static final String TABLE_HEADER_GROUP_NAME = "sms.caster.label.table.header.group.name";

	private static final String TABLE_HEADER_USER_NUMBER = "sms.caster.label.table.header.user.number";
	private static final String TABLE_HEADER_USER_NAME = "sms.caster.label.table.header.user.name";
	private static final String TABLE_HEADER_USER_CREATE_DATE = "sms.caster.label.table.header.user.createDate";
	private static final String TABLE_HEADER_USER_GROUP = "sms.caster.label.table.header.user.group";
	private JPanel outerPanel;
	private JSplitPane layoutPanel;
	
	private JPanel groupsTablePanel;
	private JScrollPane groupsTableScrollPane;
	private DataTable<TGroup> groupsTable;
	
	private JPanel userTablePanel;
	private JScrollPane userTableScrollPane;
	private DataTable<TUser> userTable;
	
	private JPanel insertPanel;
	private ImagePanel personPanel;
	private InputPanel insertInputPanel;
	private JPanel submitPanel;
	
	private InputTextField nameInput;
	private InputTextField numberInput;
	private InputComboBox<TGroup> groupList;
	
	private ImageButton searchButton;
	private ImageButton saveButton;
	private ImageButton addButton;
	private ImageButton editButton;
	
	private Toast loadDataToast;
	private AddGroupDialog addGroupPanel;
	
	private UserService userService = new UserServiceImpl();
	private GroupService groupService = new GroupServiceImpl();
	
	private TGroup editGroup;
	private TUser editUser;

	public GroupsPanel(String title) {
		super(title);
		initGroupsTable();
		initGroupsTabelPanel();
		initUserTable();
		initUserTabelPanel();
		initInsertPanel();
		initOuterPanel();
		initToast();
		initAddGroupPanel();
		initSearchButton();
		initAddButton();
		initEditButton();
		initSaveButton();
	}
	
	private void initGroupsTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(TABLE_HEADER_GROUP_NAME);
//		columnNames.add(TABLE_HEADER_USER_NUMBER);
//		columnNames.add(TABLE_HEADER_USER_CREATE_DATE);
//		columnNames.add(TABLE_HEADER_USER_GROUP);
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("groupName");
//		columnFields.add("number");
//		columnFields.add("createDate");
//		columnFields.add("TGroups");
		
		groupsTable = new DataTable<TGroup>(columnNames, columnFields);
		groupsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					syncSelectedRowData(groupsTable.getSelectedRow());
				}
			}
		});
		
		groupsTable.addDataStorer(new GroupDataStorer());
	}
	
	
	private void initUserTable(){
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
		
		userTable = new DataTable<TUser>(columnNames, columnFields);
	}
	
	private void syncSelectedRowData(int row){
		if (row < 0) {
			return;
		}
		editGroup = groupsTable.getRowData(row);
		List<TUser> users = CollectionUtil.setToList(editGroup.getTUsers());
//		if (CollectionUtil.hasElements(users)) {
			userTable.setData(users);
			userTable.updateUI();
//		}
	}
	
	
	private void initGroupsTabelPanel(){
		groupsTableScrollPane = new JScrollPane(this.groupsTable);
		groupsTablePanel = new JPanel();
		groupsTablePanel.setLayout(new BorderLayout());
		groupsTablePanel.add(groupsTableScrollPane, BorderLayout.CENTER);
		
		groupsTableScrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = groupsTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
							syncSelectedRowData(groupsTable.getLastEditRowIndex());
						}
						groupsTable.clearSelection();
				}
			}
		});
	}
	
	private void initUserTabelPanel(){
		userTableScrollPane = new JScrollPane(this.userTable);
		userTablePanel = new JPanel();
		userTablePanel.setLayout(new BorderLayout());
		userTablePanel.add(userTableScrollPane, BorderLayout.CENTER);
		
		userTableScrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = userTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
							syncSelectedRowData(userTable.getLastEditRowIndex());
						}
						userTable.clearSelection();
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
		submitPanel = new JPanel();
		insertPanel.add(leftPanel, BorderLayout.WEST);
		initInsertInputPanel();
		insertPanel.add(insertInputPanel, BorderLayout.CENTER);
		insertPanel.add(submitPanel, BorderLayout.EAST);
	}
	
	private void initInsertInputPanel(){
		insertInputPanel = new InputPanel();
		nameInput = new InputTextField(TABLE_HEADER_USER_NAME, 30);
		numberInput = new InputTextField(TABLE_HEADER_USER_NUMBER, 30);
		groupList = new InputComboBox<TGroup>(TABLE_HEADER_USER_GROUP);
		groupList.setFieldWidth(UIConstants.COMPONENT_WIDTH_UNIT * 50);
		insertInputPanel.addInputField(nameInput);
		insertInputPanel.addInputField(numberInput);
		insertInputPanel.addInputField(groupList);
	}	
	
	private void initOuterPanel(){
		layoutPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		layoutPanel.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				layoutPanel.setDividerLocation(0.6);  
			}
		});
		layoutPanel.add(groupsTablePanel, JSplitPane.TOP);
		layoutPanel.add(userTablePanel, JSplitPane.BOTTOM);
		layoutPanel.setEnabled(false);
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		outerPanel.add(layoutPanel);
		setContent(outerPanel);
		
	}
	
	private void initToast(){
		loadDataToast = new Toast(DialogUtil.getMainFrame(), "sms.caster.message.toast.data.load");
	}
	
	private void initAddGroupPanel(){
		addGroupPanel = new AddGroupDialog(groupsTable);
	}
	
	private void initSearchButton(){
		searchButton = new ImageButton("sms.caster.label.button.search");
		searchButton.setImagePath("resource/image/search.png");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUserTable();
			}
		});
		
		addToolButton(searchButton);
	}
	
	private void updateGroupsTable(){
		try {
			groupsTable.setData(groupService.findGroups(new TGroup(), new Page()));
			groupsTable.updateUI();
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSearchError(userTablePanel);
		}
	}
	
	private void updateUserTable(){
		try {
			userTable.setData(userService.findUsers(new TUser(), new Page()));
			userTable.updateUI();
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSearchError(userTablePanel);
		}
	}
	
	private void initSaveButton(){
		saveButton = new ImageButton("sms.caster.label.button.save");
		saveButton.setImagePath("resource/image/save.png");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//userTable.notifyUpdateRecords();
			}
		});
		
		addToolButton(saveButton);
	}
	
	
	private void initAddButton(){
		addButton = new ImageButton("sms.caster.label.button.add");
		addButton.setImagePath("resource/image/addGroup.png");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TableCellEditor cellEitor = groupsTable.getCellEditor();
				if (cellEitor != null) {
					cellEitor.stopCellEditing();
				}
				groupsTable.clearSelection();
				editGroup = new TGroup();
				editGroup.setReceive(0);
				groupsTable.addNewRecord(editGroup);
				addGroupPanel.setGroup(editGroup);
				addGroupPanel.setVisible(true);
			}
		});
		
		addToolButton(addButton);
	}
	
	private void initEditButton(){
		editButton = new ImageButton("sms.caster.label.button.edit");
		editButton.setImagePath("resource/image/editGroup.png");
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TableCellEditor cellEitor = groupsTable.getCellEditor();
				if (cellEitor != null) {
					cellEitor.stopCellEditing();
				}
				int rowIndex = groupsTable.getSelectedRow();
				if (rowIndex > -1) {
					editGroup = groupsTable.getRowData(rowIndex);
					addGroupPanel.setGroup(editGroup);
					addGroupPanel.setVisible(true);
				}
				groupsTable.clearSelection();
			}
		});
		addToolButton(editButton);
	}
	
	@Override
	public void beforeDisplay() {
		loadDataToast.setVisible(true);
	}

	@Override
	public void afterDisplay() {
		updateGroupsTable();
		loadDataToast.setVisible(false);
	}
	

	class GroupDataStorer extends UIDataStorer<TGroup>{
		
		public GroupDataStorer() {
			super(DialogUtil.getMainFrame());
		}
		
		@Override
		protected void submitUpdatedData(List<TGroup> updatedData) throws Exception {
			groupService.saveOrUpdateGroups(updatedData);
		}
	}
}

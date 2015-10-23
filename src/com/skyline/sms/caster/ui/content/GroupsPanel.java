package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.GroupService;
import com.skyline.sms.caster.service.impl.GroupServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.ui.component.MessageLabel;
import com.skyline.sms.caster.ui.data.storer.GroupDataStorer;
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.LogUtil;
import com.skyline.sms.caster.util.StringUtil;

public class GroupsPanel extends ContentPanel {
	
	private static final String TABLE_HEADER_GROUP_NAME = "sms.caster.label.table.header.group.name";
	private static final String TABLE_HEADER_GROUP_CREATE_DATE = "sms.caster.label.table.header.group.createDate";

	private static final String TABLE_HEADER_USER_NUMBER = "sms.caster.label.table.header.user.number";
	private static final String TABLE_HEADER_USER_NAME = "sms.caster.label.table.header.user.name";
	private static final String TABLE_HEADER_USER_CREATE_DATE = "sms.caster.label.table.header.user.createDate";
	
	private JPanel outerPanel;
	private JSplitPane layoutPanel;
	
	private JPanel groupsTablePanel;
	private JScrollPane groupsTableScrollPane;
	private DataTable<TGroup> groupsTable;
	
	private JPanel searchPanel;
	private InputTextField groupNameInput;
	
	private JPanel userTablePanel;
	private JLabel groupMemberLabel;
	private JScrollPane userTableScrollPane;
	private DataTable<TUser> userTable;
	
	private ImageButton searchButton;
	private ImageButton saveButton;
	private ImageButton addButton;
	private ImageButton editButton;
	
	
	private AddGroupDialog addGroupPanel;
	
	private GroupService groupService = new GroupServiceImpl();
	
	private TGroup editGroup;

	public GroupsPanel(String title) {
		super(title);
		initGroupsTable();
		initSearchPanel();
		initGroupsTabelPanel();
		initUserTable();
		initUserTabelPanel();
		initOuterPanel();
		initAddGroupDialog();
		initSearchButton();
		initAddButton();
		initEditButton();
		initSaveButton();
	}
	
	private void initGroupsTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(TABLE_HEADER_GROUP_NAME);
		columnNames.add(TABLE_HEADER_GROUP_CREATE_DATE);
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("groupName");
		columnFields.add("createDate");
		
		groupsTable = new DataTable<TGroup>(columnNames, columnFields);
		
		groupsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					syncSelectedRowData(groupsTable.getSelectedRowData());
				}
			}
		});
		
		groupsTable.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (TableModelEvent.UPDATE == e.getType()) {
					TGroup updatedRowData = groupsTable.getSelectedRowData();
					if (updatedRowData == null) {
						updatedRowData = groupsTable.getRowData(e.getFirstRow());
					}
					syncSelectedRowData(updatedRowData);
				}
			}
		});
		
		groupsTable.setDataStorer(new GroupDataStorer());
	}
	
	private void initSearchPanel(){
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		groupNameInput = new InputTextField(TABLE_HEADER_GROUP_NAME, 50);
		searchPanel.add(groupNameInput);
	}
	
	private void initUserTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(TABLE_HEADER_USER_NAME);
		columnNames.add(TABLE_HEADER_USER_NUMBER);
		columnNames.add(TABLE_HEADER_USER_CREATE_DATE);
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("userName");
		columnFields.add("number");
		columnFields.add("createDate");
		
		userTable = new DataTable<TUser>(columnNames, columnFields);
		userTable.setEditable(false);
	}
	
	private void syncSelectedRowData(TGroup group){
		if (group == null) {return ;}
		editGroup = group;
		userTable.setData(CollectionUtil.setToList(groupService.findGroupById(editGroup).getTUsers()));
	}
	
	
	private void initGroupsTabelPanel(){
		groupsTableScrollPane = new JScrollPane(this.groupsTable);
		groupsTablePanel = new JPanel();
		groupsTablePanel.setLayout(new BorderLayout());
		groupsTablePanel.add(searchPanel, BorderLayout.NORTH);
		groupsTablePanel.add(groupsTableScrollPane, BorderLayout.CENTER);
		
		groupsTableScrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = groupsTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
							syncSelectedRowData(groupsTable.getSelectedRowData());
						}
						groupsTable.clearSelection();
				}
			}
		});
	}
	
	private void initUserTabelPanel(){
		groupMemberLabel = new MessageLabel("sms.caster.label.panel.groups.member");
		groupMemberLabel.setBackground(Color.GRAY);
		groupMemberLabel.setPreferredSize(new Dimension(getWidth(), UIConstants.HEIGHT_UNIT));
		userTableScrollPane = new JScrollPane(this.userTable);
		userTablePanel = new JPanel();
		userTablePanel.setLayout(new BorderLayout());
		userTablePanel.add(groupMemberLabel, BorderLayout.NORTH);
		userTablePanel.add(userTableScrollPane, BorderLayout.CENTER);
		
		userTableScrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = userTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
						}
						userTable.clearSelection();
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
		layoutPanel.add(groupsTablePanel, JSplitPane.TOP);
		layoutPanel.add(userTablePanel, JSplitPane.BOTTOM);
		layoutPanel.setEnabled(false);
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		outerPanel.add(layoutPanel);
		setContent(outerPanel);
	}
	
	private void initAddGroupDialog(){
		addGroupPanel = new AddGroupDialog(groupsTable);
	}
	
	private void initSearchButton(){
		searchButton = new ImageButton("sms.caster.label.button.search");
		searchButton.setImagePath("resource/image/search.png");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGroupsTable();
			}
		});
		
		addToolButton(searchButton);
	}
	
	private void updateGroupsTable(){
		groupsTable.clearSelection();
		try {
			TGroup group = new TGroup();
			String groupName = groupNameInput.getInputField().getText();
			if (StringUtil.hasText(groupName)) {
				group.setGroupName(groupName);
			}
			groupsTable.setData(groupService.findGroups(group, new Page()));
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSearchError(userTablePanel);
		}
	}
	
	
	private void initSaveButton(){
		saveButton = new ImageButton("sms.caster.label.button.save");
		saveButton.setImagePath("resource/image/save.png");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				groupsTable.notifyUpdateRowDatas();
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
				editGroup.setCreateDate(new Date());
				groupsTable.notifyAddRowData(editGroup);
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
					addGroupPanel.setGroup(groupService.findGroupById(editGroup));
					addGroupPanel.setVisible(true);
				}else {
					DialogUtil.showToast("sms.caster.message.toast.nodata.selected");
				}
			}
		});
		addToolButton(editButton);
	}
	
	@Override
	public void beforeDisplay() {
		DialogUtil.showLoadDataToast();
	}

	@Override
	public void afterDisplay() {
		updateGroupsTable();
		DialogUtil.hiddenLoadDataToast();
	}
	
}

package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.DataList;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.ui.component.MessageButton;
import com.skyline.sms.caster.ui.component.MessageLabel;
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.LogUtil;

public class AddGroupDialog extends JDialog {
	
	private static final int DIALOG_WIDTH = UIConstants.COMPONENT_WIDTH_UNIT * 62;

	private JPanel outPanel;
	
	private JPanel topPanel;
	private InputPanel groupInputPanel;
	private InputTextField groupNameInput;
	
	private JPanel switchPanel;
	
	private JPanel userNamePanel;
	private JTextField userNameField;
	
	private MessageLabel groupMemberLabel;
	
	private JPanel usersPanel;
	private JPanel groupsPanel;
	private DataList<TUser> usersList;
	private DataList<TUser> groupMembersList;
	
	private JPanel switchButtonPanel;
	private MessageButton toLeftButton;
	private MessageButton toRightButton;
	private MessageButton toLeftAllButton;
	private MessageButton toRightAllButton;
	
	private JPanel buttonPanel;
	private MessageLabel tipLabel;
	private ImageButton submitButton;
	private ImageButton cancelButton;
	
	private UserService userService = new UserServiceImpl();
	private TGroup editGroup;
	private DataTable<TGroup> groupTable;

	private Dimension dialogSize = new Dimension(DIALOG_WIDTH, UIConstants.COMPONENT_HEIGHT_UNIT * 25);

	
	public AddGroupDialog(DataTable<TGroup> groupTable){
		super(DialogUtil.getMainFrame(), true);
		setDataTable(groupTable);
		setVisible(false);
		setAlwaysOnTop(false);
		initTitle();
		initGroup();
		initInputPanel();
		initTopPanel();
		initUserNamePanel();
		initGroupMemberLabel();
		initUsersPanel();
		initSwitchButtonPanel();
		initGroupsPanel();
		initSwitchPanel();
		initButtonPanel();
		initOutPanel();
		initThis();
	}
	
	private void initTitle(){
		setTitle(MessageBundle.getMessage("sms.caster.label.dialog.title.addGroup"));
	}
	
	private void setDataTable(DataTable<TGroup> groupTable) {
		this.groupTable = groupTable;
	}

	private void initThis(){
		setLayout(new BorderLayout());
		setSize(dialogSize);
		add(outPanel, BorderLayout.CENTER);
		setLocationRelativeTo(DialogUtil.getMainFrame());
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				DialogUtil.getMainFrame().setVisible(true);
			}
			
			public void windowClosing(WindowEvent e) {
				cancelAddGroup();
			}
		});
	}
	
	private void initGroup(){
		editGroup = new TGroup();
	}

	private void initInputPanel() {
		groupInputPanel = new InputPanel();
		groupInputPanel.setBackground(Color.BLUE);
		groupNameInput = new InputTextField("sms.caster.label.table.header.group.name", 50);
		groupInputPanel.addInputField(groupNameInput);
		groupInputPanel.setLocation(UIConstants.COMPONENT_WIDTH_UNIT, UIConstants.COMPONENT_HEIGHT_UNIT/2);
	}

	private void initTopPanel(){
		topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, UIConstants.COMPONENT_HEIGHT_UNIT*2));
		topPanel.add(groupInputPanel);
	}
	
	private void initUserNamePanel(){
		userNamePanel = new JPanel();
		userNameField = new JTextField(34);
		userNamePanel.add(userNameField);
	}
	
	private void initGroupMemberLabel(){
		groupMemberLabel = new MessageLabel("sms.caster.label.message.addgroup.groupMembers");
	}
	
	private void initUsersPanel(){
		usersPanel = new JPanel();
		usersPanel.setLayout(new BorderLayout());
		usersList = new DataList<TUser>();
		usersList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		
		usersPanel.add(usersList);
	}
	
	private void fireUsersListData(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					usersList.setData(userService.findUsers(new TUser(), new Page()));
					fireToRightButton();
				} catch (Exception e) {
					LogUtil.error(e);
					DialogUtil.showSearchError(AddGroupDialog.this);
				}
			}
		});
	}
	
	private void fireGroupMembersListData(){
		final Set<TUser> users = editGroup.getTUsers();
		if (CollectionUtil.hasElements(users)) {
			groupMembersList.setData(CollectionUtil.setToList(users));
		}
		
	}
	
	private void initSwitchButtonPanel(){
		switchButtonPanel = new JPanel();
		switchButtonPanel.setLayout(new GridLayout(4, 1, 10, UIConstants.COMPONENT_HEIGHT_UNIT));
		toLeftButton = new MessageButton("sms.caster.label.button.toLeft");
		toLeftButton.marginHorizontal(1);
		toRightButton = new MessageButton("sms.caster.label.button.toRight");
		toRightButton.marginHorizontal(1);
		toLeftAllButton = new MessageButton("sms.caster.label.button.toLeftAll");
		toLeftAllButton.marginHorizontal(1);
		toRightAllButton = new MessageButton("sms.caster.label.button.toRightAll");
		toRightAllButton.marginHorizontal(1);
		initSwitchButtonListeners();
		switchButtonPanel.add(toRightButton);
		switchButtonPanel.add(toLeftButton);
		switchButtonPanel.add(toRightAllButton);
		switchButtonPanel.add(toLeftAllButton);
	}
	
	private void initSwitchButtonListeners(){
		toRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TUser user = usersList.getSelectedValue();
				if (user != null) {
					groupMembersList.addItem(user);
					usersList.removeItem(user);
				}else{
					DialogUtil.showToast(AddGroupDialog.this,"sms.caster.message.toast.nodata.selected");
				}
				fireToLeftButton();
				fireToRightButton();
			}
		});
		
		toLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TUser user = groupMembersList.getSelectedValue();
				if (user != null) {
					usersList.addItem(user);
					groupMembersList.removeItem(user);
				}else{
					DialogUtil.showToast(AddGroupDialog.this, "sms.caster.message.toast.nodata.selected");
				}
				fireToLeftButton();
				fireToRightButton();
			}
		});
		
		toRightAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					groupMembersList.addItems(usersList.getData());
					usersList.removeAllItem();
					fireToLeftButton();
					fireToRightButton();
			}
		});
		
		toLeftAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					usersList.addItems(groupMembersList.getData());
					groupMembersList.removeAllItem();
					fireToLeftButton();
					fireToRightButton();
			}
		});
	}
	
	private void fireToRightButton(){
		if (usersList.isEmpty()) {
			toRightButton.setEnabled(false);
			toRightAllButton.setEnabled(false);
		}else{
			toRightButton.setEnabled(true);
			toRightAllButton.setEnabled(true);
		}
	}
	
	private void fireToLeftButton(){
		if (groupMembersList.isEmpty()) {
			toLeftButton.setEnabled(false);
			toLeftAllButton.setEnabled(false);
		}else {
			toLeftButton.setEnabled(true);
			toLeftAllButton.setEnabled(true);
		}
	}
	
	private void initGroupsPanel(){
		groupsPanel = new JPanel();
		groupsPanel.setLayout(new BorderLayout());
		groupMembersList = new DataList<TUser>();
		groupMembersList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		groupsPanel.add(groupMembersList);
	}
	
	private void initSwitchPanel(){
		switchPanel = new JPanel();
		switchPanel.setLayout(null);
		userNamePanel.setBounds(UIConstants.COMPONENT_WIDTH_UNIT*2, UIConstants.COMPONENT_HEIGHT_UNIT
				, UIConstants.COMPONENT_WIDTH_UNIT * 22, UIConstants.COMPONENT_HEIGHT_UNIT*2);
		
		groupMemberLabel.setBounds(UIConstants.COMPONENT_WIDTH_UNIT*36, UIConstants.COMPONENT_HEIGHT_UNIT
				, UIConstants.COMPONENT_WIDTH_UNIT * 22, UIConstants.COMPONENT_HEIGHT_UNIT*2);
		
		usersPanel.setBounds(UIConstants.COMPONENT_WIDTH_UNIT*2
				, UIConstants.COMPONENT_HEIGHT_UNIT * 3
				, UIConstants.COMPONENT_WIDTH_UNIT * 22, UIConstants.COMPONENT_HEIGHT_UNIT * 14);

		switchButtonPanel.setBounds(UIConstants.COMPONENT_WIDTH_UNIT*24
				, UIConstants.COMPONENT_HEIGHT_UNIT * 3
				, UIConstants.COMPONENT_WIDTH_UNIT * 12, UIConstants.COMPONENT_HEIGHT_UNIT * 8);
		
		groupsPanel.setBounds(UIConstants.COMPONENT_WIDTH_UNIT*36
				, UIConstants.COMPONENT_HEIGHT_UNIT * 3
				, UIConstants.COMPONENT_WIDTH_UNIT * 22, UIConstants.COMPONENT_HEIGHT_UNIT * 14);
		
		switchPanel.add(userNamePanel);
		switchPanel.add(groupMemberLabel);
		switchPanel.add(groupsPanel);
		switchPanel.add(switchButtonPanel);
		switchPanel.add(usersPanel);
	}
	
	private void initButtonPanel(){
		tipLabel = new MessageLabel("sms.caster.label.message.addgroup.tip");
		initSubmitButton();
		initCancelButton();
		
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, UIConstants.COMPONENT_HEIGHT_UNIT*3));
		buttonPanel.add(tipLabel);
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
	}
	
	private void initSubmitButton(){
		submitButton = new ImageButton("sms.caster.label.button.submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (editGroup != null) {
					editGroup.setGroupName(groupNameInput.getInputField().getText());
					List<TUser> members = groupMembersList.getData();
					if (CollectionUtil.hasElements(members)) {
						editGroup.getTUsers().addAll(members);
					}
					setVisible(false);
					groupTable.notifyUpdateRecords();
				}
			}
		});
	}
	
	private void initCancelButton(){
		cancelButton = new ImageButton("sms.caster.label.button.cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelAddGroup();
			}
		});
	}
	
	private void cancelAddGroup(){
		groupMembersList.removeAll();
		if (editGroup != null && editGroup.getId() == null) {
			groupTable.cancelNewRecord(editGroup);
		}
		setVisible(false);
	}
	
	private void initOutPanel() {
		outPanel = new JPanel();
		outPanel.setLayout(new BorderLayout());
		
		outPanel.setPreferredSize(dialogSize);
		outPanel.add(topPanel, BorderLayout.NORTH);
		outPanel.add(switchPanel, BorderLayout.CENTER);
		outPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void setGroup(TGroup group){
		editGroup = group;
		if (editGroup == null) {
			editGroup = new TGroup();
		}
		fireGroupNameInput();
		fireGroupMemberInput();
		fireUsersListData();
		fireGroupMembersListData();
		fireToRightButton();
		fireToLeftButton();
	}
	
	private void fireGroupNameInput(){
		groupNameInput.getInputField().setText(editGroup.getGroupName());
	}
	
	private void fireGroupMemberInput(){
		List<TUser> users = CollectionUtil.setToList(editGroup.getTUsers());
		if (CollectionUtil.hasElements(users)) {
			groupMembersList.setData(users);
			groupMembersList.updateUI();
		}else{
			groupMembersList.removeAllItem();
		}
	}
	
}

package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.skyline.sms.caster.pojo.TGroup;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.DataList;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.ui.component.MessageButton;
import com.skyline.sms.caster.ui.component.MessageLabel;
import com.skyline.sms.caster.util.DialogUtil;

public class AddGroupPanel extends JDialog {
	
	private static final int DIALOG_WIDTH = UIConstants.COMPONENT_WIDTH_UNIT * 63;

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
	private DataList<TGroup> groupsList;
	
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
	private JFrame owner;

	private Dimension dialogSize = new Dimension(DIALOG_WIDTH, UIConstants.COMPONENT_HEIGHT_UNIT * 25);
	
	public AddGroupPanel(){
		this(null);
	}
	
	public AddGroupPanel(JFrame owner){
		super(owner, true);
		setOwner(owner);
		setVisible(false);
		setAlwaysOnTop(false);
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
	
	private void setOwner(JFrame owner) {
		this.owner = owner;
	}

	private void initThis(){
		setLayout(new BorderLayout());
		setSize(dialogSize);
		add(outPanel, BorderLayout.CENTER);
		setLocationRelativeTo(DialogUtil.getMainFrame());
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				super.windowActivated(e);
				if (owner == null) {
					owner = DialogUtil.getMainFrame();
				}
				owner.setVisible(true);
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
//		List<TUser> findUsers = null;
//		try {
//			findUsers = userService.findUsers(new TUser(), new Page());
//		} catch (Exception e) {
//			LogUtil.error(e);
//			findUsers = new ArrayList<TUser>();
//		}
		usersList = new DataList<TUser>();
		usersList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		usersPanel.add(usersList);
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
		switchButtonPanel.add(toLeftButton);
		switchButtonPanel.add(toRightButton);
		switchButtonPanel.add(toLeftAllButton);
		switchButtonPanel.add(toRightAllButton);
	}
	
	private void initGroupsPanel(){
		groupsPanel = new JPanel();
		groupsPanel.setLayout(new BorderLayout());
		groupsList = new DataList<TGroup>();
		groupsList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		groupsPanel.add(groupsList);
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
		submitButton = new ImageButton("sms.caster.label.button.submit");
		cancelButton = new ImageButton("sms.caster.label.button.cancel");
		
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, UIConstants.COMPONENT_HEIGHT_UNIT*3));
		buttonPanel.add(tipLabel);
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
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
	}
	
}

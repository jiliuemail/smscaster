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
import com.skyline.sms.caster.util.CollectionUtil;
import com.skyline.sms.caster.util.DialogUtil;
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
	private DataTable<TUser> table;
	
	private JPanel insertPanel;
	private ImagePanel personPanel;
	private InputPanel insertInputPanel;
	private JPanel submitPanel;
	
	private InputTextField nameInput;
	private InputTextField numberInput;
	private InputComboBox<TGroup> groupList;
	
	private ImageButton searchButton;
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
		
		table = new DataTable<TUser>(columnNames, columnFields);
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					int row = table.getSelectedRow();
					if (row >= 0) {
						editUser = table.getRowData(row);
						nameInput.getInputField().setText(editUser.getUserName());
						numberInput.getInputField().setText(editUser.getNumber());
						Set<TGroup> groups = editUser.getTGroups();
						if (CollectionUtil.hasElements(groups)) {
							for (TGroup group : groups) {
								groupList.getInputField().addItem(group);
							}
						}
					}
				}
			}
			
		});
	}
	
	private void initTabelPanel(){
		scrollPane = new JScrollPane(this.table);
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						table.clearSelection();
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
	
	private void updateTable(){
		try {
			table.setData(userService.findUsers(new TUser(), new Page()));
			table.updateUI();
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

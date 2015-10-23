package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;

import com.skyline.sms.caster.dao.Page;
import com.skyline.sms.caster.pojo.TMessageSent;
import com.skyline.sms.caster.service.SentService;
import com.skyline.sms.caster.service.impl.SentServiceImpl;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.LogUtil;
import com.skyline.sms.caster.util.StringUtil;

public class SentPanel extends ContentPanel {
	
	private static final String TABLE_HEADER_MESSAGE_SENT_CONTACT_NAME = "sms.caster.label.table.header.sent.contactName";
	private static final String TABLE_HEADER_MESSAGE_SENT_MESSAGE = "sms.caster.label.table.header.sent.message";
	private static final String TABLE_HEADER_MESSAGE_SENT_PORT_NAME = "sms.caster.label.table.header.sent.portName";
	private static final String TABLE_HEADER_MESSAGE_SENT_CREATE_DATE = "sms.caster.label.table.header.sent.createDate";
	private static final String TABLE_HEADER_MESSAGE_SENT_STATUS = "sms.caster.label.table.header.sent.status";
	private JPanel outerPanel;
	private JSplitPane layoutPanel;
	
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private DataTable<TMessageSent> sentsTable;
	
	private JPanel searchPanel;
	private InputTextField sentNameInput;
	private InputTextField sentMessageInput;
	
	
	private ImageButton searchButton;
	private ImageButton resendButton;
	
	private SentService sentService = new SentServiceImpl();
	
	//private TMessageSent editMessageSent;

	public SentPanel(String title) {
		super(title);
		initTable();
		initSearchPanel();
		initTabelPanel();
		initOuterPanel();
		initSearchButton();
		initResendButton();
	}
	
	private void initTable(){
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(TABLE_HEADER_MESSAGE_SENT_CONTACT_NAME);
		columnNames.add(TABLE_HEADER_MESSAGE_SENT_MESSAGE);
		columnNames.add(TABLE_HEADER_MESSAGE_SENT_PORT_NAME);
		columnNames.add(TABLE_HEADER_MESSAGE_SENT_CREATE_DATE);
		columnNames.add(TABLE_HEADER_MESSAGE_SENT_STATUS);
		
		List<String> columnFields = new ArrayList<String>();
		columnFields.add("contactName");
		columnFields.add("message");
		columnFields.add("portName");
		columnFields.add("createDate");
		columnFields.add("status");
		
		sentsTable = new DataTable<TMessageSent>(columnNames, columnFields);
		sentsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					syncSelectedRowData(sentsTable.getSelectedRowData());
				}
			}
		 });
		
		sentsTable.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (TableModelEvent.UPDATE == e.getType()) {
					syncSelectedRowData(sentsTable.getSelectedRowData());
				}
				
			}
		});
	}
	
	private void syncSelectedRowData(TMessageSent sent){
//		try {
//			editMessageSent = sentService.findMessageSentsById(sent);
//		} catch (Exception e) {
//			DialogUtil.showSearchError(DialogUtil.getMainFrame());
//			editMessageSent = sent;
//		}
	}
	
	private void initSearchPanel(){
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		sentNameInput = new InputTextField(TABLE_HEADER_MESSAGE_SENT_CONTACT_NAME, 50);
		sentMessageInput = new InputTextField(TABLE_HEADER_MESSAGE_SENT_MESSAGE, 50);
		searchPanel.add(sentNameInput);
		searchPanel.add(sentMessageInput);
	}
	
	
	private void initTabelPanel(){
		scrollPane = new JScrollPane(this.sentsTable);
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(searchPanel, BorderLayout.NORTH);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
						TableCellEditor cellEitor = sentsTable.getCellEditor();
						if (cellEitor != null) {
							cellEitor.stopCellEditing();
							syncSelectedRowData(sentsTable.getSelectedRowData());
						}
						sentsTable.clearSelection();
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
	
	private void initResendButton(){
		resendButton = new ImageButton("sms.caster.label.button.resend");
		addToolButton(resendButton);
	}
	
	
	private void updateTable(){
		try {
			TMessageSent sent = new TMessageSent();
			String contactName = sentNameInput.getInputField().getText();
			if (StringUtil.hasText(contactName)) {
				sent.setContactName(contactName);
			}
			String message = sentMessageInput.getInputField().getText();
			if (StringUtil.hasText(message)) {
				sent.setMessage(message);
			}
			sentsTable.setData(sentService.findMessageSents(sent, new Page()));
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSearchError(tablePanel);
		}
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

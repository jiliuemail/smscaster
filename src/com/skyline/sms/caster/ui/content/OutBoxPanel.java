package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.MessageService;
import com.skyline.sms.caster.service.impl.MessageServiceImpl;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.util.LogUtil;

public class OutBoxPanel extends ContentPanel{
	
	private JButton delSms;
	private JButton updateSms;
	
	private DataTable<TMessage> smsTable;
	private JPanel smsTablePanel;
	
	private MessageService msgService;
	
	private final String TOOLBUTTON_DELSMS="sms.caster.outbox.toolbutton.delsms";
	private final String TOOLBUTTON_UPDATE="sms.caster.outbox.toolbutton.update";
	private final String SMSTABLE_HEAD_SUBJECT="sms.caster.outbox.smstable.head.subject";
	private final String SMSTABLE_HEAD_CONTACTOR_NAME="sms.caster.outbox.smstable.head.contactorName";
	private final String SMSTABLE_HEAD_CONTACTOR_NUMBER="sms.caster.outbox.smstable.head.contactorNumber";
	private final String SMSTABLE_HEAD_MESSAGE="sms.caster.outbox.smstable.head.message";
	
	public OutBoxPanel(String title) {
		super(title);
		msgService=new MessageServiceImpl();
		initToolButton();
		initSmsTable();
//		updateSmsTable();   //是否可以采用懒加载?
	}
		
	public void initToolButton(){
		delSms=new JButton(MessageBundle.getMessage(TOOLBUTTON_DELSMS));
		updateSms=new JButton(MessageBundle.getMessage(TOOLBUTTON_UPDATE));
		addToolButton(delSms);
		addToolButton(updateSms);
	
		delSms.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] columnIds=smsTable.getSelectedRows();
				for(int id:columnIds){
					System.out.println(id);
					try {
						int msgId=(Integer)smsTable.getValueAt(id, 0);
						msgService.delById(msgId);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						LogUtil.error(e1);
						//UI 中提示.....
					}
				}
				updateSmsTable();
				
			}
		});
		
		updateSms.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateSmsTable();
			}
		});
		
		
	}
	
	@Override
	public void afterDisplay() {
		updateSmsTable();
	}
	
	
	public void initSmsTable(){
		
		smsTablePanel=new JPanel();
		smsTablePanel.setLayout(new BorderLayout());
		
		List<String> columnNames=new ArrayList<String>();
		columnNames.add("ID"); //隐藏的一列
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_CONTACTOR_NAME));
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_CONTACTOR_NUMBER));
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_SUBJECT));
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_MESSAGE));
		
		List<String> fields=new ArrayList<String>();	
		fields.add("id");
		fields.add("contactName");
		fields.add("number");
		fields.add("subject");
		fields.add("message");
		
		smsTable=new DataTable<TMessage>(columnNames, fields);
		TableColumn idColumn=	smsTable.getColumnModel().getColumn(0);
		//隐藏id 列
		idColumn.setWidth(0);
		idColumn.setMaxWidth(0);
		idColumn.setMinWidth(0);
		smsTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0); 
		smsTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		TableColumn nameColumn=	smsTable.getColumn(MessageBundle.getMessage(SMSTABLE_HEAD_CONTACTOR_NAME));
		nameColumn.setPreferredWidth(150);
		nameColumn.setMaxWidth(160);
		nameColumn.setMinWidth(120);
		
		TableColumn numberColumn=	smsTable.getColumn(MessageBundle.getMessage(SMSTABLE_HEAD_CONTACTOR_NUMBER));
		numberColumn.setPreferredWidth(150);
		numberColumn.setMaxWidth(160);
		numberColumn.setMinWidth(120);
		
		
		TableColumn subjectColumn=	smsTable.getColumn(MessageBundle.getMessage(SMSTABLE_HEAD_SUBJECT));
		subjectColumn.setPreferredWidth(200);
		subjectColumn.setMaxWidth(300);
		subjectColumn.setMinWidth(150);
		
		JScrollPane  scrollSmsTable= new JScrollPane(smsTable);
		smsTablePanel.add(scrollSmsTable);

		smsTablePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		setContent(smsTablePanel);
		
		
	}
	
	public List<TMessage> getSms(){
		return msgService.getByPage();
	}
	
	
	public void updateSmsTable(){
		smsTable.setData(getSms());

//		smsTable.updateUI();
	}
	
	
}

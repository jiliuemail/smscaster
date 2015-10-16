package com.skyline.sms.caster.ui.content;

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

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.service.MessageService;
import com.skyline.sms.caster.service.impl.MessageServiceImpl;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;

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
		updateSmsTable();
	}
		
	public void initToolButton(){
		delSms=new JButton(MessageBundle.getMessage(TOOLBUTTON_DELSMS));
		updateSms=new JButton(MessageBundle.getMessage(TOOLBUTTON_UPDATE));
		addToolButton(delSms);
		addToolButton(updateSms);
		
		
		updateSms.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateSmsTable();
			}
		});
		
		
	}
	
	
	public void initSmsTable(){
		
		smsTablePanel=new JPanel();
		
		List<String> columnNames=new ArrayList<String>();
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_SUBJECT));
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_CONTACTOR_NAME));
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_CONTACTOR_NUMBER));
		columnNames.add(MessageBundle.getMessage(SMSTABLE_HEAD_MESSAGE));
		
		List<String> fields=new ArrayList<String>();	
		fields.add("subject");
		fields.add("contactName");
		fields.add("number");
		fields.add("message");
		
		smsTable=new DataTable<TMessage>(columnNames, fields);

		JScrollPane  scrollSmsTable= new JScrollPane(smsTable);
		smsTablePanel.add(scrollSmsTable);

//		scrollSmsTable.setPreferredSize(new Dimension(600, 300));

		
		//		smsTable.setSize(getWidth()-200, getHeight()-200);
		

	//	smsTablePanel.setPreferredSize(new Dimension(600, 300)); 被覆盖
		smsTablePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		setContent(smsTablePanel);
		
		
	}
	
	public List<TMessage> getSms(){
		return msgService.getByPage();
	}
	
	
	public void updateSmsTable(){
		smsTable.setData(getSms());
		smsTable.updateUI();
	}
}

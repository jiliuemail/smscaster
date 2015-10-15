package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputField;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;

public class SmsMessagePanel extends ContentPanel{
	
	private JPanel contentPanel;
	private InputPanel inputPanel;
	private InputField toNubmerField;
	private InputField toGroup;
	private InputField subject;
	
	private JPanel messagePanel;

	
	public SmsMessagePanel(String title){
		super(title);
		initToolButton();
		init();
	}
	
	public void initToolButton(){
		JButton toOutBox = new JButton("sendToOutbox");
		addToolButton(toOutBox);
		toOutBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void init(){

		initInput();
		initMessageArea();
		contentPanel=new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(inputPanel,BorderLayout.NORTH);
		contentPanel.add(messagePanel, BorderLayout.CENTER);
		setContent(contentPanel);
	}
	
	private void initInput(){
		inputPanel = new InputPanel();
		//inputPanel.setLayout(new BoxLayout(inputPanel, 3));
		toNubmerField = new InputTextField("sms.caster.label.input.tonumber", 60);
		inputPanel.addInputField(toNubmerField);
		toGroup = new InputTextField("sms.caster.label.input.togroup", 60);
		inputPanel.addInputField(toGroup);
		subject = new InputTextField("sms.caster.label.input.subject", 80);
		inputPanel.addInputField(subject);
		inputPanel.setPreferredSize(new Dimension(300,toNubmerField.getHeight()*4 ));
//		inputPanel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
	}

	public void initMessageArea(){
		messagePanel=new JPanel();
		JTextArea messageArea=new JTextArea("input message here");
		messageArea.setLineWrap(true);
		JScrollPane scrollMessage= new JScrollPane(messageArea);
		scrollMessage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollMessage.setPreferredSize(new Dimension(UIConstants.MAX_FRAME_WIDTH-UIConstants.COMPONENT_WIDTH_UNIT-200,400));
		messagePanel.add(scrollMessage);

	}
	
	public TMessage getMessage(){
		//String contactors=toNubmerField.getInputField().gett //getText() ????;父类继承的问题??
		
		return null;
	}
}

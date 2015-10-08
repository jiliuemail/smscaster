package com.skyline.sms.caster.ui.content;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputField;

public class SmsMessagePanel extends ContentPanel{
	
	private JPanel inputPanel;
	private InputField toNubmerField;
	private InputField toGroup;
	private InputField subject;
	
	public SmsMessagePanel(String title){
		super(title);
		initInput();
	}
	
	private void initInput(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, 3));
		toNubmerField = new InputField("sms.caster.label.input.tonumber", 60);
		inputPanel.add(toNubmerField);
		toGroup = new InputField("sms.caster.label.input.togroup", 60);
		inputPanel.add(toGroup);
		subject = new InputField("sms.caster.label.input.subject", 80);
		inputPanel.add(subject);
		setContent(inputPanel);
	}

}

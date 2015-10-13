package com.skyline.sms.caster.ui.content;

import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputField;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;

public class SmsMessagePanel extends ContentPanel{
	
	private InputPanel inputPanel;
	private InputField toNubmerField;
	private InputField toGroup;
	private InputField subject;
	
	public SmsMessagePanel(String title){
		super(title);
		initInput();
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
		
		setContent(inputPanel);
	}

}

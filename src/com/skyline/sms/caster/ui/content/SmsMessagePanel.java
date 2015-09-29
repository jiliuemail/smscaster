package com.skyline.sms.caster.ui.content;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.skyline.sms.caster.ui.component.InputField;

public class SmsMessagePanel extends JPanel {
	
	private InputField toNubmerField;
	private InputField toGroup;
	private InputField subject;
	
	public SmsMessagePanel(){
		
		setLayout(new BoxLayout(this, 3));
		initInput();
	}
	
	private void initInput(){
		toNubmerField = new InputField("sms.caster.label.input.tonumber", 60);
		add(toNubmerField);
		toGroup = new InputField("sms.caster.label.input.togroup", 60);
		add(toGroup);
		subject = new InputField("sms.caster.label.input.subject", 80);
		add(subject);
	}

}

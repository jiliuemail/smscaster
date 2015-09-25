package com.skyline.sms.caster.ui.component;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.skyline.sms.caster.core.MessageBundle;

public class MessageLabel extends JLabel {

	public MessageLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	public MessageLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public MessageLabel(String text) {
		super(text);
	}

	@Override
	public void setText(String text) {
		super.setText(MessageBundle.getMessage(text));
	}
	
	

}

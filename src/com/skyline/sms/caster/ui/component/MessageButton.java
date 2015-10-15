package com.skyline.sms.caster.ui.component;

import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import com.skyline.sms.caster.core.MessageBundle;

public class MessageButton extends JButton {

	public MessageButton() {
		super();
	}

	public MessageButton(Action a) {
		super(a);
	}

	public MessageButton(Icon icon) {
		super(icon);
	}

	public MessageButton(String text, Icon icon) {
		super(text, icon);
	}

	public MessageButton(String text) {
		super(text);
	}
	
	public void setText(String text) {
		super.setText(MessageBundle.getMessage(text));
	}
	
	public void marginHorizontal(int margin){
		if (margin > -1) {
			Insets insets = getMargin();
			setMargin(new Insets(insets.top, margin, insets.bottom, margin));
		}
	}
	
	public void marginVertical(int margin){
		if (margin > -1) {
			Insets insets = getMargin();
			setMargin(new Insets(margin, insets.left, margin, insets.right));
		}
	}
	
}

package com.skyline.sms.caster.ui.component;

import javax.swing.JTextField;

public class InputTextField extends InputField<JTextField> {
	
	

	public InputTextField(String label) {
		this(label, 6);
	}
	
	public InputTextField(String label, int columns) {
		super(label, new JTextField(columns));
	}

}

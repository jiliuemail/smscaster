package com.skyline.sms.caster.ui.component;

import javax.swing.JComboBox;

public class InputComboBox<E> extends InputField<JComboBox<E>> {
	

	public InputComboBox(String label) {
		super(label, new JComboBox<E>());
	}
	
	public void addItem(E item){
		getInputField().addItem(item);
	}

}

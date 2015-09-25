package com.skyline.sms.caster.ui.component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.skyline.sms.caster.ui.MessageLabel;
import com.skyline.sms.caster.ui.UIConstants;

public class InputField extends JPanel {
	
	private JLabel inputLabel;
	private JTextField inputField;
	
	public InputField(String label){
		this(label, 6);
	}
	
	public InputField(String label, int fieldLength){
		inputLabel = new MessageLabel(label);
		inputLabel.setBounds(0,0,UIConstants.COMPONENT_WIDTH_UNIT * 15, UIConstants.COMPONENT_HEIGHT_UNIT);
		inputLabel.setOpaque(true);
		inputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputField = new JTextField(fieldLength);
		inputField.setBounds(UIConstants.COMPONENT_WIDTH_UNIT * 16, 0, UIConstants.COMPONENT_WIDTH_UNIT * fieldLength, UIConstants.COMPONENT_HEIGHT_UNIT);
		initLayout();
	}
	
	private void initLayout(){
		this.setLayout(null);
		this.add(inputLabel);
		this.add(inputField);
	}
	
	public void setInputLabel(String label){
		inputLabel.setText(label);
	}
	
	public void setFieldLength(int fieldLength){
		inputField.setColumns(fieldLength);
	}

	public void setInputValue(String value){
		inputField.setText(value);
	}

	public JLabel getInputLabel() {
		return inputLabel;
	}

	public JTextField getInputField() {
		return inputField;
	}
	
	
}

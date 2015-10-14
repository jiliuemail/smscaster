package com.skyline.sms.caster.ui.component;

import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.skyline.sms.caster.ui.UIConstants;

public class InputField<C extends JComponent> extends JPanel {
	
	protected JLabel inputLabel;
	protected C inputField;
	
	protected Insets insets;
	
	protected int labelWidth;
	protected int fieldWidth;
	

	public InputField(String label, C inputField){
		insets = new Insets(3, 16, 3, 16);
		// init label
		inputLabel = new MessageLabel(label);
		this.inputField = inputField;
		setLabelWidth((int)inputLabel.getPreferredSize().getWidth());
		setFieldWidth((int)inputField.getPreferredSize().getWidth());
		inputLabel.setOpaque(true);
		inputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		initLayout();
	}

	private void setFieldBound() {
		inputField.setBounds(insets.left*2+insets.right + getLabelWidth(), insets.top, getFieldWidth(), UIConstants.COMPONENT_HEIGHT_UNIT);
	}

	private void setLabelBound() {
		inputLabel.setBounds(insets.left,insets.top,getLabelWidth(), UIConstants.COMPONENT_HEIGHT_UNIT);
	}
	
	
	
	

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
		setLabelBound();
		setFieldBound();
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public void setFieldWidth(int fieldWidth) {
		this.fieldWidth = fieldWidth;
		setFieldBound();
	}

	private void initLayout(){
		this.setLayout(null);
		this.add(inputLabel);
		this.add(inputField);
	}
	
	public void setInputLabel(String label){
		inputLabel.setText(label);
		setLabelWidth(inputLabel.getWidth());
	}
	

	public JLabel getInputLabel() {
		return inputLabel;
	}

	public C getInputField() {
		return inputField;
	}
	
	public void setInputField(C inputField) {
		if (inputField == null) {
			return;
		}
		if (this.inputField != null) {
			remove(this.inputField);
		}
		this.inputField = inputField;
		setFieldWidth((int)inputField.getPreferredSize().getWidth());
		add(this.inputField);
	}

	public int getWidth(){
		return labelWidth + fieldWidth + insets.left * 2 + insets.right * 2;
	}
	
	@Override
	public int getHeight() {
		return (int)Math.max(inputLabel.getPreferredSize().getHeight(), inputField.getPreferredSize().getHeight()) + insets.top + insets.bottom;
	}
		

}

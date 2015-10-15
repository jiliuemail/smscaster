package com.skyline.sms.caster.ui.component;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InputPanel extends JPanel {
	
	private JPanel fieldsPanel;
	private int maxLabelWidth = 0;
	
	public InputPanel(){
		setLayout(null);
		initFieldsPanel();
	}

	private void initFieldsPanel(){
		fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
		add(fieldsPanel);
	}
	
	
	public void addInputField(InputField<?> inputField){
		// 统一Label宽度
		maxLabelWidth = Math.max(maxLabelWidth, inputField.getLabelWidth());
		inputField.setLabelWidth(maxLabelWidth);
		int nOfComponent = fieldsPanel.getComponentCount();
		for (int i = 0; i < nOfComponent; i++) {
			((InputField<?>)fieldsPanel.getComponent(i)).setLabelWidth(maxLabelWidth);
		}
		fieldsPanel.add(inputField);
		int width = Math.max(fieldsPanel.getWidth(), inputField.getWidth());
		int height = fieldsPanel.getHeight() + inputField.getHeight();
		fieldsPanel.setBounds(0,0,width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setBounds(0, 0, width, height);
	}
	
}

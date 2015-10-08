package com.skyline.sms.caster.ui.component;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skyline.sms.caster.ui.UIConstants;

public class ContentPanel extends JPanel {
	
	private static int CONTENT_WIDTH = UIConstants.SCREEN_WIDTH - UIConstants.WIDTH_UNIT;
	
	private JLabel titleLabel;
	private JPanel toolPanel;
	private JPanel content;
	
	private int nextButtonPosition = 0;
	
	public ContentPanel(String title){
		setName(title);
		initTitleLabel(title);
		initToolPanel();
		afterInit();
	}
	
	private void initTitleLabel(String title){
		titleLabel = new MessageLabel(title);
		titleLabel.setBounds(0, 0, CONTENT_WIDTH, UIConstants.HEIGHT_UNIT);
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.GRAY);
	}
	
	private void initToolPanel(){
		toolPanel = new JPanel();
		toolPanel.setBackground(Color.LIGHT_GRAY);
		toolPanel.setBounds(0, UIConstants.HEIGHT_UNIT, CONTENT_WIDTH, UIConstants.HEIGHT_UNIT);
		toolPanel.setLayout(null);
	}
	
	public void setTitle(String title){
		titleLabel.setText(title);
	}

	public void addToolButton(JButton button){
		if (button == null) {
			return;
		}
		int buttonWidth = button.getText().length() * UIConstants.COMPONENT_WIDTH_UNIT + UIConstants.WIDTH_UNIT;
		if (button.getIcon() != null) {
			buttonWidth = buttonWidth + button.getIcon().getIconWidth();
		}
		
		button.setBounds(nextButtonPosition, 0, buttonWidth, UIConstants.HEIGHT_UNIT);
				
		toolPanel.add(button);
		nextButtonPosition = nextButtonPosition + buttonWidth;
	}
	
	public void setContent(JPanel contentPanel){
		if(contentPanel != null){
			if (content != null) {
				this.remove(content);
			}
			content = contentPanel;
			content.setBounds(0, UIConstants.HEIGHT_UNIT * 2 + 5, CONTENT_WIDTH, UIConstants.HEIGHT_UNIT * 2);
			this.add(content);
		}
	}
	
	public void afterInit(){
		this.setLayout(null);
		this.add(titleLabel);
		this.add(toolPanel);
	}
	
	public void beforeDisplay(){
		
	}

	public void afterDisplay(){
		
	}
	
}

package com.skyline.sms.caster.ui.component;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skyline.sms.caster.ui.UIConstants;

public class ContentPanel extends JPanel {
	
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
		
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.GRAY);
	}
	
	private void initToolPanel(){
		toolPanel = new JPanel();
		toolPanel.setBackground(Color.LIGHT_GRAY);
		
		toolPanel.setLayout(null);
	}
	
	public void setTitle(String title){
		titleLabel.setText(title);
	}
	
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		resizeComponent();
	}

	private void resizeComponent() {
		if (titleLabel != null) {
			titleLabel.setBounds(0, 0, getWidth(), UIConstants.HEIGHT_UNIT);
		}
		if (toolPanel != null) {
			toolPanel.setBounds(0, UIConstants.HEIGHT_UNIT, getWidth(), UIConstants.HEIGHT_UNIT);
		}
		if (content != null) {
			int topHeight = UIConstants.HEIGHT_UNIT * 2 + 15;
			content.setBounds(0, topHeight, getWidth(), getHeight() - topHeight);
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		resizeComponent();
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

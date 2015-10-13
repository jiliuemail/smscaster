package com.skyline.sms.caster.ui;

import javax.swing.JFrame;

import com.skyline.sms.caster.core.MessageBundle;


public class MainFrame extends BaseFrame {
	
	
	private MainPanel mainPanel;

	public MainFrame(){
		initTitle();
		initMainFrame();
	}

	private void initTitle(){
		this.setTitle(MessageBundle.getMessage("sms.caster.label.main.frame.title"));
	}
	
	private void initMainFrame(){
		this.setSize(UIConstants.WIDTH_UNIT * 5, UIConstants.HEIGHT_UNIT * 14);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new MainPanel();
		this.getContentPane().add(mainPanel);
		this.setVisible(true);
	}
	
	
	
	
}

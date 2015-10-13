package com.skyline.sms.caster.ui;

import java.awt.Dimension;

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

		this.setBounds(UIConstants.MAX_FRAME_BOUND);
		Dimension frameSize = new Dimension(MAX_FRAME_WIDTH, MAX_FRAME_HEIGHT);
		this.setMaximumSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setPreferredSize(frameSize);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new MainPanel();
		this.getContentPane().add(mainPanel);
		this.setVisible(true);
	}
	
	
	
	
}

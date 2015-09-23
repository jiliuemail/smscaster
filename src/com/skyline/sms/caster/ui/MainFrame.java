package com.skyline.sms.caster.ui;

import java.awt.Container;

import javax.swing.JFrame;

import com.skyline.sms.caster.core.MessageBundle;


public class MainFrame extends BaseFrame {
	

	private Container container;
	
	
	public MainFrame(){

		initMainFrame();
	}

	private void initTitle(){
		setTitle(MessageBundle.getMessage("sms.caster.label.main.frame.title"));
	}
	
	private void initMainFrame(){
		initTitle();
		container = this.getContentPane();
		container.removeAll();
		container.add(new MainPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	
	
	
}

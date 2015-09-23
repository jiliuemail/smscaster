package com.skyline.sms.caster.ui.toolbar;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.skyline.sms.caster.core.MessageBundle;

public class MainToolBar extends JToolBar {
	
	private JButton startButton;
	
	public MainToolBar(){
		startButton =  new JButton(MessageBundle.getMessage("sms.caster.label.button.start"));
		add(startButton);
	}

}

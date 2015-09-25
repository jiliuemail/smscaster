package com.skyline.sms.caster.ui.toolbar;

import javax.swing.JToolBar;

import com.skyline.sms.caster.ui.component.ImageButton;

public class MainToolBar extends JToolBar {
	
	private ImageButton startButton;
	private ImageButton sendButton;
	private ImageButton receiveButton;
	
	public MainToolBar(){
		startButton =  new ImageButton("sms.caster.label.button.start");
		startButton.setImagePath("resource/image/start_button.png");
		
		add(startButton);
		
		sendButton =  new ImageButton("sms.caster.label.button.send");
		sendButton.setImagePath("resource/image/send_off.png");
		
		add(sendButton);
		
		receiveButton =  new ImageButton("sms.caster.label.button.receive");
		receiveButton.setImagePath("resource/image/receive.png");
		
		add(receiveButton);
	}
	

}

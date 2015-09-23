package com.skyline.sms.caster.ui.explorer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.skyline.sms.caster.core.MessageBundle;

public class ExplorerPanel extends JPanel{
	
	private JButton composeButton;
	
	public ExplorerPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addButtons();
	}
	
	
	private void addButtons(){
		composeButton = new JButton(MessageBundle.getMessage("sms.caster.label.button.compose"));
		add(composeButton);
	}

}

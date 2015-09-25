package com.skyline.sms.caster;

import java.awt.Font;

import javax.swing.UIManager;

import com.skyline.sms.caster.ui.StartFrame;

public class SmsCaster {

	public static void main(String[] args) {
		UIManager.put("Label.font", new Font(Font.SANS_SERIF,Font.PLAIN,14));
		UIManager.put("Button.font", new Font(Font.SANS_SERIF,Font.PLAIN,14));
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
		}
		
		new StartFrame();
	}

}

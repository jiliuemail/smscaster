package com.skyline.sms.caster;

import java.awt.Font;

import javax.swing.UIManager;

import com.skyline.sms.caster.ui.StartFrame;

/**
 * 主类，启动时首先执行
 *
 */
public class SmsCaster {

	public static void main(String[] args) {
		// 设置字体
		UIManager.put("Label.font", new Font(Font.SANS_SERIF,Font.PLAIN,14));
		UIManager.put("Button.font", new Font(Font.SANS_SERIF,Font.PLAIN,14));
		// 设置样式
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
		}

		new StartFrame();
	}

}

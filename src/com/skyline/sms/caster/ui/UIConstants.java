package com.skyline.sms.caster.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * UI常量
 *
 */
public interface UIConstants {
	
	// 布局高宽单元
	public static final int HEIGHT_UNIT = 40;
	public static final int WIDTH_UNIT = 160;
	
	// 组件高宽单元
	public static final int COMPONENT_HEIGHT_UNIT = 20;
	public static final int COMPONENT_WIDTH_UNIT = 10;
	
	// 屏幕大小
	public static final Dimension SCREEN_DEMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = Double.valueOf(SCREEN_DEMENSION.getWidth()).intValue();
	public static final int SCREEN_HEIGHT = Double.valueOf(SCREEN_DEMENSION.getHeight()).intValue();
	
	// 各个内容面板的KEY，用于布局
	public static final String COMPOSE_PANEL_KEY = "componsePanel";
	public static final String CONTACTS_PANEL_KEY = "contactsPanel";


}

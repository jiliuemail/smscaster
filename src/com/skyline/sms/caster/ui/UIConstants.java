package com.skyline.sms.caster.ui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

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
	public static final Rectangle MAX_FRAME_BOUND = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	public static final int MAX_FRAME_WIDTH = Double.valueOf(MAX_FRAME_BOUND.getWidth()).intValue(); 
	public static final int MAX_FRAME_HEIGHT = Double.valueOf(MAX_FRAME_BOUND.getHeight()).intValue();
	
	// 各个内容面板的KEY，用于布局
	public static final String COMPOSE_PANEL_KEY = "componsePanel";
	public static final String CONTACTS_PANEL_KEY = "contactsPanel";
	public static final String GROUPS_PANEL_KEY = "groupsPanel";
	public static final String PHONES_PANEL_KEY = "phonesPanel";


}

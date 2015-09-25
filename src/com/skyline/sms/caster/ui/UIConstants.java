package com.skyline.sms.caster.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

public interface UIConstants {
	
	public static final int HEIGHT_UNIT = 40;
	public static final int WIDTH_UNIT = 160;
	
	public static final int COMPONENT_HEIGHT_UNIT = 20;
	public static final int COMPONENT_WIDTH_UNIT = 10;
	
	public static final Dimension SCREEN_DEMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = Double.valueOf(SCREEN_DEMENSION.getWidth()).intValue();
	public static final int SCREEN_HEIGHT = Double.valueOf(SCREEN_DEMENSION.getHeight()).intValue();


}

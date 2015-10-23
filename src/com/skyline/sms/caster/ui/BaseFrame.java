package com.skyline.sms.caster.ui;

import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JFrame;

import com.skyline.sms.caster.util.ImageUtil;

public class BaseFrame extends JFrame implements UIConstants{

	public BaseFrame() throws HeadlessException {
		super();
		setDefaultIconImage();
	}

	public BaseFrame(String title) throws HeadlessException {
		super(title);
		setDefaultIconImage();
	}
	
	protected void setDefaultIconImage(){
		Image image = ImageUtil.loadImage("resource/image/logo.png");
		if (image != null) {this.setIconImage(image);}
	}

}

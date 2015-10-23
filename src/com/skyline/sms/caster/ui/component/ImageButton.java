package com.skyline.sms.caster.ui.component;

import java.awt.Image;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.skyline.sms.caster.util.ImageUtil;

public class ImageButton extends MessageButton {
	

	public ImageButton() {
		super();
		setTextAlign();
	}

	public ImageButton(Action a) {
		super(a);
		setTextAlign();
	}

	public ImageButton(String text) {
		super(text);
		setName(text);
		setTextAlign();
	}
	
	private void setTextAlign(){
		super.setHorizontalAlignment(SwingConstants.LEFT);
	}


	public void setImagePath(String imagePath) {
		Image iconImage = ImageUtil.loadImage(imagePath);
		if (iconImage != null) {
			super.setIcon(new ImageIcon(iconImage));
		}
	}
	
	

}

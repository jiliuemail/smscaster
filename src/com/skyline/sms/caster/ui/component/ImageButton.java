package com.skyline.sms.caster.ui.component;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.skyline.sms.caster.util.IOUtil;
import com.skyline.sms.caster.util.LogUtil;

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
		Image iconImage = null;
		InputStream input = null;
		try {
			input = IOUtil.getResource(imagePath);
			iconImage = ImageIO.read(input);
		} catch (Exception e) {
			LogUtil.error(e);
		}finally {
			IOUtil.quietCloseStream(input);
		}
		
		if (iconImage != null) {
			super.setIcon(new ImageIcon(iconImage));
		}
	}
	
	

}

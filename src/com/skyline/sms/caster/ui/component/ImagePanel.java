package com.skyline.sms.caster.ui.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.skyline.sms.caster.util.ImageUtil;


public class ImagePanel extends JPanel {
	
	private Image image;
	
	public ImagePanel(String imagePath) {
		image = ImageUtil.loadImage(imagePath);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image == null) {
			super.paintComponent(g);
		}else{
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
}

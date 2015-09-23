package com.skyline.sms.caster.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.skyline.sms.caster.util.IOUtil;
import com.skyline.sms.caster.util.LogUtil;

public class ImagePanel extends JPanel {
	
	private Image image;
	
	public ImagePanel(String imagePath) {
		InputStream imageStream = null;
		try {
			imageStream = IOUtil.getResource(imagePath);
			image = ImageIO.read(imageStream);
		} catch (Exception e) {
			LogUtil.error(e);
		}finally {
			IOUtil.quietCloseStream(imageStream);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
	
}

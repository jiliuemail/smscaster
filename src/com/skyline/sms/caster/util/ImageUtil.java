package com.skyline.sms.caster.util;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	private ImageUtil(){}
	
	
	public static Image loadImage(String imageFilePath){
		Image image = null;
		InputStream input = null;
		try {
			input = IOUtil.getResource(imageFilePath);
			image = ImageIO.read(input);
		} catch (Exception e) {
			LogUtil.error(e);
		}finally {
			IOUtil.quietCloseStream(input);
		}
		return image;
	}

}

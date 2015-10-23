package com.skyline.sms.caster.ui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JDialog;

import com.skyline.sms.caster.util.ImageUtil;

public class BaseDialog extends JDialog implements UIConstants{

	public BaseDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		setDefaultIconImage();
	}

	public BaseDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		setDefaultIconImage();
	}

	public BaseDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		setDefaultIconImage();
	}

	public BaseDialog(Dialog owner, String title) {
		super(owner, title);
		setDefaultIconImage();
	}

	public BaseDialog(Dialog owner) {
		super(owner);
		setDefaultIconImage();
	}

	public BaseDialog(Frame owner, boolean modal) {
		super(owner, modal);
		setDefaultIconImage();
	}

	public BaseDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		setDefaultIconImage();
	}

	public BaseDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		setDefaultIconImage();
	}

	public BaseDialog(Frame owner, String title) {
		super(owner, title);
		setDefaultIconImage();
	}

	public BaseDialog(Frame owner) {
		super(owner);
		setDefaultIconImage();
	}

	public BaseDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		setDefaultIconImage();
	}

	public BaseDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		setDefaultIconImage();
	}

	public BaseDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		setDefaultIconImage();
	}

	public BaseDialog(Window owner, String title) {
		super(owner, title);
		setDefaultIconImage();
	}

	public BaseDialog(Window owner) {
		super(owner);
		setDefaultIconImage();
	}
	
	protected void setDefaultIconImage(){
		Image image = ImageUtil.loadImage("resource/image/logo.png");
		if (image != null) {this.setIconImage(image);}
	}

}

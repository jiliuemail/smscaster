package com.skyline.sms.caster.util;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.ui.component.Toast;

public class DialogUtil {
	
	private static JFrame mainFrame;
	
	private DialogUtil(){}
	
	public static void registryMainFrame(JFrame frame){
		mainFrame = frame;
	}
	
	public static JFrame getMainFrame(){
		return mainFrame;
	}
	
	public static void showSaveOK(Component parentComponent){
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.save.ok")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showSaveError(Component parentComponent){
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.save.error")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	public static void showSearchError(Component parentComponent){
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.search.error")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showToast(String message){
		showToast(null,message);
	}
	
	public static void showToast(Window parentWindow, String message){
		parentWindow = (parentWindow == null ? mainFrame : parentWindow);
		Toast toast = new Toast(parentWindow, MessageBundle.getMessage(message), 2000, Toast.MESSAGE_STYLE);
		toast.start();
	}

}

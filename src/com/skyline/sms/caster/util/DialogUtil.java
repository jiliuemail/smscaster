package com.skyline.sms.caster.util;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.ui.component.Toast;

/**
 * 提示框工具类，提供常用的提示
 * 
 * @author linyn
 *
 * @since 2015年10月8日
 */
public class DialogUtil {
	
	private static JFrame mainFrame;
	
	private static Toast loadDataToast;
	
	private DialogUtil(){}
	
	private static void initToast(){
		loadDataToast = new Toast(DialogUtil.getMainFrame(), "sms.caster.message.toast.data.load");
	}
	
	public static void registryMainFrame(JFrame frame){
		mainFrame = frame;
	}
	
	public static JFrame getMainFrame(){
		return mainFrame;
	}
	
	public static void showSaveOK(){
		showSaveOK(null);
	}
	
	public static void showDeleteOK(){
		showDeleteOK(null);
	}
	
	
	public static void showSaveError(){
		showSaveError(null);
	}
	
	public static void showSearchError(){
		showSearchError(null);
	}
	
	public static void showDeleteError(){
		showDeleteError(null);
	}
	
	public static boolean showConfirmDialog(String message){
		return showConfirmDialog(null, message);
	}
	
	
	public static void showSaveOK(Component parentComponent){
		parentComponent = (parentComponent == null ? mainFrame : parentComponent);
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.save.ok")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showDeleteOK(Component parentComponent){
		parentComponent = (parentComponent == null ? mainFrame : parentComponent);
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.delete.ok")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void showSaveError(Component parentComponent){
		parentComponent = (parentComponent == null ? mainFrame : parentComponent);
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.save.error")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	public static void showSearchError(Component parentComponent){
		parentComponent = (parentComponent == null ? mainFrame : parentComponent);
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.search.error")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showDeleteError(Component parentComponent){
		parentComponent = (parentComponent == null ? mainFrame : parentComponent);
		JOptionPane.showMessageDialog(parentComponent
				, MessageBundle.getMessage("sms.caster.message.dialog.content.delete.error")
				, MessageBundle.getMessage("sms.caster.message.dialog.title.operation")
				, JOptionPane.ERROR_MESSAGE);
	}
	
	public static boolean showConfirmDialog(Component parentComponent, String message){
		parentComponent = (parentComponent == null ? mainFrame : parentComponent);
		return JOptionPane.showConfirmDialog(parentComponent, MessageBundle.getMessage(message)
				, MessageBundle.getMessage("sms.caster.message.dialog.title.confirm")
				, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
	}
	
	public static void showToast(String message){
		showToast(null,message);
	}
	
	/**
	 * 弹出吐司提示框，2秒后自动消失
	 * @param parentWindow
	 * @param message
	 */
	public static void showToast(Window parentWindow, String message){
		parentWindow = (parentWindow == null ? mainFrame : parentWindow);
		Toast toast = new Toast(parentWindow, MessageBundle.getMessage(message), 2000, Toast.MESSAGE_STYLE);
		toast.start();
	}
	
	public static void showLoadDataToast(){
		if (loadDataToast == null) {
			initToast();
		}
		loadDataToast.setVisible(true);
	}
	
	public static void hiddenLoadDataToast(){
		if (loadDataToast == null) {
			initToast();
		}
		loadDataToast.setVisible(false);
	}

}

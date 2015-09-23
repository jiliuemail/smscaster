package com.skyline.sms.caster.util;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.core.Operator;

/**
 * 
 * 使用此工具执行操作时出错会弹框提示
 *
 */
public class OperatiorUtil {

	public static String ERROR_MESSAGE_TITLE = "sms.caster.message.dialog.title.error";

	private OperatiorUtil() {}

	/**
	 * 执行操作时出错会弹框提示
	 * @param component 父窗口
	 * @param messageKey 提示信息，支持国际化
	 * @param operator 执行的具体
	 */
	public static void doOperation(Component component, String messageKey, Operator operator) {
		if (operator == null) {
			return;
		}
		try {
			operator.doOperation();
		} catch (Exception e) {
			if (!StringUtil.hasText(messageKey)) {
				messageKey = e.getMessage();
			}
			JOptionPane.showMessageDialog(component, messageKey, MessageBundle.getMessage(ERROR_MESSAGE_TITLE),
					JOptionPane.ERROR_MESSAGE);
		}
	}

}

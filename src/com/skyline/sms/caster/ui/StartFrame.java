package com.skyline.sms.caster.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.ui.component.ImagePanel;
import com.skyline.sms.caster.util.DialogUtil;

/**
 * 启动时显示的界面，用于选择语言
 * 
 * 后续可添加用户名与密码框作登录界面
 *
 */
public class StartFrame extends BaseFrame{
	

	private Container container;
	private JPanel background;
	
	public StartFrame(){
		initStartFrame();
	}
	
	protected void initStartFrame() {
		initSize();
		initBackground();
		initContent();
	}

	private void initSize(){
		this.setBounds((int)((MAX_FRAME_WIDTH - WIDTH_UNIT * 4)/2), (int)((MAX_FRAME_HEIGHT - HEIGHT_UNIT * 2)/2), WIDTH_UNIT * 4, HEIGHT_UNIT * 2);
	}
	
	// 设置背景
	private void initBackground(){
		background = new ImagePanel("resource/image/bg.jpg");
	}
	
	private void initContent(){
		container = this.getContentPane();
		container.setLayout(new BorderLayout());
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		background.add(topPanel); // 界面上方留白
		background.add(new LanguagePanel()); // 语言选择面板
		this.setUndecorated(true);
		container.add(background);
		this.setVisible(true);
	}
	
	class LanguagePanel extends JPanel{
			
			private JLabel selectTip;
			private JComboBox<LocaleSelectItem> localeComboBox;
			private LocaleSelectItem localeItem;
			private JButton okButton;
			private JButton exitButton;

			public LanguagePanel() {
				super();
				selectTip = new JLabel("选择语言(Select Languge)");
				localeComboBox = new JComboBox<LocaleSelectItem>();
				localeComboBox.addItem(new LocaleSelectItem("默认(default)",null));
				localeComboBox.addItem(new LocaleSelectItem("英文(english)", new Locale("en", "US")));
				localeComboBox.addItem(new LocaleSelectItem("中文(chiness)", new Locale("zh", "CN")));
				
				okButton = new JButton("确定(OK)");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean finishBuildMessageBundle = false;
						localeItem = (LocaleSelectItem)localeComboBox.getSelectedItem();
						try {
							MessageBundle.buildMessageBundle(localeItem.getLocale());
							JComponent.setDefaultLocale(localeItem.getLocale());
							finishBuildMessageBundle = true;
						} catch (FileNotFoundException ex) {
							JOptionPane.showMessageDialog(StartFrame.this, ex.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} catch (IOException ex) {
							JOptionPane.showMessageDialog(StartFrame.this, ex.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
						if (finishBuildMessageBundle) {
							okButton.setEnabled(false);
							StartFrame.this.dispose();
							JFrame mainFrame = new MainFrame();
							DialogUtil.registryMainFrame(mainFrame);
						}
					}
				});

				exitButton = new JButton("退出(Exit)");
				exitButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						StartFrame.this.dispose();
						System.exit(0);
					}
				});
				this.setOpaque(false);
				add(selectTip);
				add(localeComboBox);
				add(okButton);
				add(exitButton);
			}
			
	 }
			
	class LocaleSelectItem{
		
		private String name;
		private Locale locale;
		
		public LocaleSelectItem(String name, Locale locale) {
			super();
			this.name = name;
			this.locale = locale;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Locale getLocale() {
			return locale;
		}

		public void setLocale(Locale locale) {
			this.locale = locale;
		}
		
		public String toString() {
			return this.name;
		}
		
	}

}

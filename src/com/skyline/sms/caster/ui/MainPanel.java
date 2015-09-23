package com.skyline.sms.caster.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.skyline.sms.caster.ui.explorer.ExplorerPanel;
import com.skyline.sms.caster.ui.toolbar.MainToolBar;

public class MainPanel extends JPanel {
	
	private JToolBar toolBar;
	private JPanel explorer;
	
	public MainPanel(){
		setLayout(new BorderLayout());
		initToolbar();
		initLeftMenu();
	}
	
	private void initToolbar(){
		toolBar = new MainToolBar();
		add(toolBar, BorderLayout.NORTH);
	}
	
	private void initLeftMenu(){
		explorer = new ExplorerPanel();
		explorer.setBounds(0, 29, UIConstants.COL_WIDTH, UIConstants.ROW_HEIGHT * 4);
		add(explorer,BorderLayout.WEST);
	}

}

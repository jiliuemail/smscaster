package com.skyline.sms.caster.ui.component;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CheckBoxsPanel<T> extends JPanel {
	
	private Map<T, JCheckBox> boxs;
	
	public CheckBoxsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		boxs = new HashMap<T, JCheckBox>();
	}
	
	public void addCheckItem(T item){
		if (item == null) {
			return;
		}
		
		JCheckBox box = new JCheckBox(item.toString(), true);
		add(box);
		boxs.put(item, box);
		updateUI();
	}

}

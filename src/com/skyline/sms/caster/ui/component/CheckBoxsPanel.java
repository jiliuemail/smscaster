package com.skyline.sms.caster.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.skyline.sms.caster.util.CollectionUtil;

public class CheckBoxsPanel<T> extends JPanel {
	
	private Map<T, JCheckBox> boxs;
	private List<ActionListener> listeners;
	private ActionListener checkBoxListener;
	
	public CheckBoxsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		boxs = new HashMap<T, JCheckBox>();
		listeners = new ArrayList<ActionListener>();
		initCheckBoxListener();
	}
	
	private void initCheckBoxListener(){
		checkBoxListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (ActionListener actionListener : listeners) {
					actionListener.actionPerformed(e);
				}
			}
		};
	}
	
	public synchronized void addCheckItem(T item){
		if (item == null) {
			return;
		}
		
		JCheckBox box = new JCheckBox(item.toString(), true);
		box.addActionListener(checkBoxListener);
		add(box);
		boxs.put(item, box);
		updateUI();
	}
	
	public synchronized void addCheckItems(Set<T> items){
		if (CollectionUtil.isEmpty(items)) {
			return;
		}
		for (T item : items) {
			JCheckBox box = new JCheckBox(item.toString(), true);
			box.addActionListener(checkBoxListener);
			add(box);
			boxs.put(item, box);
		}
		updateUI();
	}
	
	public synchronized Set<T> getSelectedItems(){
		Set<T> selectedItems = new HashSet<T>();
		Set<T> allItems = boxs.keySet();
		for (T item : allItems) {
			if (boxs.get(item).isSelected()) {
				selectedItems.add(item);
			}
		}
		return selectedItems;
	}
	
	public synchronized void removeAllItem(){
		removeAll();
		boxs.clear();
		updateUI();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		Set<T> allItems = boxs.keySet();
		for (T item : allItems) {
			boxs.get(item).setEnabled(enabled);
		}
	}
	
	public void addActionListener(ActionListener l){
		listeners.add(l);
	}
	
	public void removeActionListener(ActionListener l){
		listeners.remove(l);
	}

}

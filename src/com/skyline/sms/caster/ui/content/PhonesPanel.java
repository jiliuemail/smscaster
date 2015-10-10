package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputField;

public class PhonesPanel extends ContentPanel {

	private JButton update;
	private JButton updateAll;
	
	private JPanel jpanel;
	private JPanel phoneInfoPanel;

	private JScrollPane phoneListPane;

	
	
	public PhonesPanel(String title) {
		super(title);
		initToolBarButton();
		updateContent();
	}

	//---添加toolBar
	public void initToolBarButton(){
		update=new JButton();
		updateAll=new JButton();
		update.setText(MessageBundle.getMessage("sms.caster.label.button.update"));
		updateAll.setText(MessageBundle.getMessage("sms.caster.label.button.updateAll"));
		addToolButton(update);
		addToolButton(updateAll);
	}
	
	//----content
	
	public void updateContent(){
		jpanel=new JPanel();
		jpanel.setLayout(new BorderLayout());
		jpanel.setOpaque(true);
		jpanel.setBackground(Color.BLUE);

		initPhoneList();
	//	jpanel.add(phoneTable,BorderLayout.CENTER);
		jpanel.add(phoneListPane,BorderLayout.CENTER);
		initPhoneInfoPanel();
		jpanel.add(phoneInfoPanel,BorderLayout.SOUTH);
		
	
		setContent(jpanel);
		
	}
	
	
	public void initPhoneList(){

		String[] title={"选择","端口","状态"};
		Object[][] content={
				{true,"port1","ready"},
				{false,"port2","not ok"}
				
		};
		final JTable	 phoneTable=new JTable(content,title);
		TableColumnModel tcm=phoneTable.getColumnModel();

		tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		phoneTable.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount() == 1){
		            int columnIndex = phoneTable.columnAtPoint(e.getPoint()); //获取点击的列
		            int rowIndex = phoneTable.rowAtPoint(e.getPoint()); //获取点击的行

		            if(columnIndex == 0) {//第0列时，执行代码
		                if(phoneTable.getValueAt(rowIndex,columnIndex) == null){ //如果未初始化，则设置为false
		                	phoneTable.setValueAt(false, rowIndex, columnIndex);
		                  }

		                if(((Boolean)phoneTable.getValueAt(rowIndex,columnIndex)).booleanValue()){ //原来选中
		                	phoneTable.setValueAt(false, rowIndex, 0); //点击后，取消选中
		                  }
		                else {//原来未选中
		                	phoneTable.setValueAt(true, rowIndex, 0);
		                  }
		             }

		        }
		    }
		});
		phoneListPane= new JScrollPane(phoneTable);
		
		
	}
	
	
	class checkboxRender extends JCheckBox implements TableCellRenderer{
		checkboxRender(){
			super();
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
		//	JCheckBox checkbox = new JCheckBox
			
			return null;
		}
		
	}

	public void initPhoneInfoPanel(){

		phoneInfoPanel=new JPanel();
		phoneInfoPanel.setOpaque(true);
		phoneInfoPanel.setBackground(Color.red);
		phoneInfoPanel.setLayout(new BoxLayout(phoneInfoPanel,BoxLayout.Y_AXIS));
//		phoneInfoPanel.setLayout(null);
		phoneInfoPanel.setPreferredSize(new Dimension(100,300));  //设置borderLayout采用的大小....
		phoneInfoPanel.setOpaque(true);

		InputField csq=new InputField("sms.caster.label.input.csq", 60);  //为什么没有高度?? contentPanel ?
		InputField csca=new InputField("sms.caster.label.input.csca", 60);
		phoneInfoPanel.add(csq);
		phoneInfoPanel.add(csca);
		

	}
	
}

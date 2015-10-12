package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.regex.Pattern;

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

import jssc.SerialPortList;

import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.JsscPortList;
import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.service.PortService;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputField;
import com.skyline.sms.caster.util.LogUtil;

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
		
		Object[][] content=getPhonesListContent();
		
		 JTable  phoneTable=new JTable(content,title);
		TableColumnModel tcm=phoneTable.getColumnModel();
		
		tcm.getColumn(0).setCellRenderer(new JCheckBoxTableRender());
		tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
//		tcm.getColumn(0).setWidth(20); 控制列宽度.....

		phoneListPane= new JScrollPane(phoneTable);
		
		
	}
	
	//获取电脑上的端口
	public Object[][] getPhonesListContent() {
		String[] portNames = JsscPortList.getPortNames();

		int rowCount=portNames.length;
		int columnCount=3;
		
		String[] status=new String[rowCount];
		for(int j=0;j<rowCount;j++){
			try {
				status[j]=PortService.getInstance(JsscPort.getInstance(portNames[j])).getPortStatus(); //线性执行,会被阻塞死锁
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Object[][] cells = new Object[rowCount][columnCount];
		
		for(int i=0;i<rowCount;i++){
			cells[i][0]=false;
			cells[i][1]=portNames[i];
			cells[i][2]=status[i];
//			LogUtil.info(Arrays.toString(cells[i]));
		}
		
		return cells;
		
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
	
	
	//checkbox 渲染器
	class JCheckBoxTableRender extends JCheckBox implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			this.setSelected((boolean)value);
			return this;
		}}
	
	
}

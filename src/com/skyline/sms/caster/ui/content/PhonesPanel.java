package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jssc.SerialPortList;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.CommandFactory;
import com.skyline.sms.caster.cmd.message.CSCA;
import com.skyline.sms.caster.cmd.message.CSQ;
import com.skyline.sms.caster.cmd.parser.ParserRegister;
import com.skyline.sms.caster.connector.JsscPort;
import com.skyline.sms.caster.connector.JsscPortList;
import com.skyline.sms.caster.core.MessageBundle;
import com.skyline.sms.caster.executor.ATCommandExecutor;
import com.skyline.sms.caster.service.impl.PortServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.util.LogUtil;

public class PhonesPanel extends ContentPanel {

	private JButton update;
	private JButton updateAll;
	private JPanel jpanel;
	private JPanel phoneInfoPanel;

	private JTable  phoneTable;
	private JScrollPane phoneListPane;

	
	private InputTextField csq;
	private InputTextField csca;
	private Command csqCmd=CommandFactory.forOrigin(new CSQ());
	private Command cscaCmd=CommandFactory.forGet(new CSCA());;
	
	public PhonesPanel(String title) {
		super(title);
		initToolBarButton();
		initContent();
		
	}

	
	
	
	//---添加toolBar
	public void initToolBarButton(){
		update=new JButton();
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					updateStatus(getChoosedRows());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		updateAll=new JButton();
		updateAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jpanel.remove(phoneListPane);  //必须先移除,运行initPhoneList() 会修改phoneListPane 指向的对象,就不能移除了.
				initPhoneList();
				jpanel.add(phoneListPane,BorderLayout.CENTER);
				jpanel.updateUI();
			}
		});
		
		update.setText(MessageBundle.getMessage("sms.caster.label.button.update"));
		updateAll.setText(MessageBundle.getMessage("sms.caster.label.button.updateAll"));
		addToolButton(update);
		addToolButton(updateAll);
	}
	
	
	//----content
	
	public void initContent(){
		jpanel=new JPanel();
		jpanel.setLayout(new BorderLayout());
	//	jpanel.setOpaque(true);
	//	jpanel.setBackground(Color.BLUE);

		initPhoneList();
		jpanel.add(phoneListPane,BorderLayout.CENTER);
	
		initPhoneInfoPanel();
		jpanel.add(phoneInfoPanel,BorderLayout.SOUTH);
		
		setContent(jpanel);
		
	}
	
	
	public void initPhoneList(){

		String[] title=getTitle();
 		Object[][] content=getPhonesListContent();
		phoneTable=new JTable(content,title);
		phoneTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel tcm=phoneTable.getColumnModel();
		
		tcm.getColumn(0).setCellRenderer(new JCheckBoxTableRender());
		tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		tcm.getColumn(0).setPreferredWidth(UIConstants.COMPONENT_WIDTH_UNIT*5); //why can not change the width of column.
		
		phoneTable.addMouseListener(new tableMouseAdapter());
		phoneListPane= new JScrollPane(phoneTable);
		
		
	}
	

	
	//获取table 的header 
	public  String[] getTitle(){
		String checkboxTitle=MessageBundle.getMessage("sms.caster.jtable.phoneTable.checkbox");
		String port=MessageBundle.getMessage("sms.caster.jtable.phoneTable.port");
		String status = MessageBundle.getMessage("sms.caster.jtable.phoneTable.status");
		String[] title={checkboxTitle,port,status};
		return title;
	}

	
	//获取电脑上的端口
	public Object[][] getPhonesListContent() {
		String[] portNames = JsscPortList.getPortNames();
		
		int rowCount=portNames.length;
		int columnCount=getTitle().length;
		
		String[] status=new String[rowCount];
		for(int j=0;j<rowCount;j++){
			try {
				status[j]=PortServiceImpl.getInstance(JsscPort.getInstance(portNames[j])).getPortStatus(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				status[j]=e.getMessage();
				LogUtil.error(e);
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
	
	//获取被table 中被勾选的行
	public Integer[] getChoosedRows(){
		List<Integer> selectedRows= new ArrayList<Integer>();

		for(int i=0;i<phoneTable.getRowCount();i++){
			if((boolean)phoneTable.getValueAt(i, 0)){
				selectedRows.add(i);
			}
		}
		Integer[] rows=new Integer[selectedRows.size()];
		return selectedRows.toArray(rows);
	}
	
	//更新状态列
	public void updateStatus(Integer[] rows) throws Exception{
	
		for(int i :rows){
			String portName=(String)phoneTable.getValueAt(i, 1);
			String portStatus=PortServiceImpl.getInstance(JsscPort.getInstance(portName)).getPortStatus();
			phoneTable.setValueAt(portStatus, i, 2);
		}
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
		}
	}
	
	
	
	
	
	
	


	public void initPhoneInfoPanel(){

		phoneInfoPanel=new JPanel();
		phoneInfoPanel.setLayout(new BoxLayout(phoneInfoPanel,BoxLayout.Y_AXIS));

		phoneInfoPanel.setPreferredSize(new Dimension(100,300));  //设置borderLayout采用的大小....

		csq=new InputTextField("sms.caster.label.input.csq", 60);  //为什么没有高度?? contentPanel ?
		csca=new InputTextField("sms.caster.label.input.csca", 60);
		phoneInfoPanel.add(csq);
		phoneInfoPanel.add(csca);
		

	}
	
	
	
	
	class tableMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			int clickRow=phoneTable.convertRowIndexToModel(phoneTable.getSelectedRow());
			LogUtil.info("click "+clickRow);
			if(e.getClickCount()==1){
				String portName=(String)phoneTable.getValueAt(clickRow, 1);
				try {

					ExecuteResult csqValue=PortServiceImpl.getInstance(JsscPort.getInstance(portName)).execute(csqCmd);
					ExecuteResult cscaValue=PortServiceImpl.getInstance(JsscPort.getInstance(portName)).execute(cscaCmd);
					csq.getInputField().setText(ParserRegister.parserCommandResult(csqCmd, csqValue, String.class));
					csca.getInputField().setText(ParserRegister.parserCommandResult(cscaCmd, cscaValue, String.class));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}
	}
	
}

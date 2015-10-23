package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Checkbox;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
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
import javax.swing.table.TableColumn;
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
import com.skyline.sms.caster.pojo.PhonePort;
import com.skyline.sms.caster.service.impl.PortServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.DataTable;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.util.LogUtil;

public class PhonesPanel extends ContentPanel {

	private JButton update;
	private JButton updateAll;
	private JPanel jpanel;
	private JPanel phoneInfoPanel;

	private DataTable<PhonePort>  phonesTable;
	
	private Map<String, PhonePort> phonePortMap= new HashMap<String, PhonePort>();
	
	

	private InputTextField csq;
	private InputTextField csca;
	private Command csqCmd=CommandFactory.forOrigin(new CSQ());
	private Command cscaCmd=CommandFactory.forGet(new CSCA());;
	
	
	private final String TABLE_HEAD_CHECKBOX_NAME=MessageBundle.getMessage("sms.caster.jtable.phoneTable.checkbox");
	private final String TABLE_HEAD_PORT_NAME=MessageBundle.getMessage("sms.caster.jtable.phoneTable.port");
	private final String TABLE_HEAD_STATUS_NAME = MessageBundle.getMessage("sms.caster.jtable.phoneTable.status");
	
	
	private  PhonesPanel(String title) {
		super(title);
		initToolBarButton();
		initContent();
	}

	private  static class createInstance{
		private static PhonesPanel phonesPanel=new  PhonesPanel("sms.caster.label.panel.phones");
	 }
	
	public static PhonesPanel getInstance(){
		return createInstance.phonesPanel;
	}
	

	
	//---添加toolBar
	public void initToolBarButton(){
		update=new JButton();
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				updatePortInitStatus(getSelectedPortNames());
			}
		});
		
		
		updateAll=new JButton();
		updateAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				initPhoneTableData();
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

		initPhonesTable();
		JScrollPane phonesPane= new JScrollPane(phonesTable);
		jpanel.add(phonesPane,BorderLayout.CENTER);
	
		
		
		initPhoneInfoPanel();
		jpanel.add(phoneInfoPanel,BorderLayout.SOUTH);
		
		setContent(jpanel);
		
	}
	
	
	
	
	public void initPhonesTable(){
		List<String> columnNames =new ArrayList<>();
		columnNames.add(TABLE_HEAD_CHECKBOX_NAME);
		columnNames.add(TABLE_HEAD_PORT_NAME);
		columnNames.add(TABLE_HEAD_STATUS_NAME);

		List<String> fields =new ArrayList<>();
		fields.add("choosed");
		fields.add("portName");
		fields.add("status");
		
		phonesTable=new DataTable<>(columnNames, fields);
		
		TableColumn checkboxColumn=phonesTable.getColumn(TABLE_HEAD_CHECKBOX_NAME);
		TableColumn portNameColumn = phonesTable.getColumn(TABLE_HEAD_PORT_NAME);
		
		checkboxColumn.setMinWidth(20);
		checkboxColumn.setMaxWidth(20);
		portNameColumn.setMinWidth(UIConstants.WIDTH_UNIT);
		portNameColumn.setMaxWidth(UIConstants.WIDTH_UNIT*2);
		
		JCheckBoxTableRender checkboxRender=new JCheckBoxTableRender();  //公用render
		checkboxColumn.setHeaderRenderer(checkboxRender);
		checkboxColumn.setCellRenderer(checkboxRender);
		checkboxColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));

		


		
		
		phonesTable.addMouseListener(new tableMouseAdapter());
		phonesTable.getTableHeader().addMouseListener(new checkboxAdapter());
	}
	
	
	
	private  Set<PhonePort> getPhones(){
		phonePortMap= new HashMap<String, PhonePort>();
		String[] portNames = JsscPortList.getPortNames();	
		
		Set<PhonePort> phonePorts=new TreeSet<>();
		
		for(String portName:portNames){
			PhonePort phonePort =new PhonePort(portName);
			phonePort.setStatus(getPortInitStatus(portName));
			phonePorts.add(phonePort);
			phonePortMap.put(portName, phonePort); 
		}
		return phonePorts;
	}
	
	
	private String  getPortInitStatus(String portName){
		String status;
		try {
			status = PortServiceImpl.getInstance(JsscPort.getInstance(portName)).getPortStatus();
		} catch (Exception e) {
			LogUtil.error(e);
			status=e.getMessage();
		}
		return status;
	}
	
	
	//更新存在端口的初始状态
		public void updatePortInitStatus(Set<String> portNames) {
		
			for(String portName:portNames){
				String status=getPortInitStatus(portName);
				updatePortStatus(portName, status);
			}
		}
		
		
		public void  updatePortStatus(String portName,String status){
			PhonePort phonePort=phonePortMap.get(portName);
			phonePort.setStatus(status);
			phonesTable.notifyUpdateRowData(phonePort);

		}
		
		
		public void updatePort(PhonePort phonePort){
			phonesTable.notifyUpdateRowData(phonePort);
		}
		
		
		public PhonePort getPhonePortByName(String portName){
			return phonePortMap.get(portName);
		}
		

			
		
	//重新获取和创建所有端口数据	
	private void initPhoneTableData(){
		phonesTable.setData(new ArrayList<>(getPhones()));
	}
	
	

	

	
	//获取被table 中被勾选的行
	public List<Integer>  getChoosedRows(){
		List<Integer> selectedRows= new ArrayList<Integer>();
		for(int i=0;i<phonesTable.getRowCount();i++){
			if((boolean)phonesTable.getValueAt(i, phonesTable.getColumn(TABLE_HEAD_CHECKBOX_NAME).getModelIndex())){
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}
	
	
	/**
	 * 在发短信的时候要禁止移除选中的端口,否则移除的端口依然会发短信
	 */
	public   Set<String> getSelectedPortNames() {
		//保存已经被选的端口名
		Set<String> selectedPortNames = new ConcurrentSkipListSet<>(); //支持并发,排序的set
		selectedPortNames.clear();
		List<Integer> selectedRows=getChoosedRows();
		for(Integer row :selectedRows){
			String portName=(String)phonesTable.getValueAt(row, phonesTable.getColumn(TABLE_HEAD_PORT_NAME).getModelIndex());
			selectedPortNames.add(portName);
		}

		return selectedPortNames;
	}
	

	
	
	
	//checkbox 渲染器
	class JCheckBoxTableRender extends JCheckBox implements TableCellRenderer{

		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			if(value instanceof Boolean){
				this.setSelected((boolean)value);
			}
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
	
	
	
	@Override
	public void afterDisplay() {
		// TODO Auto-generated method stub
		initPhoneTableData();
	}
	





	//鼠标事件,更新选择的行对应端口的信号,短信中心等...

	class tableMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			int clickRow=phonesTable.convertRowIndexToModel(phonesTable.getSelectedRow());
			LogUtil.info("click "+clickRow);
			if(e.getClickCount()>=1){
				String portName=(String)phonesTable.getValueAt(clickRow, 1);
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
	
	//鼠标事件,选择checkbox 行头,则全部选择
	class checkboxAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

			if(e.getClickCount()>=1){
				if(phonesTable.getTableHeader().columnAtPoint(e.getPoint())==0 ){
					JCheckBox checkbox=	(JCheckBox)phonesTable.getColumnModel().getColumn(0).getHeaderRenderer();
					boolean isSelected=!checkbox.isSelected();
					checkbox.setSelected(isSelected);
//					phonesTable.getTableHeader().repaint();
					for(int i=0;i<phonesTable.getRowCount();i++){
						phonesTable.setValueAt(isSelected, i, 0);
					}
				}
			}
		}
		
	}
	
	
	
}

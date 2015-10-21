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
import java.util.List;
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


	
	
	
	//保存已经被选的端口名
	private  Set<String> selectedPortNames = new ConcurrentSkipListSet<>(); //支持并发,排序的set
	
	
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
				updatePhonesTable();
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

		phonesTable.getColumn(TABLE_HEAD_CHECKBOX_NAME).setCellRenderer(new JCheckBoxTableRender());
		phonesTable.getColumn(TABLE_HEAD_CHECKBOX_NAME).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		updatePhonesTable();
		
	}
	
	
	
	private  Set<PhonePort> getPhones(){
		String[] portNames = JsscPortList.getPortNames();	
		
		Set<PhonePort> phonePorts=new TreeSet<>();
		
		for(String portName:portNames){
			PhonePort phonePort =new PhonePort(portName);
			phonePort.setStatus(getPortInitStatus(portName));
			phonePorts.add(phonePort);
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
	
	
	
	private void updatePhonesTable(){
		phonesTable.setData(new ArrayList<>(getPhones()));
		
	}
	
	
	public void  updatePortStatus(String portName,String status,PhonePort port){
		
		//phonesTable.
		
		phonesTable.setValueAt(status, row, column);
		phonesTable.updateUI();
	}
	
	
	
	
/*	
	
	
	
	
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

		String[] title={TABLE_HEAD_CHECKBOX_NAME,TABLE_HEAD_PORT_NAME,TABLE_HEAD_STATUS_NAME};
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
	
*/
	
	//获取被table 中被勾选的行
	public List<Integer>  getChoosedRows(){

		List<Integer> selectedRows= new ArrayList<Integer>();
		for(int i=0;i<phoneTable.getRowCount();i++){
			if((boolean)phoneTable.getValueAt(i, 0)){
				selectedRows.add(i);
			}
		}
		
		return selectedRows;
	}
	
	
	/**
	 * 在发短信的时候要禁止移除选中的端口,否则移除的端口依然会发短信
	 */
	public   Set<String> getSelectedPortNames() {
		selectedPortNames.clear();
		List<Integer> selectedRows=getChoosedRows();
		for(Integer row :selectedRows){
			String portName=(String)phoneTable.getValueAt(row,1 );
			selectedPortNames.add(portName);
		}

		return selectedPortNames;
	}
	
	
	//更新状态列
	public void updateStatus(List<Integer> rows) throws Exception{

		for(int row :rows){
			String portName=(String)phoneTable.getValueAt(row,1 );
			String portStatus=PortServiceImpl.getInstance(JsscPort.getInstance(portName)).getPortStatus();
			phoneTable.setValueAt(portStatus, row, 2);
		}
	}
	
	
	//通过行的index来更新status
	
	
	//通过端口的名字来获取行的index
	
	
	
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
			int clickRow=phonesTable.convertRowIndexToModel(phonesTable.getSelectedRow());
			LogUtil.info("click "+clickRow);
			if(e.getClickCount()==1){
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
	
}

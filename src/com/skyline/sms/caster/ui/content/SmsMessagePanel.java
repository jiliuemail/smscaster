package com.skyline.sms.caster.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.skyline.sms.caster.pojo.TMessage;
import com.skyline.sms.caster.pojo.TUser;
import com.skyline.sms.caster.service.MessageService;
import com.skyline.sms.caster.service.UserService;
import com.skyline.sms.caster.service.impl.MessageServiceImpl;
import com.skyline.sms.caster.service.impl.UserServiceImpl;
import com.skyline.sms.caster.ui.UIConstants;
import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.InputField;
import com.skyline.sms.caster.ui.component.InputPanel;
import com.skyline.sms.caster.ui.component.InputTextField;
import com.skyline.sms.caster.util.LogUtil;

public class SmsMessagePanel extends ContentPanel{
	
	private JPanel contentPanel;
	private InputPanel inputPanel;
	private InputTextField toNubmerField;
	private InputTextField toGroup;
	private InputTextField subject;
	
	private JPanel messagePanel;
	private JTextArea messageArea;
	private MessageService msgService=new MessageServiceImpl();;
	private UserService userService= new UserServiceImpl();
	
	
	public SmsMessagePanel(String title){
		super(title);
		initToolButton();
		init();
	}
	
	public void initToolButton(){
		JButton toOutBox = new JButton("sendToOutbox");
		addToolButton(toOutBox);
		toOutBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					msgService.saveOrUpdate(getMessage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					LogUtil.error(e1);
				}
			}
		});
		
	}
	
	public void init(){

		initInput();
		initMessageArea();
		contentPanel=new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(inputPanel,BorderLayout.NORTH);
		contentPanel.add(messagePanel, BorderLayout.CENTER);
		setContent(contentPanel);
	}
	
	private void initInput(){
		inputPanel = new InputPanel();
		//inputPanel.setLayout(new BoxLayout(inputPanel, 3));
		toNubmerField = new InputTextField("sms.caster.label.input.tonumber", 60);
		inputPanel.addInputField(toNubmerField);
		toGroup = new InputTextField("sms.caster.label.input.togroup", 60);
		inputPanel.addInputField(toGroup);
		subject = new InputTextField("sms.caster.label.input.subject", 80);
		inputPanel.addInputField(subject);
		inputPanel.setPreferredSize(new Dimension(300,toNubmerField.getHeight()*4 ));
//		inputPanel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
	}

	public void initMessageArea(){
		messagePanel=new JPanel();
		messageArea=new JTextArea("input message here");
		messageArea.setLineWrap(true);
		JScrollPane scrollMessage= new JScrollPane(messageArea);
		scrollMessage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollMessage.setPreferredSize(new Dimension(UIConstants.MAX_FRAME_WIDTH-UIConstants.COMPONENT_WIDTH_UNIT-200,400));
		messagePanel.add(scrollMessage);

	}
	
	//将toNumber 发的短信转换成短信对象
	public List<TMessage> getMessage(){
		String phoneNumbers=toNubmerField.getInputField().getText();
		String[] numbers=phoneNumbers.split(";|,");
		String message=messageArea.getText();
		String subjectContent=subject.getInputField().getText();
		List<TMessage> messages = new ArrayList<>();
		for(String number:numbers){
			
			TUser user =getUserByNumber(number);
			
			TMessage sms = new TMessage(0, message,number);
			sms.setContactName(number);
			sms.setSubject(subjectContent);
			sms.setContactId(user.getId());
			sms.setContactType(0); //和user 中的类型不一样.....
			
			messages.add(sms);

		}

		return messages;
	}
	
	
	public TUser getUserByNumber(String number){
		TUser user =userService.findUserByNumber(number);
		if(user==null){
			user = new TUser();
			user.setNumber(number);
			user.setUserName(number);
			userService.add(user);
		}
		user =userService.findUserByNumber(number); //重新获取user 的id
		return user;
		
	}
	
	
	@Override
	public void afterDisplay() {
		
	}
}

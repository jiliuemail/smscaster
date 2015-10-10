package com.skyline.sms.caster.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.content.ContactsPanel;
import com.skyline.sms.caster.ui.content.PhonesPanel;
import com.skyline.sms.caster.ui.content.SmsMessagePanel;
import com.skyline.sms.caster.ui.toolbar.MainToolBar;

public class MainPanel extends JPanel {
	
	private Map<String, String> contentMap = new HashMap<>();
	
	private JToolBar toolBar;
	private JSplitPane workPanel;
	private JPanel explorer;
	private JPanel contentPanel;
	
	private ImageButton composeButton;
	private ImageButton contactsButton;
	private ImageButton groupsButton;
	private ImageButton inboxButton;
	private ImageButton outboxButton;
	private ImageButton phoneButton;
	private ImageButton responderButton;
	private ImageButton schedulerButton;
	private ImageButton sentButton;
	
	private ContentPanel composePanel;
	private ContentPanel contactsPanel;
	private ContentPanel phonesPanel;
	
	private Rectangle contentBound;
	private CardLayout cardLayout;
	
	public MainPanel(){
		setLayout(new BorderLayout());
		initToolbar();
		initWorkPanel();
		initExplorerPanel();
		initContentPanel();
		
		initComposePanel();
		initContactsPanel();
		initPhonesPanel();
	}
	
	private void initToolbar(){
		toolBar = new MainToolBar();
		add(toolBar, BorderLayout.NORTH);
	}
	
	private void initWorkPanel(){
		workPanel = new JSplitPane();
		workPanel.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				workPanel.setDividerLocation(1.0 / 6.0);  
			}
		});
		add(workPanel,BorderLayout.CENTER);
	}
	
	private void initExplorerPanel(){
		explorer = new JPanel();
		explorer.setLayout(new GridLayout(20, 1));
		
		composeButton = new ImageButton("sms.caster.label.button.compose");
		composeButton.setImagePath("resource/image/componse.png");
		
		contactsButton = new ImageButton("sms.caster.label.button.contacs");
		contactsButton.setImagePath("resource/image/contacts.png");
		
		groupsButton = new ImageButton("sms.caster.label.button.groups");
		groupsButton.setImagePath("resource/image/groups.png");
		
		inboxButton = new ImageButton("sms.caster.label.button.inbox");
		inboxButton.setImagePath("resource/image/inbox.png");
		
		outboxButton = new ImageButton("sms.caster.label.button.outbox");
		outboxButton.setImagePath("resource/image/outbox.png");
		
		phoneButton = new ImageButton("sms.caster.label.button.phone");
		phoneButton.setImagePath("resource/image/phone.png");
		
		responderButton = new ImageButton("sms.caster.label.button.responder");
		responderButton.setImagePath("resource/image/responder.png");
		
		schedulerButton = new ImageButton("sms.caster.label.button.scheduler");
		schedulerButton.setImagePath("resource/image/scheduler.png");
		
		sentButton = new ImageButton("sms.caster.label.button.sent");
		sentButton.setImagePath("resource/image/sent.png");
		
		explorer.add(composeButton);
		explorer.add(contactsButton);
		explorer.add(groupsButton);
		explorer.add(inboxButton);
		explorer.add(outboxButton);
		explorer.add(phoneButton);
		explorer.add(responderButton);
		explorer.add(schedulerButton);
		explorer.add(sentButton);
		
		workPanel.add(explorer, JSplitPane.LEFT);
	}
	
	private void initContentPanel(){
		contentPanel = new JPanel();
		contentBound = new Rectangle(UIConstants.WIDTH_UNIT, toolBar.getHeight()
				, UIConstants.SCREEN_WIDTH - UIConstants.WIDTH_UNIT
				, UIConstants.SCREEN_HEIGHT - toolBar.getHeight());
		
		contentPanel.setBounds(contentBound);
		cardLayout = new CardLayout();
		contentPanel.setLayout(cardLayout);
		
		workPanel.add(contentPanel, JSplitPane.RIGHT);
	}
	
	
	private void registryContentPanel(final String panelKey, final JButton button, final ContentPanel panel){
		String panelName = panel.getName();
		contentPanel.add(panelName,panel);
		contentMap.put(panelKey, panelName);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.beforeDisplay();
				cardLayout.show(contentPanel, contentMap.get(panelKey));
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						panel.afterDisplay();
					}
				});
			}
		});
	}
	
	private void initComposePanel(){
		composePanel = new SmsMessagePanel("sms.caster.label.panel.compose");
		composePanel.setBounds(contentBound);
		registryContentPanel(UIConstants.COMPOSE_PANEL_KEY, composeButton,composePanel);
	}

	private void initContactsPanel(){
		contactsPanel = new ContactsPanel("sms.caster.label.panel.contacts");
		contactsPanel.setBounds(contentBound);
		registryContentPanel(UIConstants.CONTACTS_PANEL_KEY, contactsButton,contactsPanel);
	}
	
	private void initPhonesPanel(){
		phonesPanel=new PhonesPanel("sms.caster.label.panle.phones");
		phonesPanel.setBounds(contentBound);
		registryContentPanel(UIConstants.PHONES_PANEL_KEY, phoneButton,phonesPanel);
	}
}

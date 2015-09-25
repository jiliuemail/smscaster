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

import com.skyline.sms.caster.ui.component.ContentPanel;
import com.skyline.sms.caster.ui.component.ImageButton;
import com.skyline.sms.caster.ui.content.ContactsPanel;
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
		outboxButton = new ImageButton("sms.caster.label.button.outbox");
		phoneButton = new ImageButton("sms.caster.label.button.phone");
		responderButton = new ImageButton("sms.caster.label.button.responder");
		schedulerButton = new ImageButton("sms.caster.label.button.scheduler");
		sentButton = new ImageButton("sms.caster.label.button.sent");
		
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
	
	
	private void registryContentPanel(final String panelKey, final JButton button, final JPanel panel){
		String panelName = panel.getName();
		contentPanel.add(panelName,panel);
		contentMap.put(panelKey, panelName);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, contentMap.get(panelKey));
			}
		});
	}
	
	private void initComposePanel(){
		composePanel = new ContentPanel("sms.caster.label.panel.compose");
		composePanel.setBounds(contentBound);
		composePanel.addToolButton(new JButton("test"));
		composePanel.setContent(new SmsMessagePanel());
		registryContentPanel(UIConstants.COMPOSE_PANEL_KEY, composeButton,composePanel);
	}

	private void initContactsPanel(){
		contactsPanel = new ContentPanel("sms.caster.label.panel.contacts");
		contactsPanel.setBounds(contentBound);
		contactsPanel.addToolButton(new JButton("Contact"));
		contactsPanel.setContent(new ContactsPanel(null));
		registryContentPanel(UIConstants.CONTACTS_PANEL_KEY, contactsButton,contactsPanel);
	}
	
}

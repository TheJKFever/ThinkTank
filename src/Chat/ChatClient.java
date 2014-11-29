package Chat;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Client.ConnectionToGameServer;
import Game.Event;
import Global.Settings;

public class ChatClient extends JPanel {
	
	JPanel chatPanel,topPanel, centerPanel, bottomPanel, inputPanel, loginPanel;
	JPanel hostNamePanel, playerNamePanel;
	
	JTextField hostNameTf;
	JButton confirmBtn;
	JLabel hostNameLbl, playerNameLbl;
	JTextField playerNameTf;
	
	JScrollPane scroll;
	JTextField tf;
	JLabel chatLabel;
	JTextArea ta;
	JButton sendBtn;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	String name;
	
	public void configureGUI() {

		setLayout(new CardLayout());
		
		
//		/***** Login Panel *******/
//		loginPanel = new JPanel();
//		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
//		confirmBtn = new JButton("Confirm");
//		hostNameTf = new JTextField(10);
//		hostNameLbl = new JLabel("Host name");
//		
//		
//		hostNamePanel = new JPanel();
//		hostNameLbl = new JLabel("Host Name: ");
//		hostNameTf= new JTextField(10);
//		hostNamePanel.add(hostNameLbl);
//		hostNamePanel.add(hostNameTf);
//		
//		playerNamePanel = new JPanel();
//		playerNameLbl = new JLabel("Player name: ");
//		playerNamePanel.add(playerNameLbl);
//		playerNameTf = new JTextField(10);
//		playerNamePanel.add(playerNameTf);
//		
//		loginPanel.add(hostNamePanel);
//		loginPanel.add(playerNamePanel);
//
//		loginPanel.add(confirmBtn);
//		generalPanel.add(loginPanel, "login");
//		confirmBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				((CardLayout)generalPanel.getLayout()).show(generalPanel, "chat");
//				name = playerNameTf.getText();
//				setTitle(name);
//				String ip = hostNameTf.getText();
//			}
//		});
		
		
		
		/****** Chat Panel*******/
		chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		
		// top part
		topPanel = new JPanel();
		chatLabel = new JLabel("Chat");
		topPanel.add(chatLabel);
		chatPanel.add(topPanel, BorderLayout.NORTH);
		
		// center part
		ta = new JTextArea();
		ta.setBorder(new EmptyBorder(20,20,20,20));
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		scroll = new JScrollPane(ta);
		
		centerPanel = new JPanel();
		centerPanel.add(scroll);
		chatPanel.add(centerPanel);
	
		chatPanel.add(scroll);
		
		// bottom part
		inputPanel = new JPanel(new BorderLayout());
		tf = new JTextField(20);
		inputPanel.add(tf);
		sendBtn = new JButton("Send");
		inputPanel.add(sendBtn, BorderLayout.EAST);
		chatPanel.add(inputPanel, BorderLayout.SOUTH);
		add(chatPanel,"chat");
	}
	
	public void receivedMessage(String message) {
		if (message.substring(0,1).equalsIgnoreCase("i")) { // TODO: <-- what's this 
			String[] split = message.split(":");
			this.name +=split[1];
			this.repaint();
		}
		else {
			ta.append(message);
		}
	}
	
	public ChatClient(ConnectionToGameServer gameConnection) {
//		this.name = gameConnection.gameScreen.engine.player.username;
		// TODO: uncomment above, once username is implemented
		this.name = "IMPLEMENT USERNAME";
		configureGUI();
		sendBtn.addActionListener(new SendBtnListener(gameConnection));
	}
	
	public class SendBtnListener implements ActionListener {
		private ConnectionToGameServer gameConnection;

		public SendBtnListener(ConnectionToGameServer gameConnection) {
			this.gameConnection = gameConnection;
		}

		public void actionPerformed(ActionEvent e) {
			String message = "["+ChatClient.this.name+"]"+"\t"+tf.getText();
			tf.setText("");
			ta.append(message);
			gameConnection.sendEvent(new Event("chat", message));
		}
	}		
}

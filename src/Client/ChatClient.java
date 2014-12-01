package Client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Game.Event;

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
	
	String name;
	
	public void configureGUI() {

		setLayout(new CardLayout());
		
		
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
		tf = new JTextField(12);
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
			ta.append("\n"+message);
		}
	}
	
	public ChatClient() {
//		this.name = gameConnection.gameScreen.engine.player.username;
		// TODO: uncomment above, once username is implemented
		this.name = "IMPLEMENT USERNAME";
		configureGUI();
		sendBtn.setEnabled(false);
	}
	
	public void setConnection(ConnectionToGameServer gameConnection) {
		sendBtn.addActionListener(new SendBtnListener(gameConnection));
		sendBtn.setEnabled(true);
	}
	
	public class SendBtnListener implements ActionListener {
		private ConnectionToGameServer gameConnection;

		public SendBtnListener(ConnectionToGameServer gameConnection) {
			this.gameConnection = gameConnection;
		}

		public void actionPerformed(ActionEvent e) {
			String message = "["+ChatClient.this.name+"]"+"\t"+tf.getText();
			tf.setText("");
//			ta.append("\n"+message);
			// go to connectionToServer
			gameConnection.sendEvent(new Event("chat", message));
		}
	}		
}

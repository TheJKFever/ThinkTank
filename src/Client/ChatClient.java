package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Game.Event;
import Screens.GameScreen;

public class ChatClient extends JPanel {
	
	JPanel topPanel, centerPanel, inputPanel, buttonPanel;
	JPanel hostNamePanel, playerNamePanel;
	
	JTextField hostNameTf;
	JButton confirmBtn;
	JLabel hostNameLbl, playerNameLbl;
	JTextField playerNameTf;
	JScrollPane scroll;
	JTextField tf;
	JLabel chatLabel;
	JTextArea ta;
	JButton sendAllBtn, sendTeamBtn;
	private GameScreen gameScreen;
		
	public ChatClient(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		/****** Chat Panel*******/
		setLayout(new BorderLayout(0, 0));
		
		// top part
		topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		chatLabel = new JLabel("Chat");
		topPanel.add(chatLabel);
		
		// center part
		ta = new JTextArea();
		ta.setBorder(new EmptyBorder(5,3,3,5));
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		ta.setMaximumSize(new Dimension(300, 500));
		scroll = new JScrollPane(ta);
		
		centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		centerPanel.add(scroll);
		
		// bottom part
		buttonPanel = new JPanel();
		inputPanel = new JPanel();
		add(inputPanel, BorderLayout.SOUTH);
		inputPanel.setLayout(new BorderLayout(0, 0));
		tf = new JTextField(12);
		inputPanel.add(tf, BorderLayout.NORTH);
		sendAllBtn = new JButton("Send All");
		sendTeamBtn = new JButton("Send Team");
		buttonPanel.add(sendAllBtn);
		buttonPanel.add(sendTeamBtn);
		inputPanel.add(buttonPanel);
		sendAllBtn.setEnabled(false);
		sendTeamBtn.setEnabled(false);
	
	}

	public void setConnection(ConnectionToGameServer gameConnection) {
		sendAllBtn.addActionListener(new SendBtnListener(gameConnection));
		sendAllBtn.setEnabled(true);
	}
	
	public class SendBtnListener implements ActionListener {
		private ConnectionToGameServer gameConnection;

		public SendBtnListener(ConnectionToGameServer gameConnection) {
			this.gameConnection = gameConnection;
		}

		public void actionPerformed(ActionEvent e) {
			String message = "["+ChatClient.this.gameScreen.gui.user.username +"] "+tf.getText();
			tf.setText("");
			gameConnection.sendEvent(new Event("chat", message));
		}
	}
}

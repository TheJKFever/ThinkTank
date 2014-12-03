package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Chat.ChatObject;
import Game.Event;
import Game.Player;
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
	JButton sendAllBtn, sendTeamBtn, sendPlayerBtn;
	DefaultListModel<String> listModel;
	private GameScreen gameScreen;
	public JList<String> list;
		
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
		ta.setBorder(new EmptyBorder(0,3,3,5));
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		ta.setMaximumSize(new Dimension(300, 500));
		scroll = new JScrollPane(ta);
		
		centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ChatPlayersSelectionHandler(this));
		
		centerPanel.add(list, BorderLayout.NORTH);
		centerPanel.add(scroll, BorderLayout.CENTER);
		
		// bottom part
		buttonPanel = new JPanel();
		inputPanel = new JPanel();
		add(inputPanel, BorderLayout.SOUTH);
		inputPanel.setLayout(new BorderLayout(0, 0));
		tf = new JTextField(12);
		inputPanel.add(tf, BorderLayout.NORTH);
		sendAllBtn = new JButton("All");
		sendAllBtn.setMargin(new Insets(2, 4, 2, 4));
		sendTeamBtn = new JButton("Team");
		sendTeamBtn.setMargin(new Insets(2, 4, 2, 4));
		sendPlayerBtn = new JButton("Player");
		sendPlayerBtn.setMargin(new Insets(2, 4, 2, 4));
		buttonPanel.add(sendAllBtn);
		buttonPanel.add(sendTeamBtn);
		buttonPanel.add(sendPlayerBtn);
		inputPanel.add(buttonPanel);
		sendAllBtn.setEnabled(false);
		sendTeamBtn.setEnabled(false);
		sendPlayerBtn.setEnabled(false);
	}
	
	public void addPlayer(String playerName) {
		
		listModel.addElement(playerName);
	}
	
	public void removePlayer(Player player) {
		listModel.remove(listModel.indexOf(player.username));
	}

	public void setConnection(ConnectionToGameServer gameConnection) {
		sendAllBtn.addActionListener(new SendBtnListener(gameConnection));
		sendAllBtn.setEnabled(true);
		sendTeamBtn.addActionListener(new SendTeamBtnListener(gameConnection));
		sendTeamBtn.setEnabled(true);
		sendPlayerBtn.addActionListener(new SendPlayerBtnListener(this, gameConnection));
	}
	
	public class SendBtnListener implements ActionListener {
		private ConnectionToGameServer gameConnection;

		public SendBtnListener(ConnectionToGameServer gameConnection) {
			this.gameConnection = gameConnection;
		}

		public void actionPerformed(ActionEvent e) {
			String message = "["+ChatClient.this.gameScreen.gui.user.username +"] "+tf.getText();
			tf.setText("");
			gameConnection.sendEvent(new Event("chat", new ChatObject("all", gameScreen.engine.player, message)));
		}
	}
	public class SendTeamBtnListener implements ActionListener {
		private ConnectionToGameServer gameConnection;
		public SendTeamBtnListener(ConnectionToGameServer gameConnection) {
			this.gameConnection = gameConnection;
		}

		public void actionPerformed(ActionEvent e) {
			String message = "["+ChatClient.this.gameScreen.gui.user.username +"] "+tf.getText();
			tf.setText("");
			gameConnection.sendEvent(new Event("chat", new ChatObject("team", gameScreen.engine.player, message)));
		}
	}
	public class SendPlayerBtnListener implements ActionListener {
		private ChatClient chatWindow;
		private ConnectionToGameServer gameConnection;
		public SendPlayerBtnListener(ChatClient chatWindow, ConnectionToGameServer gameConnection) {
			this.chatWindow = chatWindow;
			this.gameConnection = gameConnection;
		}

		public void actionPerformed(ActionEvent e) {
			String message = "["+ChatClient.this.gameScreen.gui.user.username +"] "+tf.getText();
			tf.setText("");
			gameConnection.sendEvent(new Event("chat", new ChatObject(chatWindow.list.getSelectedValue(), gameScreen.engine.player, message), gameScreen.engine.player));
		}
	}
	class ChatPlayersSelectionHandler implements ListSelectionListener {
	    private ChatClient chatWindow;
		public ChatPlayersSelectionHandler(ChatClient chatWindow) {
	    	this.chatWindow = chatWindow;
	    }
		public void valueChanged(ListSelectionEvent e) {
	        if (list.getSelectedIndex() == -1) {
	        	chatWindow.sendPlayerBtn.setEnabled(false);
	        } else {
	        	chatWindow.sendPlayerBtn.setEnabled(true);
	        }
	    }
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("fdsafdsfdsadfs");
		frame.setVisible(true);
		frame.setSize(150, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ChatClient(null));
	}
}

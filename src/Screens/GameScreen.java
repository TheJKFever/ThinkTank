package Screens;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import Chat.ChatClient;
import Client.ConnectionToGameServer;
import Client.ThinkTankGUI;
import Game.ClientEngine;
import Game.GamePanel;

public class GameScreen extends JPanel {
	public JPanel chatPanel, utilityBar, sidePanel;

	public ThinkTankGUI gui;
	public ChatClient chat; //	TODO: <-- fix this
	public ClientEngine engine;
	public ConnectionToGameServer gameConnection;
	public GamePanel gamePanel;
	
	public GameScreen(ThinkTankGUI gui) {
		this.gui = gui;
		setLayout(new BorderLayout());
		
		gamePanel = new GamePanel();
		chatPanel = new JPanel();
		sidePanel = new JPanel();
		sidePanel.add(chatPanel);
		add(gamePanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);
	}
	
	public boolean connectToGameServer(String host, int port) {
		engine = new ClientEngine(this);
		try {
			gameConnection = new ConnectionToGameServer(this, host, port);
			return true;
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			return false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		
	}
}

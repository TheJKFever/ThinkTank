package Screens;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import Chat.ChatClient;
import Client.ConnectionToGameServer;
import Client.ThinkTankGUI;
import Game.ClientEngine;

public class GameScreen extends JPanel {
	public JPanel chatPanel, gamePanel, utilityBar;

	public ThinkTankGUI gui;
	public ChatClient chat; daf;dkjsalk;jfjkfsdajlk <-- fix this
	public ClientEngine engine;
	public ConnectionToGameServer gameConnection;
	
	public GameScreen(ThinkTankGUI gui) {
		this.gui = gui;
		gamePanel = new JPanel();
		// do gamePanel here
		
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

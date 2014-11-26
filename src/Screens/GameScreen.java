package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import Chat.ChatClient;
import Client.ConnectionToGameServer;
import Client.ThinkTankGUI;
import Game.ClientEngine;
import Game.GamePanel;

public class GameScreen extends JPanel {
	
	private static final long serialVersionUID = -1188091035895129077L;

	public JPanel chatPanel, utilityBar, sidePanel;

	public ThinkTankGUI gui;
	public ChatClient chat; //	TODO: <-- fix this
	public ClientEngine engine;
	public ConnectionToGameServer gameConnection;
	public GamePanel gamePanel;
	
	public GameScreen(ThinkTankGUI gui) {
		super();
		System.out.println("IN GAME SCREEN CONSTRUCTOR");
		
		this.gui = gui;
		setLayout(new BorderLayout());
		gamePanel = new GamePanel();
		chatPanel = new JPanel();
		sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(200, 600));
		sidePanel.add(chatPanel);
		add(gamePanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);
	}
	
	public boolean connectToGameServer(String host, int port) {
		System.out.println("GAMESCREEN: connectToGameServer");
		engine = new ClientEngine(this);
		System.out.println("GAMESCREEN: created client engine");
		try {
			gameConnection = new ConnectionToGameServer(this, host, port);
			System.out.println("GAMESCREEN: got connection to game server");
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

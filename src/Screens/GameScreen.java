package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import Chat.ChatClient;
import Client.ConnectionToGameServer;
import Client.ThinkTankGUI;
import Engines.ClientEngine;
import Global.Settings;

public class GameScreen extends JPanel {
	
	private static final long serialVersionUID = -1188091035895129077L;

	ChatClient chatPanel;

	public JPanel utilityBar, sidePanel;

	public ThinkTankGUI gui;
	public ChatClient chat; //	TODO: <-- fix this
	public ClientEngine engine;
	public ConnectionToGameServer gameConnection;
	public GamePanel gamePanel;
	
	public GameScreen(ThinkTankGUI gui) {
		super();
		this.gui = gui;
//		setLayout(new BorderLayout());
		gamePanel = new GamePanel(this);
		gamePanel.setPreferredSize(new Dimension(Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT));
		chatPanel = new ChatClient(gameConnection);
		chatPanel.setPreferredSize(new Dimension(140, Settings.BOARD_HEIGHT));
		sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(150, Settings.BOARD_HEIGHT));
		sidePanel.add(chatPanel);
		add(gamePanel);
		add(sidePanel);
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

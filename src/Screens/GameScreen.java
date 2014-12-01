package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import Client.ChatClient;
import Client.ConnectionToGameServer;
import Client.ThinkTankGUI;
import Engines.ClientEngine;
import Game.Event;
import Game.Helper;
import Game.SimpleKeyEvent;
import Global.Settings;

public class GameScreen extends JPanel {
	
	private static final long serialVersionUID = -1188091035895129077L;

	public ChatClient chatPanel;

	public JPanel  sidePanel;
	public UtilityBar bar;
	public ThinkTankGUI gui;
	public ChatClient chat; //	TODO: <-- fix this
	public ClientEngine engine;
	public ConnectionToGameServer gameConnection;
	public GamePanel gamePanel;
	
	public GameScreen(ThinkTankGUI gui) {
		super();
		Helper.log("Creating new GameScreen");
		this.gui = gui;
		bar = new UtilityBar(this);
		gamePanel = new GamePanel(this);
		gamePanel.setPreferredSize(new Dimension(Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT));
		chatPanel = new ChatClient();
		chatPanel.setPreferredSize(new Dimension(140, Settings.BOARD_HEIGHT));
		sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(150, Settings.BOARD_HEIGHT));
		sidePanel.add(chatPanel);
				
		add(gamePanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);
		
		add(bar, BorderLayout.SOUTH);
		
		Helper.log("Created new GameScreen");
	}
	
	public boolean connectToGameServer(String host, int port) {
		Helper.log("GAMESCREEN: connectToGameServer");
		engine = new ClientEngine(this);
		Helper.log("GAMESCREEN: created client engine");
		try {
			gameConnection = new ConnectionToGameServer(this, host, port);
			chatPanel.setConnection(gameConnection);
			Helper.log("GAMESCREEN: got connection to game server");
			return true;
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			return false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}
	
	public void sendKeyPressToClientAndServer(SimpleKeyEvent ke) {
		Event event = new Event("key event", ke);
		synchronized(this.engine.eventQ) {
			try {
				this.engine.eventQ.put(event);
			} catch (InterruptedException ie) {
				System.out.println("USER INPUT HANDLER: INTERRUPTED");
				ie.printStackTrace();
			}
		}
		this.gameConnection.sendEvent(event);
	}
}

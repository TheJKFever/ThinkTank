package Engines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;

import Client.ConnectionToGameServer;
import Game.Event;
import Game.GameState;
import Game.Globals;
import Game.Player;
import Screens.GamePanel;
import Screens.GameScreen;

public class ClientEngine implements Runnable {
	
	public GameState gameState;
	ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	
	GamePanel gamePanel;
	ConnectionToGameServer gameConnection;
	public Player player;
	
	private Thread engineThread;
	private GameScreen gameScreen;

	public ClientEngine(GameScreen gameScreen) {
		System.out.println("ClientEngine: IN CONSTRUCTOR");
		this.gameScreen = gameScreen;
		this.gamePanel = gameScreen.gamePanel;
		this.gameState = null;
		engineThread = new Thread(this);
	}

	public void startGame() {
		engineThread.start();
	}

	public void getGameStateFromServer() {
		log("CLIENT ENGINE: GETTING GAME STATE FROM SERVER");
		gameState = gameScreen.gameConnection.getGameStateFromServer();
		while (gameState == null) {
			log("GAME STATE == NULL, TRYING AGAIN");
			gameState = gameScreen.gameConnection.getGameStateFromServer();
		}
		log(gameState.toString());
	}
	
	public void run() {
		log("CLIENT ENGINE: THREAD STARTED");
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		
		getGameStateFromServer();
		log("CLIENT ENGINE: About to paint gamePanel for the first time");
		gamePanel.repaint();
		log("CLIENT ENGINE: ADDING KEY LISTENER");
		gamePanel.addKeyListener(new GameInputHandler());

		while (gameState.inGame) {
			getGameStateFromServer();
			processUserInput();
			log("CLIENT ENGINE: ABOUT TO UPDATE GAME STATE");
			gameState.update();
			log("CLIENT ENGINE: ABOUT TO REPAINT GAMEPANEL");
			gamePanel.repaint();
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = Globals.DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				log("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
	}
	
	public void processUserInput() {
		log("CLIENT ENGINE: PROCESSING USER INPUT");
		synchronized(eventQ) {
			for (Event event: eventQ) {
				if (event.type == "key event") {
					KeyEvent ke = ((KeyEvent)event.data);
					if (ke.getID() == KeyEvent.KEY_RELEASED) {
						player.tank.keyReleased(ke);
					} else if (ke.getID() == KeyEvent.KEY_PRESSED) {
						player.tank.keyPressed(ke);
					}
				}
			}
			eventQ.clear();
		}
	}

	private class GameInputHandler extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			synchronized(eventQ) {
				try {
					Event event = new Event("key event", e);
					eventQ.put(event);
					gameConnection.sendEvent(event);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		
		public void keyPressed(KeyEvent e) {
			synchronized(eventQ) {
				try {
					Event event = new Event("key event", e);
					eventQ.put(new Event("key event", e));
					gameConnection.sendEvent(event);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}
	
	public void log(String msg) {
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}
}
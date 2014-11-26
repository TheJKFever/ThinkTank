package Engines;

import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

//import Client.ConnectionToGameServer;
import Client.UserInputHandler;
import Game.Event;
import Game.GameState;
import Game.Globals;
import Game.Player;
import Game.SimpleKeyEvent;
import Screens.GamePanel;
import Screens.GameScreen;

public class ClientEngine implements Runnable {
	
	
	public Player player;
	public GameState gameState;
//	public ConnectionToGameServer gameConnection;
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	
	private Thread engineThread;
	private GameScreen gameScreen;
	GamePanel gamePanel;
	
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
		
		log("CLIENT ENGINE: Adding UserInputHandler");
		gamePanel.addKeyListener(new UserInputHandler(this.gameScreen));

		while (true) {
//		while (gameState.inGame) {
			log("CLIENT ENGINE: entered main loop");
			
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
//		log("CLIENT ENGINE: GAMESTATE.INGAME == FALSE");
	}
	
	public void processUserInput() {
		log("CLIENT ENGINE: PROCESSING USER INPUT");
		synchronized(eventQ) {
			for (Event event: eventQ) {
				if (event.type == "key event") {
					SimpleKeyEvent ke = ((SimpleKeyEvent)event.data);
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
	
	public void log(String msg) {
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}
}
package Engines;

import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

//import Client.ConnectionToGameServer;
import Client.UserInputHandler;
import Game.Event;
import Game.GameState;
import Game.Helper;
import Game.Player;
import Game.SimpleKeyEvent;
import Global.Settings;
import Screens.GamePanel;
import Screens.GameScreen;

public class ClientEngine extends Engine {
	
	public Player player;
	public GameState gameState;
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	
	private Thread engineThread;
	private GameScreen gameScreen;
	GamePanel gamePanel;
	
	public ClientEngine(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.gamePanel = gameScreen.gamePanel;
		this.gameState = new GameState();
		engineThread = new Thread(this);
		Helper.log("Created new ClientEngine");
	}

	public void start() {
		engineThread.start();
	}

	public void getGameStateFromServer() {
		Helper.log("CLIENT ENGINE: GETTING GAME STATE FROM SERVER");
		
		GameState newGameState = gameScreen.gameConnection.getGameStateFromServer();
//		if (newGameState!=null) {
//			gameState = newGameState;
		while (newGameState == null) {
			Helper.log("CLIENT ENGINE: GAME STATE == NULL, TRYING AGAIN in a few");
			newGameState = gameScreen.gameConnection.getGameStateFromServer();
			try {
				Thread.sleep(Settings.DELAY/3);
			} catch (InterruptedException ie) {
				System.out.println("CLIENT ENGINE: INTERRUPTED WHILE WAITING FOR GAME STATE");
			}
		}
		
//		while (newGameState == null) {
//			Helper.log("CLIENT ENGINE: GAME STATE == NULL, TRYING AGAIN in a few");
//			newGameState = gameScreen.gameConnection.getGameStateFromServer();
//			try {
//				Thread.sleep(Globals.DELAY/5);
//			} catch (InterruptedException ie) {
//				Helper.log("CLIENT ENGINE: INTERRUPTED WHILE WAITING FOR GAME STATE");
//			}
//		}
//		gameState = newGameState;
		// Helper.log("CLIENT ENGINE: GOT GAME STATE");
		// Helper.log(gameState);
	}
	
	public void run() {
		Helper.log("CLIENT ENGINE: THREAD STARTED");
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		
//		getGameStateFromServer();
		
		Helper.log("CLIENT ENGINE: About to paint gamePanel for the first time");
		gamePanel.repaint();
		
		Helper.log("CLIENT ENGINE: Adding UserInputHandler");
		gamePanel.addKeyListener(new UserInputHandler(this.gameScreen));


		while (gameState.inGame) {
			getGameStateFromServer();
			processInput();
			this.player.tank = gameState.tankForThisClient;
			gameState.update();
			gamePanel.repaint();
			waitIfDoneEarly(beforeTime);
			beforeTime = System.currentTimeMillis();
			Helper.log("Thoughts: " + player.tank.thoughts);
			Helper.log("Health: " + player.tank.health + "/10");
			Helper.log("Tank.y: " + player.tank.y);
			Helper.log("Tank.x: " + player.tank.x);
		}
	}
	
	public void waitIfDoneEarly(long beforeTime) {
		long timeDiff, sleep;
		
		timeDiff = System.currentTimeMillis() - beforeTime;
		sleep = Settings.DELAY - timeDiff;

		if (sleep < 0) {
			sleep = 1;
		}
		
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			Helper.log("interrupted");
		}
	}
	
	public void processInput() {
		Helper.log("CLIENT ENGINE: PROCESSING USER INPUT");
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
}
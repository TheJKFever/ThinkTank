package Engines;

import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;


//import Client.ConnectionToGameServer;
import Client.UserInputHandler;
import Game.Event;
import Game.GameState;
import Game.Player;
import Game.SimpleKeyEvent;
import Global.Settings;
import Screens.GamePanel;
import Screens.GameScreen;

public class ClientEngine implements Runnable {
	
	
	public Player player;
	public GameState gameState;
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
//		log("CLIENT ENGINE: GETTING GAME STATE FROM SERVER");
		
		GameState newGameState = gameScreen.gameConnection.getGameStateFromServer();
		while (newGameState == null) {
//			log("CLIENT ENGINE: GAME STATE == NULL, TRYING AGAIN in a few");
			newGameState = gameScreen.gameConnection.getGameStateFromServer();
			try {
				Thread.sleep(Settings.DELAY/3);
			} catch (InterruptedException ie) {
				System.out.println("CLIENT ENGINE: INTERRUPTED WHILE WAITING FOR GAME STATE");
			}
		}
		gameState = newGameState;
		log("CLIENT ENGINE: GOT GAME STATE");
		log(gameState.toString());
	}
	
	public void run() {
		log("CLIENT ENGINE: THREAD STARTED");
		long beforeTime;
		beforeTime = System.currentTimeMillis();
		
		getGameStateFromServer();
		
//		log("CLIENT ENGINE: About to paint gamePanel for the first time");
		gamePanel.repaint();
		
//		log("CLIENT ENGINE: Adding UserInputHandler");
		gamePanel.addKeyListener(new UserInputHandler(this.gameScreen));

		while (gameState.inGame) {
			getGameStateFromServer();
			processUserInput();
			gameState.update();
			gamePanel.repaint();
			waitIfDoneEarly(beforeTime);
			beforeTime = System.currentTimeMillis();
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
			log("interrupted");
		}
	}
	
	public void processUserInput() {
//		log("CLIENT ENGINE: PROCESSING USER INPUT");
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
		if (Settings.DEBUG) {
			System.out.println(msg);
		}
	}
}
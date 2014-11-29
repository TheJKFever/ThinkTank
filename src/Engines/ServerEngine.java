package Engines;

import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Game.Event;
import Game.GameState;
import Game.SimpleKeyEvent;
import Global.Settings;
import Server.GameServerConnectionToClient;

public class ServerEngine implements Runnable {
	
	public GameState gameState;
	Vector<GameServerConnectionToClient> clients;
	
	// TODO: Ensure eventQ is proper data structure and large enough
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(1000);
	private Thread engineThread;

	public ServerEngine(Vector<GameServerConnectionToClient> clientConnections) {
		log("SERVERENGINE: CONSTRUCTOR");
		this.clients = clientConnections;
		this.gameState = new GameState();	
		engineThread = new Thread(this);
//		log("SERVER ENGINE: INITIAL GAME STATE");
//		log(gameState.toString());
	}
	
	
	public void run() {
		log("ServerEngine: run()");
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		
		gameState.inGame = true;
		broadcastGameState();
		startGame();
		
		while (gameState.inGame) {
			processInputFromClients();
			gameState.update();
			broadcastGameState();
			
			// TODO: Calibrate server time step, faster or slower?
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = Settings.DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				log("interrupted");
			}
			beforeTime = System.currentTimeMillis();
			// TODO: kill server if all clients leave
		}
	}
	
	public void broadcastGameState() {
		log("GAMESERVER: BROADCAST GAME STATE");
		log(gameState.toString());
		
		Event e;
		for (GameServerConnectionToClient client: clients) {
			gameState.tankForThisClient = client.player.tank;
			e = new Event("game update", gameState);
			client.sendEvent(e);
		}
	}

	private void startGame() {
		log("GAMESERVER: START GAME");
		gameState.startGameClock();
		for (GameServerConnectionToClient client:clients) {
			client.sendEvent(new Event("start game"));
		}
	}

	public void processInputFromClients() {
//		log("GAMESERVER: PROCESSINPUTFROMCLIENTS");
		synchronized(eventQ) {
			for (Event event: eventQ) {
//				log("GAMESERVER: PROCESSING EVENT:\n" + event);
				switch(event.type) {
					case "key event":
						SimpleKeyEvent ke = (SimpleKeyEvent)(event.data);
						if (ke.getID() == KeyEvent.KEY_RELEASED) {
							event.player.tank.keyReleased(ke);
						} else if (ke.getID() == KeyEvent.KEY_PRESSED) {
							event.player.tank.keyPressed(ke);
						}
					break;
					default:
						log("SERVER ENGINE: DID NOT RECOGNIZE EVENT:\n" + event);
					// handle others...
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
	
	public void start() {
		engineThread.start();
	}
}
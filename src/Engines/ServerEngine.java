package Engines;

import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Game.Event;
import Game.GameState;
import Game.Globals;
import Game.SimpleKeyEvent;
import Server.GameServerConnectionToClient;

public class ServerEngine implements Runnable {
	
	public GameState gameState;
	Vector<GameServerConnectionToClient> clients;
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(1000);	// TODO: could possibly not be adding over 100 events
	private Thread engineThread;

	public ServerEngine(Vector<GameServerConnectionToClient> clientConnections) {
		log("SERVERENGINE: CONSTRUCTOR");
		this.clients = clientConnections;
		this.gameState = new GameState();	
		engineThread = new Thread(this);
		log("SERVER ENGINE: INITIAL GAME STATE");
		log(gameState.toString());
	}
	
	
	public void run() {
		log("GSCONNECTIONTOCLIENT: RUN()");
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		
		gameState.inGame = true;
		broadcastGameState();
		startGame();
		
		while (gameState.inGame) {
			processInputFromClients();
			gameState.update();
			broadcastGameState();
			
			// TODO: ADD DELAY / TIMER HERE
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
			// TODO: kill server if all clients leave
		}
	}
	
	public void broadcastGameState() {
		log("GAMESERVER: BROADCAST GAME STATE");
		log(gameState.toString());
		Event e = new Event("game update", gameState);
		
		for (GameServerConnectionToClient client: clients) {
			client.sendEvent(e);
		}
	}

	private void startGame() {
		log("GAMESERVER: START GAME");
		for (GameServerConnectionToClient client:clients) {
			client.sendEvent(new Event("start game"));
		}
	}

	public void processInputFromClients() {
		log("GAMESERVER: PROCESSINPUTFROMCLIENTS");
		synchronized(eventQ) {
			for (Event event: eventQ) {
				log("GAMESERVER: PROCESSING EVENT:\n" + event);
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
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}
	
	public void start() {
		engineThread.start();
	}
}
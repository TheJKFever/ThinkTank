package Engines;

import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Game.Event;
import Game.GameState;
import Game.Globals;
import Server.GameServerConnectionToClient;

public class ServerEngine implements Runnable {
	
	public GameState gameState;
	Vector<GameServerConnectionToClient> clients;
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(1000);	// TODO: could possibly not be adding over 100 events
	private Thread engineThread;

	public ServerEngine(Vector<GameServerConnectionToClient> clientConnections) {
		System.out.println("SERVERENGINE: CONSTRUCTOR");
		this.clients = clientConnections;
		this.gameState = new GameState();	
		engineThread = new Thread(this);
		System.out.println("SERVER ENGINE: INITIAL GAME STATE");
		System.out.println(gameState);
	}
	
	
	public void run() {
		System.out.println("GSCONNECTIONTOCLIENT: RUN()");
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
			sleep = Globals.DELAY * 10 - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
	}
	
	public void broadcastGameState() {
		System.out.println("GAMESERVER: BROADCAST GAME STATE");
		System.out.println(gameState);
		for (GameServerConnectionToClient client: clients) {
			client.sendEvent(new Event("game update", gameState));
		}
	}

	private void startGame() {
		System.out.println("GAMESERVER: START GAME");
		for (GameServerConnectionToClient client:clients) {
			client.sendEvent(new Event("start game"));
		}
	}

	public void processInputFromClients() {
		System.out.println("GAMESERVER: PROCESSINPUTFROMCLIENTS");
		synchronized(eventQ) {
			for (Event event: eventQ) {
				switch(event.type) {
					case "key event":
						KeyEvent ke = ((KeyEvent)event.data);
						if (ke.getID() == KeyEvent.KEY_RELEASED) {
							event.player.tank.keyReleased(ke);
						} else if (ke.getID() == KeyEvent.KEY_PRESSED) {
							event.player.tank.keyPressed(ke);
						}
					break;
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
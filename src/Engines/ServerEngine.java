package Engines;

import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Game.Event;
import Game.GameState;
import Game.Globals;
import Game.Helper;
import Game.SimpleKeyEvent;
import Server.GameServerConnectionToClient;

public class ServerEngine extends Engine {
	
	public GameState gameState;
	Vector<GameServerConnectionToClient> clients;
	
	// TODO: Ensure eventQ is proper data structure and large enough
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(1000);
	private Thread engineThread;

	public ServerEngine(Vector<GameServerConnectionToClient> clientConnections) {
		this.clients = clientConnections;
		this.gameState = new GameState();	
		engineThread = new Thread(this);
		// Helper.log("SERVER ENGINE: INITIAL GAME STATE: ");
		// Helper.log(gameState);
		Helper.log("Created new ServerEngine");
	}
	
	public void start() {
		engineThread.start();
	}
	
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		
		gameState.inGame = true;
		broadcastGameState();
		startGame();
		
		while (gameState.inGame) {
			processInput();
			gameState.update();
			broadcastGameState();
			
			// TODO: Calibrate server time step, faster or slower?
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = Globals.DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				Helper.log("interrupted");
			}
			beforeTime = System.currentTimeMillis();
			// TODO: kill server if all clients leave
		}
	}
	
	public void broadcastGameState() {
		// Helper.log("GAMESERVER: BROADCAST GAME STATE");
		// Helper.log(gameState.toString());
		Event e = new Event("game update", gameState);
		
		for (GameServerConnectionToClient client: clients) {
			client.sendEvent(e);
		}
	}

	private void startGame() {
		Helper.log("GAMESERVER: START GAME");
		for (GameServerConnectionToClient client:clients) {
			client.sendEvent(new Event("start game"));
		}
	}

	public void processInput() {
		Helper.log("GAMESERVER: PROCESSINPUTFROMCLIENTS");
		synchronized(eventQ) {
			for (Event event: eventQ) {
				Helper.log("GAMESERVER: PROCESSING EVENT:\n" + event);
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
						Helper.log("SERVER ENGINE: DID NOT RECOGNIZE EVENT:\n" + event);
					// handle others...
				}
			}
			eventQ.clear();
		}
	}
}
package Game;

import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Server.GameServerConnectionToClient;

public class ServerEngine implements Runnable {
	
	public GameState gs;
	Vector<GameServerConnectionToClient> clients;
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(1000);	// TODO: could possibly not be adding over 100 events
	private Thread engineThread;

	public ServerEngine(Vector<GameServerConnectionToClient> clientConnections) {
		System.out.println("SERVERENGINE: CONSTRUCTOR");
		this.clients = clientConnections;
		this.gs = new GameState();
		
		engineThread = new Thread(this);
	}
	
	
	public void run() {
		System.out.println("GSCONNECTIONTOCLIENT: RUN()");
		gs.inGame = true;
		
		broadcastGameState();
		startGame();
		
		while (gs.inGame) {
			processInputFromClients();
			gs.update();
			broadcastGameState();
		}
	}
	
	private void startGame() {
		System.out.println("GAMESERVER: START GAME");
		for (GameServerConnectionToClient client:clients) {
			client.sendEvent(new Event("start game"));
		}
	}


	public void broadcastGameState() {
		System.out.println("GAMESERVER: BROADCAST GAME STATE");
		for (GameServerConnectionToClient client:clients) {
			client.sendEvent(new Event("game update", gs));
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
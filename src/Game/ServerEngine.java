package Game;

import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Server.ConnectionToClient;

public class ServerEngine implements Runnable {
	
	public GameState gs;
	Vector<ConnectionToClient> clients;
	public ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	// TODO: could possibly not be adding over 100 events
	private Thread engineThread;

	public ServerEngine(Vector<ConnectionToClient> clientConnections) {
		this.clients = clientConnections;
		this.gs = new GameState();
		
		engineThread = new Thread(this);
	}
	
	
	public void run() {
		gs.inGame = true;
		// TODO: implement
		// clients should be on the waiting page, send a signal to start game, maybe put this in engine start
		broadcastGameState();
		startGame();
		
		while (gs.inGame) {
			processInputFromClients();
			gs.update();
			broadcastGameState();
		}
	}
	
	private void startGame() {
		for (ConnectionToClient client:clients) {
			client.sendEvent(new Event("start game"));
		}
	}


	public void broadcastGameState() {
		for (ConnectionToClient client:clients) {
			client.sendEvent(new Event("game update", gs));
		}
	}
	
	public void processInputFromClients() {
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
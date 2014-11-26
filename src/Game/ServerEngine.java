package Game;

import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import Server.ConnectionToClient;

public class ServerEngine implements Runnable {
	
	public GameState gs;
	Vector<ConnectionToClient> clients;
	ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	
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
		
		while (gs.inGame) {
			processInputsFromClients();
			gs.update();
			broadcastGameState();
		}
	}
	
	public void broadcastGameState() {
		
	}
	
	public void processInputFromClients() {
		sequenceInputs();
		applyInputsToGameState();
	}

	
	public void applyInputsToGameState() {
//		for (Event event: eventQ) {
//			if (event.type == "KeyEvent") {
//				if (event.keyEvent.getID() == KeyEvent.KEY_RELEASED) {
//					tank.keyReleased(event.keyEvent);
//				} else if (event.keyEvent.getID() == KeyEvent.KEY_PRESSED) {
//					tank.keyPressed(event.keyEvent);
//				}
//			}
//		}
	}
	
	public void sequenceInputs() {
		
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
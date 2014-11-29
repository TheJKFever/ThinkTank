package Client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Game.Event;
import Game.SimpleKeyEvent;
import Screens.GameScreen;

public class UserInputHandler extends KeyAdapter {

	GameScreen gameScreen;
	
	public UserInputHandler(GameScreen gs) {
		super();
		this.gameScreen = gs;
	}
	
	public void keyReleased(KeyEvent ke) {
//		System.out.println("INPUT HANDLER: KEY RELEASED");
		synchronized(gameScreen.engine.eventQ) {
			try {
				Event event = new Event("key event", new SimpleKeyEvent(ke));
				gameScreen.engine.eventQ.put(event);
				// TODO: add to gameConnection's send queue to avoid waiting
				gameScreen.gameConnection.sendEvent(event);
			} catch (InterruptedException ie) {
				System.out.println("USER INPUT HANDLER: INTERRUPTED");
				ie.printStackTrace();
			}
		}
	}
	
	public void keyPressed(KeyEvent ke) {
//		System.out.println("INPUT HANDLER: KEY PRESSED");
		synchronized(gameScreen.engine.eventQ) {
			try {
				Event event = new Event("key event", new SimpleKeyEvent(ke));
				gameScreen.engine.eventQ.put(new Event("key event", ke));
				// TODO: add to gameConnection's send queue to avoid waiting
				gameScreen.gameConnection.sendEvent(event);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
				System.out.println("USER INPUT HANDLER: INTERRUPTED");
			}
		}
	}
}

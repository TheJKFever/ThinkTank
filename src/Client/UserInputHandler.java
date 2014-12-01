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
		System.out.println("INPUT HANDLER: KEY RELEASED");
		sendKeyPressToClientAndServer(ke);
	}
	
	public void keyPressed(KeyEvent ke) {
		System.out.println("INPUT HANDLER: KEY PRESSED");
		sendKeyPressToClientAndServer(ke);
	}
	
	private void sendKeyPressToClientAndServer(KeyEvent ke) {
		Event event = new Event("key event", new SimpleKeyEvent(ke));
		synchronized(gameScreen.engine.eventQ) {
			try {
				gameScreen.engine.eventQ.put(event);
			} catch (InterruptedException ie) {
				System.out.println("USER INPUT HANDLER: INTERRUPTED");
				ie.printStackTrace();
			}
		}
		gameScreen.gameConnection.sendEvent(event);
	}
	
}

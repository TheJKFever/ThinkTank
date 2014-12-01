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
		gameScreen.sendKeyPressToClientAndServer(new SimpleKeyEvent(ke));
	}
	
	public void keyPressed(KeyEvent ke) {
		System.out.println("INPUT HANDLER: KEY PRESSED");
		gameScreen.sendKeyPressToClientAndServer(new SimpleKeyEvent(ke));
	}
}

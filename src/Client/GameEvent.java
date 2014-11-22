package Client;

import java.awt.event.KeyEvent;

public class GameEvent {
	
	String type;
	String message;
	KeyEvent keyEvent; // change to EventObject ?
	long timestamp;
	
	public GameEvent(KeyEvent e) {
		this.keyEvent = e;
		this.timestamp = System.nanoTime();
		this.type = "KeyEvent";
		this.message = null;
	}
	
	public GameEvent(String message) {
		this.keyEvent = null;
		this.timestamp = System.nanoTime();
		this.type = "Message";
	}

}

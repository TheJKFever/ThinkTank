package Game;

import java.awt.event.KeyEvent;

public class Event {
	
	public String type;
	public String message;
	public KeyEvent keyEvent; // change to EventObject ?
	public long timestamp;
	
	public Event(KeyEvent e) {
		this.keyEvent = e;
		this.timestamp = System.nanoTime();
		this.type = "KeyEvent";
		this.message = null;
	}
	
	public Event(String message) {
		this.keyEvent = null;
		this.timestamp = System.nanoTime();
		this.type = "Message";
	}

}

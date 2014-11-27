package Game;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Event implements Serializable {
	
	private static final long serialVersionUID = 7395547026975140049L;
	public String type;
	public Object data;
	public Player player;
	public long timestamp;
	
	public Event(String type) {
		this.type = type;
		this.timestamp = System.nanoTime();
	}
	
	public Event(String type, KeyEvent ke) {
		this.type = type;
		this.data = new SimpleKeyEvent(ke);
		this.timestamp = System.nanoTime();
	}
	
	public Event(String type, Object data) {
		this.type = type;
		this.data = data;
		this.timestamp = System.nanoTime();
	}
	
	public String toString() {
		return ("EVENT: { \n\ttimestamp: " + timestamp +",\n\t type: " + type + ",\n\t data: " + data + "\n}");
	}
	
}

package Game;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import Helper.Helper;

public class Event implements Serializable {
	
	private static final long serialVersionUID = 7395547026975140049L;
	public String type;
	public Object data;
	public long timestamp;
	
	public Event(KeyEvent e) {
		this.data = e;
		this.timestamp = System.nanoTime();
		this.type = "key event";
	}
	
	public Event(String message) {
		this.data = message;
		this.timestamp = System.nanoTime();
		this.type = "chat";
	}
	
	public Event(String type, Player player) {
		this.data = player;
		this.type = type;
	}
	
	public String Jsonify() {
		return Helper.Jsonify(timestamp, type, data);
	}
	
	public Object serialize() {
		return null; // TODO: FINISH
	}
}

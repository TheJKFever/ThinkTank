package Game;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Event implements Serializable {
	
	private static final long serialVersionUID = 7395547026975140049L;
	public String type;
	public Object data;
	public Player player;
	public Boolean result;
	public long timestamp;
	
	public Event(String type) {
		this(type, null, null, null);
	}
	
	public Event(String type, Object data) {
		this(type, data, null, null);
	}
	
	public Event(String type, Object data, Player player) {
		this(type, data, player, null);		
	}
	
	public Event(String type, Object data, Boolean result) {
		this(type, data, null, result);
	}
	
	public Event(String type, Object data, Player player, Boolean result) {
		this.type = type;
		this.data = data;
		this.result = result;
		this.player = player;
		this.timestamp = System.nanoTime();
	}
	

	public String toString() {
		return ("EVENT: { \n\ttimestamp: " + timestamp +",\n\t type: " + type + ",\n\t data: " + data + ",\n\t result: " + result + "\n }");
	}
	
}

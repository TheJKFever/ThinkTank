package Game;

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
	
	public Event(String type, Object data) {
		this.type = type;
		this.data = data;
		this.timestamp = System.nanoTime();
	}
	
	public String toString() {
		return ("timestamp: " + timestamp +", type: " + type + ", data: " + data);
	}
	
}

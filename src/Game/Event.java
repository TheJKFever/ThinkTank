package Game;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import Helper.Helper;

public class Event implements Serializable {
	
	private static final long serialVersionUID = 7395547026975140049L;
	public String type;
	public Object data;
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
	
	public String Jsonify() {
		return Helper.Jsonify(timestamp, type, data);
	}
	
	public Object serialize() {
		return null; // TODO: FINISH
	}
}

package Game;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class SimpleKeyEvent implements Serializable {

	private static final long serialVersionUID = 2421093008211902641L;
	
	int keyCode;
	int eventID;
	
	public SimpleKeyEvent(KeyEvent ke) {
		this.keyCode = ke.getKeyCode();
		this.eventID = ke.getID();
	}
	
	public SimpleKeyEvent(int keyCode, int id) {
		this.keyCode = keyCode;
		this.eventID = id;
	}
	
	public int getKeyCode() {
		return this.keyCode;
	}
	
	public int getID() {
		return this.eventID;
	}
	
	@Override
	public String toString() {
		return "SimpleKeyEvent: { keyCode: " + keyCode + ", eventID: " + eventID + " }"; 
	}
}

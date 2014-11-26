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
	
	public int getKeyCode() {
		return this.keyCode;
	}
	
	public int getID() {
		return this.eventID;
	}
	
	@Override
	public String toString() {
		return "SIMPLE EVENT: { keyCode: " + keyCode + ", eventID: " + eventID + " }"; 
	}
}

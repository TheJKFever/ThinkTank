package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import Game.Event;

public class ConnectionToCentralServer extends ConnectionToServer {
	public ThinkTankGUI gui;
	
	public ConnectionToCentralServer(ThinkTankGUI gui, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gui = gui;
	}
	
	public void receive(Object obj) {
		// TODO Parse all possible messages
		Event event = (Event)obj;
		switch(event.type) {
			case "new game":
				int port = (int)event.data;
				if (port==-1) throw new RuntimeException("could not create game, go back to main menu");
				else {
					gui.joinGame(port);
				}
			default:
				logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}


}

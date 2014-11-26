package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import Game.Event;
import Game.Player;
import Screens.GameScreen;

public class ConnectionToGameServer extends ConnectionToServer {
	public GameScreen gameScreen;
	
	public ConnectionToGameServer(GameScreen gameScreen, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gameScreen = gameScreen;
	}
	
	public void receive(Object obj) {
		// TODO Parse all possible messages
		Event event = Event.deserialize(obj);
		switch(event.type) {
		case "game update":
			gameScreen.engine.eventQ.add(jsonData);
		case "chat":
//			gui.chatPanel.
		case "assign player":
			engine.player = (Player)event.data;
		case "start game":
			// display gamescreen
			// tell gameEngine to start
			gameScreen.gui.startGame();
			gameScreen.engine.startGame();
		default:
			logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}
	}
}

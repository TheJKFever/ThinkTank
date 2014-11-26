package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import Game.Event;
import Game.GameState;
import Game.Player;
import Screens.GameScreen;

public class ConnectionToGameServer extends ConnectionToServer {
	public GameScreen gameScreen;
	public GameState gs;
	
	public ConnectionToGameServer(GameScreen gameScreen, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gameScreen = gameScreen;
	}

	public GameState getGameStateFromServer() {
		return gs;
	}
	
	public void receive(Object obj) {
		// TODO Parse all possible messages
		Event event = (Event)obj;
		switch(event.type) {
		case "assign player":
			gameScreen.engine.player = (Player)event.data;
		case "game update":
			this.gs = (GameState)event.data;
//			gameScreen.engine.eventQ.add(event);
		case "start game":
			gameScreen.gui.startGame();
			gameScreen.engine.startGame();
		case "chat":
//			gui.chatPanel.
		default:
			ThinkTankGUI.logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}
}

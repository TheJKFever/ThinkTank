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
	public GameState gameState;
	public Thread thread;
	
	public ConnectionToGameServer(GameScreen gameScreen, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		System.out.println("ConnectionToGameServer: CONSTRUCTOR");
		this.gameScreen = gameScreen;
		this.gameState = null;
		this.thread = new Thread(this);
	}

	public GameState getGameStateFromServer() {
//		System.out.println("ConnectionToGameServer: GETGAMESTATEFROMSERVER()");
		GameState latestState = gameState;
		gameState = null;
		return latestState;
	}
	
	public void receive(Object obj) {
		Event event = (Event) obj;
		switch(event.type) {
		case "assign player":
			System.out.println("ConnectionToGameServer: RECEIVED ASSIGN PLAYER EVENT");
			gameScreen.engine.player = (Player) event.data;
			break;
		case "game update":
//			System.out.println("ConnectionToGameServer: RECEIVED GAME UPDATE EVENT, raw");
//			System.out.println((GameState) event.data);
			this.gameState = (GameState) event.data;
			break;
		case "start game":
			System.out.println("ConnectionToGameServer: RECEIVED START GAME EVENT");
			gameScreen.gui.startGame();
			gameScreen.engine.startGame();
			break;
		case "chat":
			System.out.println("ConnectionToGameServer: RECEIVED CHAT EVENT");
//			gui.chatPanel.
			break;
		default:
			System.out.println("ConnectionToGameServer: RECEIVE UNRECOGNIZED EVENT");
			ThinkTankGUI.logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}
}

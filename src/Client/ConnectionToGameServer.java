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
		System.out.println("CONNECTIONTOGAMESERVER: CONSTRUCTOR");
		this.gameScreen = gameScreen;
		this.gameState = null;
		this.thread = new Thread(this);
	}

	public GameState getGameStateFromServer() {
		System.out.println("CONNECTIONTOGAMESERVER: GETGAMESTATEFROMSERVER()");
		GameState latestState = gameState;
		gameState = null;
		return latestState;
	}
	
	public void receive(Object obj) {
		System.out.println("CONNECTIONTOGAMESERVER: RECEIVING");
		
		Event event = (Event) obj;
		switch(event.type) {
		case "assign player":
			System.out.println("CONNECTIONTOGAMESERVER: RECEIEVED ASSIGN PLAYER EVENT");
			gameScreen.engine.player = (Player) event.data;
			break;
		case "game update":
			System.out.println("CONNECTIONTOGAMESERVER: RECEIEVED GAME UPDATE EVENT");
			this.gameState = (GameState) event.data;
			break;
		case "start game":
			System.out.println("CONNECTIONTOGAMESERVER: RECEIEVED START GAME EVENT");
			gameScreen.gui.startGame();
			gameScreen.engine.startGame();
			break;
		case "chat":
//			gui.chatPanel.
			break;
		default:
			System.out.println("CONNECTIONTOGAMESERVER: DIDN'T UNDERSTAND EVENT");
			ThinkTankGUI.logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}
}

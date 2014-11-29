package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import Game.Event;
import Game.GameState;
import Game.Helper;
import Game.Player;
import Screens.GameScreen;

public class ConnectionToGameServer extends ConnectionToServer {
	public GameScreen gameScreen;
	public GameState gameState;
	public Thread thread;
	
	public ConnectionToGameServer(GameScreen gameScreen, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gameScreen = gameScreen;
		this.gameState = null;
		this.thread = new Thread(this);
		Helper.log("Created New ConnectionToGameServer");
	}

	public GameState getGameStateFromServer() {
		Helper.log("ConnectionToGameServer: GETGAMESTATEFROMSERVER()");
		GameState latestState = gameState;
		gameState = null;
		return latestState;
	}
	
	public void receive(Object obj) {		
		Event event = (Event) obj;
		switch(event.type) {
		case "assign player":
			Helper.log("ConnectionToGameServer: RECEIVED ASSIGN PLAYER EVENT");
			gameScreen.engine.player = (Player) event.data;
			Helper.log("Assigned Player: " + gameScreen.engine.player);
			break;
		case "game update":
			// Helper.log("ConnectionToGameServer: RECEIVED GAME UPDATE EVENT, raw");
			// Helper.log((GameState) event.data);
			this.gameState = (GameState) event.data;
			// Helper.log("ConnectionToGameServer: this.gameState = ");
			// Helper.log(this.gameState);
			break;
		case "start game":
			Helper.log("ConnectionToGameServer: RECEIVED START GAME EVENT");
			gameScreen.gui.showGamePanel();
			gameScreen.engine.start();
			break;
		case "chat":
			System.out.println("ConnectionToGameServer: RECEIVED CHAT EVENT");
//			gui.chatPanel.
			break;
		default:
			Helper.log("ConnectionToGameServer: DIDN'T UNDERSTAND EVENT");
			ThinkTankGUI.logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}
}

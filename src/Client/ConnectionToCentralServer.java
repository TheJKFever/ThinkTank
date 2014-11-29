package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;

import Game.Event;
import Game.Helper;
import Server.GameObject;

public class ConnectionToCentralServer extends ConnectionToServer {
	public ThinkTankGUI gui;
	public Thread connectionToServerThread;
	
	public ConnectionToCentralServer(ThinkTankGUI gui, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gui = gui;
		connectionToServerThread = new Thread(this);
		connectionToServerThread.start();
	}
	
	public void receive(Object obj) {
		// TODO Parse all possible messages
		Event event = (Event)obj;
		int port;
		switch(event.type) {
			case "new game":
				port = (int)event.data;
				if (port==-1) throw new RuntimeException("could not create game, go back to main menu");
				else {
					gui.joinGame(port);
				}
				break;
			case "games info":
				Vector<GameObject> games = (Vector<GameObject>)event.data;
				Helper.log("Received update game event from central server, updated " + games.size() + " games.");
				gui.lobby.updateGames(games);
				break;
			case "join game":
				port = (int)event.data;
				System.out.println("CONNECTION TO CS: ABOUT TO TELL GUI TO JOIN GAME");
				gui.joinGame(port);
				break;
			default:
				ThinkTankGUI.logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}


}

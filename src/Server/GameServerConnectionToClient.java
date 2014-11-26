package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import Game.Event;
import Game.Player;

public class GameServerConnectionToClient extends ServerThread {
	private static Logger logger = Logger.getLogger("GameServer.log");
	private static int ID = 0;
	public Player player;
	public GameServer gameServer;
	
	public GameServerConnectionToClient(GameServer game, Socket client) {
		super(client);
		System.out.println("GSCONNECTIONTOCLIENT: CONSTRUCTOR");
		this.gameServer = game;
	}

	public void assignPlayer(Player p) {
		System.out.println("GSCONNECTIONTOCLIENT: ASSIGNING PLAYER");
		this.player = p;
		sendEvent(new Event("assign player", p)); // TODO: figure out how to send p so that the client receives pertinent info
	}
	
	@Override
	public void receive(Object obj) {
		System.out.println("GSCONNECTIONTOCLIENT: RECEIVING");
		Event event = (Event)obj;
		event.player = player;
		switch(event.type) {
			case "key event":
				System.out.println("GSCONNECTION: RECEIEVED KEY EVENT");
				gameServer.engine.eventQ.add(event);
				break;
			case "chat":
				// sendMessage();
				break;
			default:
				System.out.println("COULD NOT RECOGNIZE EVENT: " + event);
//				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}			
	}

	@Override
	public void listen() {
		System.out.println("GSCONNECTIONTOCLIENT: LISTENING FOR DATA FROM CLIENT");
		Object dataFromClient;
		try {
			while ((dataFromClient = in.readObject()) != null) {
				receive(dataFromClient);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
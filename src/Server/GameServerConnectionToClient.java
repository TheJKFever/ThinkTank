package Server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import Game.Event;
import Game.Helper;
import Game.Player;
import Global.Settings;

public class GameServerConnectionToClient extends ServerThread {
	private static Logger logger = Logger.getLogger("GameServer.log");
	private static int ID = 0;
	public Player player;
	public GameServer gameServer;
	
	public GameServerConnectionToClient(GameServer game, Socket client) {
		super(client);
		Helper.log("GSCONNECTIONTOCLIENT: CONSTRUCTOR");
		this.gameServer = game;
		Helper.log("Created new GameServerConnectionToClient");
	}

	public void assignPlayer(Player p) {
		Helper.log("GSCONNECTIONTOCLIENT: ASSIGNING PLAYER");
		this.player = p;
		sendEvent(new Event("assign player", p)); // TODO: figure out how to send p so that the client receives pertinent info
	}
	
	@Override
	public void receive(Object obj) {
		Helper.log("GSCONNECTIONTOCLIENT: RECEIVING");
		Event event = (Event) obj;
		System.out.println("RECEIVED EVENT:\n" + event);
		event.player = player;
		switch(event.type) {
			case "key event":
				Helper.log("GSCONNECTION: RECEIEVED KEY EVENT");
				gameServer.engine.eventQ.add(event);
				break;
			case "chat":
				// sendMessage();
				break;
			default:
				Helper.log("COULD NOT RECOGNIZE EVENT: " + event);
//				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}			
	}

	@Override
	public void listen() {
		Helper.log("GSCONNECTIONTOCLIENT: LISTENING FOR DATA FROM CLIENT");
		Object dataFromClient;
		try {
			while ((dataFromClient = in.readObject()) != null) {
				receive(dataFromClient);
			}
		} catch (EOFException eof) {
			gameServer.release(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

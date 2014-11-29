package Server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import Game.Event;
import Game.Player;
import Global.Settings;

public class GameServerConnectionToClient extends ServerThread {
	private static Logger logger = Logger.getLogger("GameServer.log");
	private static int ID = 0;
	public Player player;
	public GameServer gameServer;
	
	public GameServerConnectionToClient(GameServer game, Socket client) {
		super(client);
		if (Settings.DEBUG) System.out.println("GSCONNECTIONTOCLIENT: CONSTRUCTOR");
		this.gameServer = game;
	}

	public void assignPlayer(Player p) {
		if (Settings.DEBUG) System.out.println("GSCONNECTIONTOCLIENT: ASSIGNING PLAYER");
		this.player = p;
		sendEvent(new Event("assign player", p)); // TODO: figure out how to send p so that the client receives pertinent info
	}
	
	@Override
	public void receive(Object obj) {
		if (Settings.DEBUG) System.out.println("GSCONNECTIONTOCLIENT: RECEIVING");
		Event event = (Event) obj;
		System.out.println("RECEIVED EVENT:\n" + event);
		event.player = player;
		switch(event.type) {
			case "key event":
				if (Settings.DEBUG) System.out.println("GSCONNECTION: RECEIEVED KEY EVENT");
				gameServer.engine.eventQ.add(event);
				break;
			case "chat":
				// sendMessage();
				break;
			default:
				if (Settings.DEBUG) System.out.println("COULD NOT RECOGNIZE EVENT: " + event);
//				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}			
	}

	@Override
	public void listen() {
		if (Settings.DEBUG) System.out.println("GSCONNECTIONTOCLIENT: LISTENING FOR DATA FROM CLIENT");
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

package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.json.simple.JSONObject;

import Game.Event;
import Game.Player;
import Helper.Helper;

public class ConnectionToClient extends ServerThread {
	private static int ID = 0;
	public Player player;
	public GameServer game;
	
	public ConnectionToClient(GameServer game, Socket client) {
		super(client);
		this.game = game;
	}

	public void assignPlayer(Player p) {
		this.player = p;
		sendEvent(new Event("assign player", p)); // TODO: figure out how to send p so that the client receives pertenent info
	}
	
	@Override
	public void receive(Object obj) {
		Event event = Event.deserialize(obj);
		switch(event.type) {
		// consider making this {"type": "command", "data": "new game"...
		// instead of {"type": "new game", ...
			case "key event":
				
//				game.eventQ.add(Helper.parseEvent(jsonData));
			case "chat":
//				sendMessage();
			case "new game":
			default:
//				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}			
	}

	@Override
	public void listen() {
		String dataFromPlayer;
		try {
			while ((dataFromPlayer = in.readObject()) != null) {
				receive(dataFromPlayer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package Server;

import java.io.IOException;
import java.net.Socket;

import Game.Event;
import Game.Player;

public class ConnectionToClient extends ServerThread {
	private static int ID = 0;
	public Player player;
	public GameServer gameServer;
	
	public ConnectionToClient(GameServer game, Socket client) {
		super(client);
		this.gameServer = game;
	}

	public void assignPlayer(Player p) {
		this.player = p;
		sendEvent(new Event("assign player", p)); // TODO: figure out how to send p so that the client receives pertenent info
	}
	
	@Override
	public void receive(Object obj) {
		Event event = (Event)obj;
		event.player = player;
		switch(event.type) {
			case "key event":
				gameServer.engine.eventQ.add(event);
			case "chat":
//				sendMessage();
			default:
//				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}			
	}

	@Override
	public void listen() {
		Object dataFromPlayer;
		try {
			while ((dataFromPlayer = in.readObject()) != null) {
				receive(dataFromPlayer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

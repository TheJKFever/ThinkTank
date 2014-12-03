package Server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import Chat.ChatObject;
import Game.Event;
import Game.Helper;
import Game.Player;

public class GameServerConnectionToClient extends ConnectionToClient {
	private static Logger logger = Logger.getLogger("GameServer.log");
	public Player player;
	public GameServer gameServer;
	private Lock lock = new ReentrantLock();
	private Condition callback = lock.newCondition();
	
	public GameServerConnectionToClient(GameServer game, Socket client) {
		super(client);
		Helper.log("GSCONNECTIONTOCLIENT: CONSTRUCTOR");
		this.gameServer = game;
		Helper.log("Created new GameServerConnectionToClient");
	}

	public void assignPlayer(Player p) {
		Helper.log("GSCONNECTIONTOCLIENT: ASSIGNING PLAYER");
		this.player = p;
		sendEvent(new Event("assign player", p));
	}
	
	@Override
	public void receive(Object obj) {
		Helper.log("GSCONNECTIONTOCLIENT: RECEIVING");
		Event event = (Event) obj;
		Helper.log("RECEIVED EVENT:\n" + event);
		event.player = player;
		switch(event.type) {
			case "key event":
				Helper.log("GSCONNECTION: RECEIEVED KEY EVENT");
				gameServer.engine.eventQ.add(event);
				break;
			case "chat":
				ChatObject chat = (ChatObject)event.data;
				if (chat.to.equals("all")) {
					gameServer.broadcast(event);
				} else if (chat.to.equals("team")) {
					gameServer.teamBroadcast(chat.from.team, event);
				} else {
					gameServer.sendTo(chat.to, event);
				}
				break;
			case "set username":
				this.player.setUsername((String)event.data);
				if (gameServer.engine.gameState.inGame) {
					gameServer.broadcast(new Event("new player", this.player.username));
				} else if (gameServer.engine.gameState.ready()) {
					gameServer.broadcast(new Event("player list", gameServer.engine.gameState.players));
					gameServer.engine.start();
				}
				break;
			default:
				Helper.log("COULD NOT RECOGNIZE EVENT: " + event);
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
		} catch (EOFException|SocketException eof) {
			gameServer.release(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import Engines.ServerEngine;
import Game.Event;
import Game.GameState;
import Game.Helper;
import Game.Player;

public class GameServer extends ServerSocket implements Runnable {
	private static Logger logger = Logger.getLogger("GameServer.log");
	private static int ID=0;
	public ServerEngine engine;
	public Vector<GameServerConnectionToClient> clients;
	public String name;
	public Thread thread;
	private CentralServer centralServer;
			
	public GameServer(CentralServer cs, String name, int port) throws IOException{
		super(port);
		this.centralServer = cs;
		this.name = name;
		Helper.log("CREATED GAME SERVER SOCKET");
		clients = new Vector<GameServerConnectionToClient>();
		engine = new ServerEngine(this, clients);
		Helper.log("CREATED SERVER ENGINE");
		name = "" + ID++;
		this.thread = new Thread(this);
	}
	
	public void run() {
		listenForClients();
	}
	
	private void listenForClients() {
		Helper.log("GAMESERVER: LISTENING FOR CLIENTS");
		int teamNumber = 0;
		while(!engine.gameState.playable()) {
			try {
				Socket client = this.accept();
				addPlayer(client, (teamNumber % 2) + 1);
				teamNumber++;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		engine.start(); // starts engineThread, and sets gs.inGame
		 
		// keep listening so even more players can join, even after min join
		while (true) {
			try {
				Socket socket = this.accept();
				GameServerConnectionToClient client = addPlayer(socket, (teamNumber % 2) + 1);
				teamNumber++;
				client.sendEvent(new Event("start game"));
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public GameServerConnectionToClient addPlayer(Socket socket, int team) {
		System.out.println("GAMESERVER: ADDING A CLIENT");
		GameServerConnectionToClient client = new GameServerConnectionToClient(this, socket);
		clients.addElement(client);
		
		Player p = engine.gameState.teams[team-1].newPlayer(); // Creates new player and adds to gameState

		client.assignPlayer(p); // tells client which player is his
		client.start();
		return client;
	}
	
	// go to ConnectionToClient
	public void broadcast(Event event) {
		synchronized(clients) {
			for (GameServerConnectionToClient client:clients) {
				client.send(event);
			}
		}
	}
	
	public void teamBroadcast(Player player, String data) {
		synchronized(clients) {
			for (GameServerConnectionToClient client:clients) {
				if (client.player.team.num == player.team.num) {
					client.send(new Event("chat", data));
				}
			}
		}
	}
	
	public void release(GameServerConnectionToClient thread) {
		synchronized(clients) {
			System.out.println("releasing connection to client from central server");
			thread.interrupt();
			System.out.println("interupted thread");
			boolean removed = clients.remove(thread);
			System.out.println("removed thread from vector: " + removed);
			// TODO: released client, now needs to validate that game is still playable
			// if so do nothing, if not, then signal to all players that the game has ended.
		}
	}

	public void saveStats(GameState gameState) {
		centralServer.saveStats(gameState);
	}
}
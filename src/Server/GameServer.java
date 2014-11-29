package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import Engines.ServerEngine;
import Game.Helper;
import Game.Player;
import Global.Settings;

public class GameServer extends ServerSocket implements Runnable {
	private static Logger logger = Logger.getLogger("GameServer.log");
	private static int ID=0;
	public ServerEngine engine;
	public Vector<GameServerConnectionToClient> clients;
	public String name;
	public Thread thread;
		
	public GameServer(String name, int port) throws IOException{
		super(port);
		this.name = name;
		Helper.log("CREATED GAME SERVER SOCKET");
		clients = new Vector<GameServerConnectionToClient>();
		engine = new ServerEngine(clients);
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
		while(true) {
			try {
				Socket client = this.accept();
				addPlayer(client, (teamNumber % 2) + 1);
				teamNumber++;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void addPlayer(Socket socket, int team) {
		System.out.println("GAMESERVER: ADDING A CLIENT");
		GameServerConnectionToClient client = new GameServerConnectionToClient(this, socket);
		clients.addElement(client);
		Player p = engine.gameState.teams[team-1].newPlayer(); // Creates new player and adds to gameState
		client.assignPlayer(p); // tells client which player is his
		client.start();
	}
	
	public void release(GameServerConnectionToClient thread) {
		System.out.println("releasing connection to client from central server");
		thread.interrupt();
		System.out.println("interupted thread");
		boolean removed = clients.remove(thread);
		System.out.println("removed thread from vector: " + removed);
		// TODO: released client, now needs to validate that game is still playable
		// if so do nothing, if not, then signal to all players that the game has ended.
	}
	
	public static GameObject toObject(int port, GameServer game) {
		return new GameObject(game.name, port, game.clients);
	}
}
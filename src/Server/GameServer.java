package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import Engines.ServerEngine;
import Game.Globals;
import Game.Player;
import Game.Team;

public class GameServer extends ServerSocket implements Runnable {
	private static Logger logger = Logger.getLogger("GameServer.log");
	private static int ID=0;
	public ServerEngine engine;
	private Vector<GameServerConnectionToClient> clients;
	public String name;
	public Thread thread;
		
	public GameServer(int port) throws IOException{
		super(port);
		if (Globals.DEBUG) System.out.println("CREATED SOCKET");
		clients = new Vector<GameServerConnectionToClient>();
		if (Globals.DEBUG) System.out.println("CREATED CLIENTS");
		engine = new ServerEngine(clients);
		if (Globals.DEBUG) System.out.println("CREATED ENGINE");
		name = "" + ID++;
		this.thread = new Thread(this);
	}
	
	public void run() {
		listenForClients();
		// TODO: GRACEFULLY HANDLE CLIENTS CLOSING CONNECTION
	}
	
	private void listenForClients() {
		if (Globals.DEBUG) System.out.println("GAMESERVER: LISTENING FOR CLIENTS");
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
}
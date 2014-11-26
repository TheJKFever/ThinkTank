package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import Game.Globals;
import Game.Player;
import Game.ServerEngine;
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
		System.out.println("CREATED SOCKET");
		clients = new Vector<GameServerConnectionToClient>();
		System.out.println("CREATED CLIENTS");
		engine = new ServerEngine(clients);
		System.out.println("CREATED ENGINE");
		name = "" + ID++;
		this.thread = new Thread(this);
	}
	
	public void run() {
		listenForClients();
		// TODO: GRACEFULLY HANDLE CLIENTS CLOSING CONNECTION
	}
	
	private void listenForClients() {
		System.out.println("GAMESERVER: LISTENING FOR CLIENTS");
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
		 
		// keep listening so even more players can join, event after min join
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
	
	// TODO: create a playerExited method for onDispose of Client
	
//	public static void main(String[] args) {
//		try {
//			GameServer server = new GameServer(Globals.Development.GAME_PORT);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
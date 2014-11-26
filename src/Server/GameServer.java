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

public class GameServer extends ServerSocket {
	private static Logger logger = Logger.getLogger("GameServer.log");;
	private static int ID=0;
	public ServerEngine engine;
	private Vector<ConnectionToClient> clients;
	public String name;
		
	public GameServer(int port) throws IOException{
		super(port);
		engine = new ServerEngine(clients);
		name = "" + ID++;
		listenForClients();
	}
	
	private void listenForClients() {
		int team=0;
		while(!engine.gs.playable()) {
			try {
				Socket client = this.accept();
				addPlayer(client, team%2+1);
				team++;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		engine.start(); // starts engineThread, and sets gs.inGame
	}

	public void addPlayer(Socket socket, int team) {
		ConnectionToClient client = new ConnectionToClient(this, socket);
		clients.addElement(client);
		Player p = engine.gs.teams[team-1].newPlayer(); // Creates new player and adds to gameState
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
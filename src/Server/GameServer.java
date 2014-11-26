package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import Game.Globals;
import Game.Player;
import Game.ServerEngine;
import Helper.Helper;

public class GameServer extends ServerSocket {
	private boolean waitingForPlayers;
	private ServerEngine game;
	private Logger logger;
	private Vector<ConnectionToClient> clients;
		
	public GameServer(int port) throws IOException{
		super(port);
		game = new ServerEngine(clients);
		listenForClients();
	}
	
	private void listenForClients() {
		waitingForPlayers = true;
		int team=0;
		while(waitingForPlayers) {
			try {
				Socket client = this.accept();
				addPlayer(new ConnectionToClient(client, team%2+1));
				team++;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		startGame();
	}
	
	public void startGame() {
		// TODO: implement
		// clients should be on the waiting page, send a signal to start game
		game.start();
	}

	public void addPlayer(ConnectionToClient client) {
		clients.addElement(client);
		switch(client.team) {
		case 1:
			Player p = game.gs.teams[0].newPlayer();
			client.assignPlayer(p);
			if (game.gs.teams[1].players.size()>0) waitingForPlayers = false;
			break;
		case 2:
			Player p = game.gs.teams[1].newPlayer();
			client.assignPlayer(p);
			if (game.gs.teams[0].players.size()>0) waitingForPlayers = false;
			break;
		default:
			int nonExistentTeam = client.team;
			client.team = (int)(Math.ceil(Math.random()*2));
			client.send(Helper.Jsonify("warning", "Team " + nonExistentTeam + " does not exist, assigned player to team " + client.team));
			addPlayer(client);
		}
		client.start();
	}
	
	// TODO: create a playerExited method for onDispose of Client
	
	public static void main(String[] args) {
		try {
			GameServer server = new GameServer(Globals.Development.GAME_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
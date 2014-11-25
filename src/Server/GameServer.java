package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import Game.Globals;
import Game.ServerEngine;
import Helper.Helper;

public class GameServer extends ServerSocket {
	private boolean waitingForPlayers;
	private ServerEngine game;
	private Logger logger;
	private Vector<ConnectionToClient> players;
		
	public GameServer(int port) throws IOException{
		super(port);
		game = new ServerEngine();
		listenForPlayers();
	}
	
	private void listenForPlayers() {
		waitingForPlayers = true;
		int team=0;
		while(waitingForPlayers) {
			try {
				Socket player = this.accept();
				addPlayer(new ConnectionToClient(player, team%2+1));
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

	public void addPlayer(ConnectionToClient player) {
		players.addElement(player);
		switch(player.team) {
		case 1:
			game.gs.team1.newPlayer();
			if (game.gs.team2.players.size()>1) waitingForPlayers = false;
			break;
		case 2:
			game.gs.team2.newPlayer();
			if (game.gs.team1.players.size()>1) waitingForPlayers = false;
			break;
		default:
			int nonExistentTeam = player.team;
			player.team = (int)(Math.ceil(Math.random()*2));
			player.send(Helper.Jsonify("warning", "Team " + nonExistentTeam + " does not exist, assigned player to team " + player.team));
			addPlayer(player);
		}
		player.start();
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
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

public class TankGame extends Thread {
	private ServerSocket server;
	private ArrayList<PlayerThread> team1;
	private ArrayList<PlayerThread> team2;
	private boolean waitingForPlayers;
	
	public TankGame(int port){
		Socket client = game.accept(); // Listen for new client joining
		// Add new player to game
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String teamInit="";
		try {
			// TODO: make sure operation doesn't stop here and wait if no input given.
			teamInit = br.readLine(); 
		} catch (IOException e) {
			logger.log(Level.WARNING, "", e);
		}

		// If client sent a team to join, get team. otherwise choose random number between 1 and 2
		int team  = (teamInit!=null) ? Integer.parseInt(teamInit) : (int)(Math.ceil(Math.random()*2));
		PlayerThread newPlayer = new PlayerThread(client, team);
		game.addPlayer(newPlayer, team);
		newPlayer.start();
		logger.log(Level.INFO, "added new player on port: " + port + " to team: " + team);

		if (game.isReady()){
			game.start();
		}

		
		
		
		
		this.server = new ServerSocket(port);
		team1 = new ArrayList<PlayerThread>();
		team2 = new ArrayList<PlayerThread>();
		waitingForPlayers = true;
	}
	
	public Socket accept() {
		return this.server.accept();
	}

	// TODO: consider making this a boolean method
	public void addPlayer(PlayerThread player, int team) {
		switch(team) {
		case 1:
			team1.add(player);
			if (team2.size()>1) waitingForPlayers = false;
			break;
		case 2:
			team2.add(player);
			if (team1.size()>1) waitingForPlayers = false;
			break;
		default:
			if (player.client.isConnected()) {
				// TODO: send message to client saying team does not exists, choosing team at random
			}
			addPlayer(player, (int)(Math.ceil(Math.random()*2)));
		}
	}
	
	// TODO: create a playerLeft method for onDispose of Client
	
	public boolean isReady() {
		if (!waitingForPlayers) return true;
		return false;
	}
	
	@Override
	public void run() {
		// TODO: 
	}
}
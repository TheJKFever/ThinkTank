package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import Helper.Helper;

public class ThinkTankGameServer extends ServerSocket {
	private boolean waitingForPlayers;
	private ThinkTank game;
	private Logger logger;
		
	public ThinkTankGameServer(int port) throws IOException{
		super(port);
		game = new ThinkTank();
		listenForPlayers();		
	}
	
	private void listenForPlayers() {
		waitingForPlayers = true;
		int team=0;
		while(waitingForPlayers) {
			try {
				Socket player = this.accept();
				addPlayer(new PlayerThread(player, team%2+1));
				team++;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		startGame();
	}
	
	public void startGame() {
		// TODO: implement
	}

	// TODO: consider making this a boolean method
	public void addPlayer(PlayerThread player) {
		switch(player.team) {
		case 1:
			game.gs.team1.add(player);
			if (game.gs.team2.size()>1) waitingForPlayers = false;
			break;
		case 2:
			game.gs.team2.add(player);
			if (game.gs.team1.size()>1) waitingForPlayers = false;
			break;
		default:
			int nonExistantTeam = player.team;
			player.team = (int)(Math.ceil(Math.random()*2));
			player.send(Helper.Jsonify("warning", "Team " + nonExistantTeam + " does not exist, assigned player to team " + player.team));
			addPlayer(player);
		}
		player.start();
	}
	
	// TODO: create a playerExited method for onDispose of Client
	
	public class PlayerThread extends ServerThread {
		public int team;
		
		public PlayerThread(Socket client, int team) {
			super(client);
			this.team = team;
		}

		public void send(String jsonify) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void received(String data) {
		/*		Format of JSON data
		 * 		{
		 * 		  "timestamp": 0nanoseconds0,
		 *		  "type": "type of message",
		 *		  "data": "this varies based off type",
		 *		  "result": true <- if boolean result
		 * 		}
		 */
			JSONObject jsonData = Helper.parse(data);
			String type = (String)jsonData.get("type");
			switch(type) {
			// consider making this {"type": "command", "data": "new game"...
			// instead of {"type": "new game", ...
				case "event":
					game.eventQ.add(Helper.parseEvent(jsonData));
				case "chat":
					sendMessage();
				default:
					logger.log(Level.INFO, "Parse error. did not understand message: " + data);
			}			
		}

		@Override
		public void listen() {
			String dataFromPlayer;
			try {
				while ((dataFromPlayer = in.readLine()) != null) {
					received(dataFromPlayer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
package Game;

import Chat.ChatClient;
import Entities.Tank;
import Server.ConnectionToClient;

public class Player {
	public Tank tank;
	public ChatClient chat;
//	public ConnectionToClient client;
	public Team team;
	public GameState gs;
	
	public Player(Team team, GameState gs) {
		this.team = team;
		this.gs = gs;
	}
	
	public Player() {
		
	}
}

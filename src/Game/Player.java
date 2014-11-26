package Game;

import Chat.ChatClient;
import Entities.Tank;

public class Player {
	public Tank tank;
	public ChatClient chat;
	public Team team;
	public GameState gs;
	
	public Player(Team team, GameState gs) {
		this.team = team;
		this.gs = gs;
		tank = new Tank(this, gs);
	}
	
	public Player() {
		
	}
}

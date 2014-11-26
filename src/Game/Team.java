package Game;

import java.util.Vector;

import Entities.Brain;

public class Team {
	public int num;
	public Brain brain;
	public Vector<Player> players;
	GameState gs;
	
	public Team(int num, GameState gs) {
		this.gs = gs;
		this.num = num;
	}
	
	public Player newPlayer() {
		Player player = new Player(this, gs);
		players.addElement(player);
		return player;
	}

}

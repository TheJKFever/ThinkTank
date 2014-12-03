package Game;

import java.io.Serializable;
import java.util.Vector;

import Entities.Brain;

public class Team implements Serializable  {
	
	private static final long serialVersionUID = 5095457291029072701L;
	
	public int num;
	public Brain brain;
	public Vector<Player> players;
	public boolean winner;
	GameState gs;
	
	public Team(int num, GameState gs) {
		players = new Vector<Player>();
		this.num = num;
		this.gs = gs;
		winner = false;
		brain = new Brain(this, gs);
	}
	
	public Player newPlayer() {
		Player player = new Player(this, gs);
		players.addElement(player);
		// player has no username at this point
		return player;
	}

}

package Game;

import java.util.Vector;

import Entities.Brain;

public class Team {
	public int teamNumber;
	public Brain brain;
	public Vector<Player> players;
	GameState gs;

	public Team(int teamNumber, GameState gs) {
		this.gs = gs;
		this.teamNumber = teamNumber;
	}

}

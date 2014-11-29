package Client;

import java.util.Vector;

import Entities.Brain;
import Entities.Tank;

public class Team {
	public int teamNumber;
	public Brain brain;
	public Vector<Player> players;

	public Team(int teamNumber, GameState gs) {
		this.teamNumber = teamNumber;
		players=new Vector<Player>();
		brain = new Brain(teamNumber, gs);
		gs.brains.add(brain);
	}
	
	public void newPlayer() {
		Player player = new Player();
		players.add(player);
	}
}

package Game;

import java.io.Serializable;

import Entities.ProfileObject;
import Entities.Tank;

public class Player implements Serializable  {
	
	private static final long serialVersionUID = 8546860096571379783L;
	private static int playerCount=0;
	public Tank tank;
	public Team team;
	public GameState gs;
	private boolean usernameIsSet = false;
	
	public int numKills;
	public int numDeaths;
	public int numShots;
	public int numHits;
	public String username;
	public boolean destroyedBrain;
	
	public Player(Team team, GameState gs) {
		this.team = team;
		this.gs = gs;
		gs.players.add(this);
		this.tank = new Tank(this, gs);
		this.numKills = 0;
		this.numDeaths = 0;
		this.numShots = 0;
		this.numHits = 0;
	}
	
	public void setUsername(String username) {
		System.out.println("\nSETTING USERNAME\n");
		if (username.equals("guest")) {
			username += ""+(playerCount++);
		}
		this.username = username;
		usernameIsSet = true;
	}

	public boolean usernameSet() {
		return usernameIsSet;
	}
}

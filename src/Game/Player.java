package Game;

import java.io.Serializable;

import Entities.ProfileObject;
import Entities.Tank;

public class Player implements Serializable  {
	
	private static final long serialVersionUID = 8546860096571379783L;
	
	public Tank tank;
	public Team team;
	public GameState gs;
	
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
		this.username = username;
	}
}

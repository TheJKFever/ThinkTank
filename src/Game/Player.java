package Game;

import java.io.Serializable;

import Client.ChatClient;
import Entities.Tank;

public class Player implements Serializable  {
	
	private static final long serialVersionUID = 8546860096571379783L;
	
	public Tank tank;
	public Team team;
	public GameState gs;
	
	public Player(Team team, GameState gs) {
		this.team = team;
		this.gs = gs;
		gs.players.add(this);
		tank = new Tank(this, gs);
	}
	
	public Player() {
		
	}
}

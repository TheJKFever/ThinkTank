package Game;

import java.io.Serializable;
import java.util.Vector;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;

public class GameState implements Serializable {
	
	private static final long serialVersionUID = 9118715319749588761L;
	
	public Team[] teams;
	public Vector<Brain> brains;
	public Vector<Player> players;
	public Vector<Shot> shots;
	public Vector<Tank> tanks;
	public Vector<Barrier> barriers;
//	public Vector<Turret> turrets; TODO: ADD TURRETS
	public boolean inGame;
	
	public GameState() {
		inGame = false;
		
		barriers = new Vector<Barrier>();
		brains = new Vector<Brain>();
		shots = new Vector<Shot>();
		players = new Vector<Player>();
		tanks = new Vector<Tank>();
		teams = new Team[2];
		
		// create teams
		for (int i=0; i < teams.length; i++) {
			teams[i] = new Team(i+1, this);
		}
		
		setUpMap();
		Helper.log("Created new GameState");
	}
	
	public boolean playable() {
		Helper.log("GAMESTATE: PLAYABLE()?");
//		if (teams != null) {
//			Helper.log("GAMESTATE: TEAM 1 SIZE" + teams[0].players.size() + " TEAM 2 SIZE" + teams[1].players.size());
//		} else {
//			Helper.log("GAMESTATE: .teams is NULL");
//		}

		if (teams != null && 
			teams[0].players.size() > 0 && 
			teams[1].players.size() > 0) {
			Helper.log("GAMESTATE: YES, PLAYABLE");
				return true;
		} else {	
			Helper.log("GAMESTATE: NO, NOT PLAYABLE");
			return false;
		}
	}
	
	public void setUpMap() {
		Helper.log("GAMESTATE: SETUPMAP()");
		new Barrier(100, 100, 300, 10, this);
		new Barrier(200, 200, 200, 10, this);
		new Barrier(300, 300, 400, 10, this);
		new Barrier(400, 400, 100, 10, this);
		new Barrier(150, 150, 10, 200, this);
		new Barrier(250, 250, 10, 100, this);		
		// TODO: Add Thoughtpools
	}
	
	public void update() {
		// Helper.log("GAMESTATE: UPDATE");
		
		// tanks
		for (Tank t: tanks) {
			t.update();
		}
		// brains
		for (Brain b: brains) {
			b.update();
		}
		// barriers 
		for (Barrier b: barriers) {
			b.update();
		}
		// shots
		for (int i = (shots.size() - 1); i >= 0; i--) {
			Shot shot = shots.get(i);
			if (!shot.isVisible()) {
				shots.remove(i);
			} else {
				shot.update();
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("------------------------------------------\n");
		sb.append("GAME STATE:\n");
		sb.append("IN GAME: " + this.inGame + "\n");
		for (int i = 0; i < teams.length; i++) {
			Team team = teams[i];
			sb.append("TEAM " + (i+1) + ":\n");
			sb.append(team.brain);
			sb.append("Players: " + team.players.size() + "\n");
			for (Player player: team.players) {
				sb.append(player.tank);	
			}			
		}
		sb.append("SHOTS: " + this.shots.size() + "\n");
		for (Shot sh: shots) {
			sb.append(sh);	
		}
		sb.append("------------------------------------------\n");
		return sb.toString();
	}
}
package Game;

import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Entities.ThoughtPool;
import Global.Settings;

public class GameState implements Serializable {
	
	private static final long serialVersionUID = 9118715319749588761L;
	
	public Team[] teams;
	public Vector<Brain> brains;
	public Vector<Player> players;
	public Vector<Shot> shots;
	public Vector<Tank> tanks;
	public Vector<Barrier> barriers;
	public Vector<ThoughtPool> thoughtPools;
//	public Vector<Turret> turrets; TODO: ADD TURRETS
	public boolean inGame;
	
	public GameState() {
		System.out.println("GAMESTATE: CONSTRUCTOR");
		
		inGame = false;
		
		barriers = new Vector<Barrier>();
		thoughtPools = new Vector<ThoughtPool>();
		brains = new Vector<Brain>();
		shots = new Vector<Shot>();
		players = new Vector<Player>();
		tanks = new Vector<Tank>();
		teams = new Team[2];
		
		// create teams with brains
		for (int i=0; i < teams.length; i++) {
			teams[i] = new Team(i+1, this);
			Brain b = new Brain(teams[i], this);
			teams[i].brain = b;
			brains.add(b);	
		} // TODO: make all the same style
		
		setUpMap();
	}
	
	public boolean playable() {
		System.out.println("GAMESTATE: PLAYABLE()?");
		
		if (teams != null && 
			teams[0].players.size() > 0 && 
			teams[1].players.size() > 0) {
			System.out.println("GAMESTATE: YES, PLAYABLE");
				return true;
		} else {	
			System.out.println("GAMESTATE: NO, NOT PLAYABLE");
			return false;
		}
	}
	
	public void setUpMap() {
		System.out.println("GAMESTATE: SETUPMAP()");
	
		// top wall
		barriers.add(new Barrier(0, 0, Global.Settings.BOARD_WIDTH, 10, this));
		// left wall
		barriers.add(new Barrier(0, 0, 10, Global.Settings.BOARD_HEIGHT, this));
		// bottom wall
		barriers.add(new Barrier(0, Settings.BOARD_HEIGHT-10, Settings.BOARD_WIDTH, 10, this));
		// right wall
		barriers.add(new Barrier(Settings.BOARD_WIDTH - 10, 0, 10, Settings.BOARD_HEIGHT, this));
				
		// TODO: create interesting map, not just random barriers			
		barriers.add(new Barrier(100, 100, 300, 10, this));
		barriers.add(new Barrier(200, 200, 200, 10, this));
		barriers.add(new Barrier(300, 300, 400, 10, this));
		barriers.add(new Barrier(400, 400, 100, 10, this));
		barriers.add(new Barrier(150, 150, 10, 200, this));
		barriers.add(new Barrier(250, 250, 10, 100, this));
		
		thoughtPools.add(new ThoughtPool(200, 200, 50, 50, this));
		thoughtPools.add(new ThoughtPool(250, 250, 50, 50, this));
		thoughtPools.add(new ThoughtPool(300, 300, 20, 40, this));
		thoughtPools.add(new ThoughtPool(350, 350, 70, 70, this));
		thoughtPools.add(new ThoughtPool(400, 400, 60, 40, this));
		thoughtPools.add(new ThoughtPool(450, 450, 20, 20, this));
	}
	
	public void update() {
		System.out.println("GAMESTATE: UPDATE");
		
		// tanks
		for (Tank t: tanks) {
			t.update();
		}
		
//		for (Shot s: shots) {
//			s.update();
//		}
		// shots
		for (int i = (shots.size() - 1); i >= 0; i--) {
			Shot shot = shots.get(i);
			if (!shot.isVisible()) {
				shots.remove(i);
			} else {
				shot.update();
			}
		}
		// brains
//		for (Brain b: brains) {
//			b.update();
//		}
		// barriers 
//		for (Barrier b: barriers) {
//			b.update();
//		}
		// Thought Pools
//		for (ThoughtPool tp: thoughtPools) {
//			tp.update();
//		}
		
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
//		for (Shot sh: shots) {
//			sb.append(sh);	
//		}
		sb.append("------------------------------------------\n");
		return sb.toString();
	}
}
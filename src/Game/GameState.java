package Game;

import java.io.Serializable;
import java.util.Vector;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Server.GameServerConnectionToClient;

public class GameState implements Serializable {
	
	private static final long serialVersionUID = 9118715319749588761L;
	public Team[] teams;
	public Vector<Brain> brains;
	public Vector<Player> players;
	public Vector<Shot> shots;
	public Vector<Tank> tanks;
	public Vector<Barrier> barriers;
//	public Vector<Turret> turrets; TODO: ADD TURRETS
	public boolean inGame = false;
	
	public GameState() {
		System.out.println("GAMESTATE: CONSTRUCTOR");
		
		barriers = new Vector<Barrier>();
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
		if (teams!= null) {
			System.out.println("GAMESTATE: TEAM 1 SIZE" + teams[0].players.size() + " TEAM 2 SIZE" + teams[1].players.size());
		} else {
			System.out.println("GAMESTATE: .teams is NULL");
		}
		if (teams != null && 
			teams[0].players.size() > 0 && 
			teams[1].players.size() > 0) {
			System.out.println("GAMESTATE: YES, PLAYABLE");
				return true;
		} else {	
			System.out.println("GAMESTATE: NO, PLAYABLE");
			return false;
		}
	}
	
	public void setUpMap() {
		System.out.println("GAMESTATE: SETUPMAP()");
		// TODO: adds barriers, thought pools
		barriers.add(new Barrier(100, 100, 300, 10, this));
		barriers.add(new Barrier(200, 200, 200, 10, this));
		barriers.add(new Barrier(300, 300, 400, 10, this));
		barriers.add(new Barrier(400, 400, 100, 10, this));
		barriers.add(new Barrier(150, 150, 10, 200, this));
		barriers.add(new Barrier(250, 250, 10, 100, this));
		
		// TODO: Thoughtpools
	}
	
	public void update() {
		System.out.println("GAMESTATE: UPDATE");
		
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
	
}
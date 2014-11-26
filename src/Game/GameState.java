package Game;

import java.io.Serializable;
import java.util.Vector;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Server.ConnectionToClient;

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
		barriers = new Vector<Barrier>();
		brains = new Vector<Brain>();
		shots = new Vector<Shot>();
		players = new Vector<Player>();
		tanks = new Vector<Tank>();
		teams = new Team[2];
		
//		// create teams and brains
//		teams[0] = new Team(1, this);
//		Brain b = new Brain(teams[0], this);
//		teams[0].brain = b;
//		brains.add(b);	
//		teams[1] = new Team(2, this);
//		b = new Brain(teams[1], this);
//		teams[1].brain = b;
//		brains.add(b);	
		
		for (int i=0; i < teams.length; i++) {
			teams[i] = new Team(i+1, this);
			Brain b = new Brain(teams[i], this);
			teams[i].brain = b;
			brains.add(b);	
		} // TODO: make all the same style
		
		setUpMap();
	}
	
	public boolean playable() {
		if (teams[0].players.size()>0 && teams[1].players.size()>0) return true;
		return false;
	}
	
	public void setUpMap() {
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
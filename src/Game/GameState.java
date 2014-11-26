package Game;

import java.util.Vector;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Server.ConnectionToClient;

public class GameState {
	// TODO: these should probably all be private with getters, and no setters
	public Team[] teams = new Team[2];
	public Vector<Brain> brains;
	public Vector<Player> players;
	public Vector<Shot> shots;
	public Vector<Tank> tanks;
	public Vector<Barrier> barriers;
	public Vector<Turret> turrets;
	public boolean inGame = false;
	
	public GameState() {
		barriers = new Vector<Barrier>();
		shots = new Vector<Shot>();
		
		// create teams and brains
		for (int i = 1; i <= teams.length; i++) {
			teams[i] = new Team(i, this);
			Brain b = new Brain(teams[i], this);
			teams[i].brain = b;
			brains.add(b);	
		} // TODO: make all the same style
		
		// adds barriers, thought pools
		setUpMap();
	}
	
	public boolean playable() {
		if (teams[0].players.size()>0 && teams[1].players.size()>0) return true;
		return false;
	}
	
	public void setUpMap() {
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
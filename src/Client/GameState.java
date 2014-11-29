package Client;

import java.util.ArrayList;
import java.util.Vector;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Entities.Turret;

public class GameState {
	// TODO: these should probably all be private with getters, and no setters
	public Team team1;
	public Team team2;
	public Vector<Shot> shots;
	public Vector<Turret> turrets;
	public long startTime;
	public Vector<Brain> brains;
	public Vector<Barrier> barriers;
	public Vector<Tank> tanks;
	
	public GameState() {
		brains = new Vector<Brain>();
		tanks = new Vector<Tank>();
		barriers = new Vector<Barrier>();

		team1 = new Team(1, this);
		team2 = new Team(2, this);
		shots = new Vector<Shot>();
		turrets = new Vector<Turret>();
		startTime = System.currentTimeMillis();
	}
}
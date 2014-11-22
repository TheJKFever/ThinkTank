package Client;

import java.util.Vector;

import Server.ThinkTankGameServer.PlayerThread;

public class GameState {
	// TODO: these should probably all be private with getters, and no setters
	public Vector<PlayerThread> team1;
	public Vector<PlayerThread> team2;
	public Vector<Bullet> shots;
	public Vector<Turret> turrets;
	public long startTime;
	public Vector<Updatable> updatables;

	public GameState() {
		team1 = new Vector<PlayerThread>();
		team2 = new Vector<PlayerThread>();
		shots = new Vector<Bullet>();
		turrets = new Vector<startTime>();
		updatables = new Vector<Updatables>();
		startTime = System.currentTimeMillis();
	}
	
}

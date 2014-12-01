package Game;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Entities.ThoughtPool;
import Entities.Turret;
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
	public Vector<Turret> turrets;
	public boolean inGame;
	public long timeRemaining;
	public long startTime;
	public long timeElapsed;
	public String displayTime;
	public Tank tankForThisClient = null;
	public int winningTeam;
	
	public int cornerLength = 50;
	public int squareLength = 140;
	public int spacer = 45;
	
	public GameState() {
		inGame = false;
		
		barriers = new Vector<Barrier>();
		thoughtPools = new Vector<ThoughtPool>();
		turrets = new Vector<Turret>();
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
		
		//Initially
		timeRemaining = TimeUnit.MINUTES.toNanos(10L);
		timeElapsed = 0L;
	}
	
	public void startGameClock() {
		startTime = System.nanoTime();
		updateGameClock();
	}
	
	
	public void updateGameClock() {
		timeRemaining = TimeUnit.MINUTES.toNanos(10) - (System.nanoTime() - startTime);
		long minutes = TimeUnit.NANOSECONDS.toMinutes(timeRemaining);
		long seconds = TimeUnit.NANOSECONDS.toSeconds(timeRemaining - TimeUnit.MINUTES.toNanos(minutes));
		displayTime = String.format("%02d:%02d", minutes, seconds);
		if (timeRemaining <= 0L) {
			endGame();
		}
	}
	
	public void endGame() {
		// TODO: implement endGame()
		this.inGame = false;
		calculateStats();
		// display GAME OVER
		// display winner
		// display stats
		// display replay button
	}
	
	public void calculateStats() {
		// calculate winner based on brain health
		// set winningTeam to higher brainhealth, etc.
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
		System.out.println("GAMESTATE: SETUPMAP()");
	
		// top wall
		barriers.add(new Barrier(0, 0, Global.Settings.BOARD_WIDTH, 10, this));
		// left wall
		barriers.add(new Barrier(0, 0, 10, Global.Settings.BOARD_HEIGHT, this));
		// bottom wall
		barriers.add(new Barrier(0, Settings.BOARD_HEIGHT-10, Settings.BOARD_WIDTH, 10, this));
		// right wall
		barriers.add(new Barrier(Settings.BOARD_WIDTH - 10, 0, 10, Settings.BOARD_HEIGHT, this));
				
		// LEFT BRAIN WALL
		barriers.add(new Barrier(100+spacer, spacer+squareLength+spacer, 10, squareLength, this));
		
		// RIGHT BRAIN WALL
		barriers.add(new Barrier(700-spacer-10, spacer+squareLength+spacer, 10, squareLength, this));
				
		drawSquare(100, 0);
		drawSquare(100, (spacer + squareLength));
		drawSquare(100, (spacer + squareLength) * 2);
		
		drawSquare(100 + (spacer + squareLength), 0);
		drawSquare(100 + (spacer + squareLength), (spacer + squareLength));
		drawSquare(100 + (spacer + squareLength), (spacer + squareLength) * 2);
		
		drawSquare(100 + (2*(spacer + squareLength)), 0);
		drawSquare(100 + (2*(spacer + squareLength)), (spacer + squareLength));
		drawSquare(100 + (2*(spacer + squareLength)), (spacer + squareLength)*2);
	}
	
	public void generateRandomThoughtPool() {
		int randY = (int)(Math.random() * 3 + 1);
		int randX = (int)(Math.random() * 3 + 1);
		int tpy = ((45 + 140) * randY) - 70 - 15;
		int tpx = 100 + ((45 + 140)*randX) - 70 - 15;
		thoughtPools.add(new ThoughtPool(tpx, tpy, 30, 30, this));
	}
	
	public void drawSquare(int startX, int startY) {
		
		// top left corner
		barriers.add(new Barrier(startX+spacer, startY+spacer, cornerLength, 10, this));
		barriers.add(new Barrier(startX+spacer, startY+spacer, 10, cornerLength, this));
		
		// bottom left corner
		barriers.add(new Barrier(startX+spacer, startY+spacer+squareLength-10, cornerLength, 10, this));
		barriers.add(new Barrier(startX+spacer, startY+spacer+squareLength-cornerLength, 10, cornerLength, this));
		
		// top right corner
		barriers.add(new Barrier(startX+spacer+squareLength-cornerLength, startY+spacer, cornerLength, 10, this));
		barriers.add(new Barrier(startX+spacer+squareLength-10, startY+spacer, 10, cornerLength, this));
				
		// bottom right corner
		barriers.add(new Barrier(startX+spacer+squareLength-cornerLength, startY+spacer+squareLength-10, cornerLength, 10, this));
		barriers.add(new Barrier(startX+spacer+squareLength-10, startY+spacer+squareLength-cornerLength, 10, cornerLength, this));
	}
	
	public void update() {
		// Helper.log("GAMESTATE: UPDATE");
		
		// tanks
		for (Tank t: tanks) {
			t.update();
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
		
		for (Turret t:turrets)
		{
			t.update();
		}
		
		updateGameClock();
		
		if (displayTime.contains(":59") || displayTime.contains(":20") || displayTime.contains(":40")) {
			thoughtPools.clear();
			generateRandomThoughtPool();
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
//		for (Shot sh: shots) {
//			sb.append(sh);	
//		}
		sb.append("------------------------------------------\n");
		return sb.toString();
	}

	public String getDisplayTime() {
		if ((Long)startTime != null) {
			updateGameClock();
			return displayTime;
		} else {
			return String.format("%02d:%02d", 10, 0);
		}
	}
}
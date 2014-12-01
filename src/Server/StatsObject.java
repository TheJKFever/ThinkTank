package Server;

import java.io.Serializable;

public class StatsObject implements Serializable {
	private static final long serialVersionUID = 2617531284165291485L;
	public String username;
	public int total_games, wins, numShots, numDeaths, numKills, numHits, brainsDestroyed; 
	public StatsObject(String username) {
		this.username = username;
	}
}

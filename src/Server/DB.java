package Server;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import Entities.ProfileObject;
import Game.GameState;
import Game.Player;
import Global.Settings;

public class DB {
	protected ReentrantLock queryLock;
	public Connection connection;
	
	public DB(ReentrantLock queryLock) {
		this.queryLock = queryLock;
		try {
			Class.forName(Settings.Development.DB.DRIVER);
			connection = DriverManager.getConnection(
					Settings.Development.DB.ADDRESS + Settings.Development.DB.NAME, 
					Settings.Development.DB.USER, 
					Settings.Development.DB.PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean insertProfile(ProfileObject profile) throws UserAlreadyExistsException {
		queryLock.lock();
		String query="";
		try {
			// TODO: Select query of name, if more than 0 records return false
			query="SELECT COUNT(*) FROM players WHERE name = \"" + profile.username + "\"";
			Statement st = connection.createStatement();
			ResultSet results = st.executeQuery(query);
			results.next();
			if (results.getInt(1)!=0) {
				queryLock.unlock();
				throw new UserAlreadyExistsException("Username already exists");
			}
			
			// Create new player
			query="INSERT INTO players (name, password) VALUES (?,?);";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, profile.username);
			stmt.setString(2, profile.password);
			stmt.execute();
			queryLock.unlock();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			queryLock.unlock();
			return false;
		}
		return true;
	}

	public boolean attemptLogin(ProfileObject profile) throws Exception {
		String query="";
		try {
			query="SELECT COUNT(*) FROM players WHERE name=\"" + profile.username + "\"";
			Statement st = connection.createStatement();
			ResultSet results = st.executeQuery(query);
			results.next();
			if (results.getInt(1)!=0) {
				query="SELECT COUNT(*) FROM players WHERE name=\"" + profile.username + "\" AND password =\"" + profile.password + "\"";
				st = connection.createStatement();
				results = st.executeQuery(query);
				results.next();
				if (results.getInt(1)!=0) return true;
				else throw new Exception("Incorrect password");
			} else {
				throw new Exception("Incorrect username");
			}
		} catch (SQLException e) {
			throw new Exception("Unknown SQL Exception");
		}
	}

	public void saveStats(GameState gameState) {
		queryLock.lock();
		try {
			for (Player player: gameState.players) {
				if (player.username.equals("guest")) continue;
				String command = "UPDATE players " +
					"SET total_games = total_games + 1," +
					"num_kills = num_kills + ?," +
					"num_deaths = num_deaths + ?," +
					"num_shots = num_shots + ?," +
					"num_hits = num_hits + ?," +
					"wins = wins + ?, " +
					"brains_destroyed = brains_destroyed + ? " +
					"WHERE name = ?";
				
				PreparedStatement stmt = connection.prepareStatement(command);
				stmt.setInt(1, player.numKills);
				stmt.setInt(2, player.numDeaths);
				stmt.setInt(3, player.numShots);
				stmt.setInt(4, player.numHits);
				int won = (gameState.winningTeam == player.team.num)? 1 : 0;
				stmt.setInt(5, won);
				int destroyedBrain = (player.destroyedBrain)? 1 : 0;
				stmt.setInt(6, destroyedBrain);
				stmt.setString(7, player.username);
				stmt.executeUpdate();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		queryLock.unlock();
	}
	
	public StatsObject getStatsFor(String username) {
		StatsObject stats = new StatsObject(username);
		String query="";
		try {
			query="SELECT * FROM players WHERE name=\"" + username + "\"";
			Statement st = connection.createStatement();
			ResultSet results = st.executeQuery(query);
			results.next();
			stats.total_games = results.getInt("total_games");
			stats.wins = results.getInt("total_games");
			stats.numShots = results.getInt("num_shots");
			stats.numDeaths = results.getInt("num_deaths");
			stats.numKills = results.getInt("num_kills");
			stats.numHits = results.getInt("num_hits");
			stats.brainsDestroyed = results.getInt("brains_destroyed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stats;
	}

	public class UserAlreadyExistsException extends Exception {
		public UserAlreadyExistsException(String msg) {
			super(msg);
		}
	}
}

package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import Entities.ProfileObject;
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
		String query="";
		try {
			// TODO: Select query of name, if more than 0 records return false
			query="SELECT COUNT(*) FROM players WHERE name = \"" + profile.username + "\"";
			Statement st = connection.createStatement();
			ResultSet results = st.executeQuery(query);
			results.next();
			if (results.getInt(1)!=0) {
				throw new UserAlreadyExistsException("Username already exists");
			}
			
			// Create new player
			query="INSERT INTO players (name, password) VALUES (?,?);";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, profile.username);
			stmt.setString(2, profile.password);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
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
	
	public class UserAlreadyExistsException extends Exception {
		public UserAlreadyExistsException(String msg) {
			super(msg);
		}
	}
}

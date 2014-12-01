package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import Entities.Objects.ProfileObject;
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

	public boolean insertProfile(ProfileObject profile) {
		String username = profile.username;
		String password = profile.password;
		String query="";
		try {
			// TODO: Select query of name, if more than 0 records return false
			// Create new player
			query="INSERT INTO players (name, password) VALUES (?,?);";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(1, password);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

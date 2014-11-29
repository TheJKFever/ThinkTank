package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import Client.GameState;

public class DB implements Runnable
{
	public static final String ADDRESS = "jdbc:mysql://localhost/";
	public static final String NAME = "lab11";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "root";
	
	protected ReentrantLock queryLock;
	protected GameState gs;
	public DB(ReentrantLock queryLock, GameState gamestate)
	{
		this.queryLock = queryLock;
		this.gs=gamestate;
	}
	@Override
	public void run() {
		queryLock.lock();
		System.out.println("Executing... ");
		execute();
		System.out.println("Done");
		queryLock.unlock();
	}
	
	public void execute()
	{
		Connection connection=null;
		Statement statement=null;
		try {
			Class.forName(DRIVER);
			connection=DriverManager.getConnection(ADDRESS+NAME, USER, PASSWORD);
			statement=connection.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

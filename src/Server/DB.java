package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import Client.GameState;

public abstract class DB implements Runnable
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
	
	public abstract void execute();
}

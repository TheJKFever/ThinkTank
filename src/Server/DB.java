package Server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.concurrent.locks.ReentrantLock;

public class DB implements Runnable
{
	public static final String ADDRESS = "jdbc:mysql://localhost/";
	public static final String NAME = "lab11";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "root";
	protected ReentrantLock queryLock;
	public DB(ReentrantLock queryLock)
	{
		this.queryLock = queryLock;
	}
	@Override
	public void run() {
		System.out.println("Executing... ");
		execute();
		System.out.println("Done");
	}
	
	public void execute() {
		// TODO: 
	};
}

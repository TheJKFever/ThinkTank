package Server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.concurrent.locks.ReentrantLock;

public class DB implements Runnable
{
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

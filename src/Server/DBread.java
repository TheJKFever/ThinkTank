package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import Client.GameState;
import Client.Player;

public class DBread extends DB {

	public DBread(ReentrantLock queryLock, GameState gamestate) {
		super(queryLock, gamestate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName(DRIVER);
			connection=DriverManager.getConnection(ADDRESS+NAME, USER, PASSWORD);
			statement=connection.createStatement();
			
			/*
			 * Columns in the database: team, player, kills, death, hit, shot
			 */
			resultSet=statement.executeQuery("SELECT team, player, kills, death, hit, shot FROM DB");
			while (resultSet.next())
			{
				int teamNumber=Integer.parseInt(resultSet.getString("team"));
				int playerNumber=Integer.parseInt(resultSet.getString("player"));
				int kills=Integer.parseInt(resultSet.getString("kills"));
				int death=Integer.parseInt(resultSet.getString("death"));
				int hit=Integer.parseInt(resultSet.getString("hit"));
				int shot=Integer.parseInt(resultSet.getString("shot"));
				if (teamNumber==1)
				{
					for (Player player:gs.team1.players)
					{
						if (playerNumber==player.playerNumber)
						{
							player.enemyKilled=kills;
							player.death=death;
							player.numHit=hit;
							player.numShot=shot;
						}
					}
				}
				else if (teamNumber==2)
				{
					for (Player player:gs.team2.players)
					{
						if (playerNumber==player.playerNumber)
						{
							player.enemyKilled=kills;
							player.death=death;
							player.numHit=hit;
							player.numShot=shot;
						}
					}
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import Client.GameState;
import Client.Player;

public class DBwrite extends DB {

	public DBwrite(ReentrantLock queryLock, GameState gamestate) {
		super(queryLock, gamestate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try {
			Class.forName(DRIVER);
			connection=DriverManager.getConnection(ADDRESS+NAME, USER, PASSWORD);

			for (Player player:gs.team1.players)
			{
				String command="UPDATE DB "+
						"SET kills = ?, "+
						" death = ?, "+
						" hit = ?, "+
						" shot = ? "+
						"WHERE team = '1' "+
						"AND player = "+player.playerNumber;
				preparedStatement=connection.prepareStatement(command);
				preparedStatement.setString(1, String.valueOf(player.enemyKilled));
				preparedStatement.setString(2, String.valueOf(player.death));
				preparedStatement.setString(3, String.valueOf(player.numHit));
				preparedStatement.setString(4, String.valueOf(player.numShot));
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

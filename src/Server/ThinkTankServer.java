package Server;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Exceptions.PortNotAvailalbeException;
import Helper.Helper;

public class ThinkTankServer extends Server {

	private static final int MAX_CAPACITY = 64; // per server
	private static final int MAX_GAMES = 5; // per server
	private static final int[] PORTS = {2300, 2301, 2302, 2303, 2304};
	private Map<Integer, TankGame> games;
	private Logger logger;
	private Connection db;
	private Semaphore capacity;
	private JSONParser JSON = new JSONParser();
	
	/* Create a server on port <port> that will listen for incoming requests */
	public ThinkTankServer(int port) throws IOException {
		super(port);
		capacity = new Semaphore(MAX_CAPACITY);
		games = new HashMap<Integer, TankGame>();
		try {
			Class.forName(DB.DRIVER);
			db = DriverManager.getConnection(DB.ADDRESS + DB.NAME, DB.USER, DB.PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger = Logger.getLogger("Server.log");
	}
	
	public int newGame() throws PortNotAvailalbeException {
		if (games.size()<MAX_GAMES) {
			logger.log(Level.SEVERE, "Cannot start new game. server has reached max game capacity: " + MAX_GAMES);
			return -1;
		}
		for (int port:PORTS) {
			try {
				newGame(port);
				return port;
			} catch (PortNotAvailalbeException e) {}
		}
		logger.log(Level.SEVERE, "Cannot start new game. All available ports are taken by server");
		throw new PortNotAvailalbeException("Cannot start new game. All available ports are taken by server");
	}
	
	public void newGame(int port) throws PortNotAvailalbeException {
		if (games.containsKey(port)) {
			logger.log(Level.SEVERE, "Game already running on port: " + port);
			throw new PortNotAvailalbeException("Game already running on port: " + port);
		}
		TankGame game = new TankGame(port);
		games.put(port, game);
	}


	@Override
	public void received(ServerThread client, String data) {
/*		Format of JSON data
 * 		{
 *		  "type": "type of message",
 *		  "data": "this varies based off type",
 *		  "result": true <- if boolean result
 * 		}
 */
// TODO Parse all possible messages
		try {
			JSONObject jsonData = (JSONObject)JSON.parse(data);
			String type = (String)jsonData.get("type");
			switch(type) {
			// consider making this {"type": "command", "data": "new game"...
			// instead of {"type": "new game", ...
				case "new game": 
				int portOfNewGame;
				try {
					portOfNewGame = newGame();
					client.send(Helper.Jsonify("response", portOfNewGame, true));
				} catch (PortNotAvailalbeException pnae) {
					client.send(Helper.Jsonify("response", pnae.getMessage(), false));
				}
				default:
					logger.log(Level.INFO, "Parse error. did not understand message: " + data);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public boolean validate(Socket connection) {
		if (!capacity.tryAcquire()) { // give a permit to each client up to max_capacity
			logger.log(Level.SEVERE, "Server has reaced max capacity: " + MAX_CAPACITY);
			return false;
//				throw new ServerAtMaxCapacityException("Server has reach ed max capacity: " + MAX_CAPACITY);
		}
		return true;
		// TODO: make sure to release the permit when the client signs off			
	}
}


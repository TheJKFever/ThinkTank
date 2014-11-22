package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Client.Globals;
import Exceptions.PortNotAvailalbeException;
import Helper.Helper;

public class ThinkTankCentralServer extends ServerSocket {
	private final int MAX_CAPACITY = 64; // per server
	private final int MAX_GAMES = 5; // per server
	private final int[] PORTS = {2300, 2301, 2302, 2303, 2304};
	private Map<Integer, ThinkTankGameServer> games;
	private Vector<ServerThread> clients;
	private Logger logger;
	private Connection db; // TODO: connect to DB for stats
	private Semaphore capacity;
	
	/* Create a server on port <port> that will listen for incoming requests */
	public ThinkTankCentralServer(int port) throws IOException {
		super(port);
		clients = new Vector<ServerThread>();
		capacity = new Semaphore(MAX_CAPACITY);
		games = new HashMap<Integer, ThinkTankGameServer>();
		try { // Database Connection
			Class.forName(Globals.Development.DB.DRIVER);
			db = DriverManager.getConnection(Globals.Development.DB.ADDRESS + Globals.Development.DB.NAME, Globals.Development.DB.USER, Globals.Development.DB.PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger = Logger.getLogger("Server.log");
		listenForConnections();
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
		ThinkTankGameServer game;
		try {
			game = new ThinkTankGameServer(port);
			games.put(port, game);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean validate(Socket connection) {
		if (!capacity.tryAcquire()) { // give a permit to each client up to max_capacity
			logger.log(Level.SEVERE, "Server has reaced max capacity: " + MAX_CAPACITY);
			return false;
			// throw new ServerAtMaxCapacityException("Server has reach ed max capacity: " + MAX_CAPACITY);
		}
		return true;
		// TODO: make sure to release the permit when the client signs off			
	}

	public void listenForConnections() {
		while(!this.isClosed()) {
			// Listen for clients signing onto server
			try {
				Socket connection = this.accept();
				if (validate(connection)) {
					clients.addElement(new CentralServerThread(connection));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public class CentralServerThread extends ServerThread {
		public CentralServerThread(Socket connection) {
			super(connection);
		}
		
		public void send(String data) {
			out.println(data);
			out.flush();
		}
		
		public void received(String data) {
	// TODO Parse all possible messages
			JSONObject jsonData = Helper.parse(data);
			String type = (String)jsonData.get("type");
			switch(type) {
			// consider making this {"type": "command", "data": "new game"...
			// instead of {"type": "new game", ...
				case "new game": 
				int portOfNewGame;
				try {
					portOfNewGame = newGame();
					send(Helper.Jsonify("response", portOfNewGame, true));
				} catch (PortNotAvailalbeException pnae) {
					send(Helper.Jsonify("response", pnae.getMessage(), false));
				}
				default:
					logger.log(Level.INFO, "Parse error. did not understand message: " + data);
			}
		}

		public void listen() {
			// Listen for messages from client
			String dataFromClient;
			try {
				while ((dataFromClient = in.readLine()) != null) {
					received(dataFromClient);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import Entities.GameObject;
import Entities.ProfileObject;
import Exceptions.PortNotAvailableException;
import Game.Event;
import Game.GameState;
import Game.Helper;
import Global.Settings;
import Server.DB.UserAlreadyExistsException;

public class CentralServer extends ServerSocket {
	private static Logger logger = Logger.getLogger("CentralServer.log");
	private final int MAX_CAPACITY = 64; // per server
	private final int MAX_GAMES = 5; // per server
	private final int[] PORTS = {2300, 2301, 2302, 2303, 2304};
	public Map<Integer, GameServer> games;
	private Vector<CentralServerConnectionToClient> clients;
	private DB db;
	private Semaphore capacity;
	
	/* Create a server on port <port> that will listen for incoming requests */
	public CentralServer(int port) throws IOException {
		super(port);
		
		clients = new Vector<CentralServerConnectionToClient>();
		capacity = new Semaphore(MAX_CAPACITY);
		games = new HashMap<Integer, GameServer>();
		db = new DB(new ReentrantLock());
		listenForConnections();
	}
	
	public int newGame(String name) throws PortNotAvailableException {
		Helper.log("IN CENRTAL SERVER NEWGAME()");
		if (games.size() >= MAX_GAMES) {
			Helper.log("MAX CAPACITY REACHED");
			logger.log(Level.SEVERE, "Cannot start new game. server has reached max game capacity: " + MAX_GAMES);
			return -1;
		}
		for (int port: PORTS) {
			try {
				Helper.log("TRYING PORT: " + port);
				newGame(name, port);
				return port;
			} catch (PortNotAvailableException e) {
				Helper.log("THIS PORT NOT AVAIALABLE: " + port);
				e.printStackTrace();
			}
		}
		System.out.println("ALL PORTS TAKEN, COULD NOT START GAME");
		logger.log(Level.SEVERE, "Cannot start new game. All available ports are taken by server");
		throw new PortNotAvailableException("Cannot start new game. All available ports are taken by server");
	}
	
	public void newGame(String name, int port) throws PortNotAvailableException {
		System.out.println("IN CENTRAL SERVER NEWGAME(" + port + ")");
		if (games.containsKey(port)) {
			System.out.println("GAMES ALREADY USING PORT: " + port);
			logger.log(Level.SEVERE, "Game already running on port: " + port);
			throw new PortNotAvailableException("Game already running on port: " + port);
		}
		GameServer gameServer;
		try {
			Helper.log("ABOUT TO CREATE GAME SERVER");
			gameServer = new GameServer(this, name, port);
			Helper.log("CREATED GAME SERVER");
			gameServer.thread.start();
			Helper.log("STARTED GAME SERVER THREAD");
			games.put(port, gameServer);
			Helper.log("ADDED TO GAMESERVER TO GAMES MAP");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean validate(Socket connection) {
		if (!capacity.tryAcquire()) { // give a permit to each client up to max_capacity
			logger.log(Level.SEVERE, "Server has reached max capacity: " + MAX_CAPACITY);
			return false;
			// throw new ServerAtMaxCapacityException("Server has reach ed max capacity: " + MAX_CAPACITY);
		}
		return true;
	}
	
	public void release(CentralServerConnectionToClient thread) {
		System.out.println("releasing connection to client from central server");
		thread.interrupt();
		System.out.println("interupted thread");
		boolean removed = clients.remove(thread);
		System.out.println("removed thread from vector: " + removed);
		capacity.release();
	}

	public void listenForConnections() {
		System.out.println("Central Server: Listening for connections...");
		while(!this.isClosed()) {
			// Listen for clients signing onto server
			try {
				Socket socket = this.accept();
				if (validate(socket)) {
					CentralServerConnectionToClient csConnection = new CentralServerConnectionToClient(socket, this);
					clients.addElement(csConnection);
					csConnection.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public Vector<GameObject> getGamesVector() {
		Vector<GameObject> gameVector = new Vector<GameObject>();
		for(Map.Entry<Integer, GameServer> game:games.entrySet()) {
			gameVector.add(new GameObject(game.getValue().name, game.getKey(), game.getValue().clients));
		}
		return gameVector;
	}

	public void broadcast(Event event) {
		synchronized(clients) {
			for (CentralServerConnectionToClient client:clients) {
				client.send(event);
			}
		}
	}

	public boolean newProfile(ProfileObject profile) throws UserAlreadyExistsException {
		return db.insertProfile(profile);
	}

	public boolean login(ProfileObject profile) throws Exception {
		return db.attemptLogin(profile);
	}
	
	public void saveStats(GameState gameState) {
		db.saveStats(gameState);
	}
	
	public StatsObject getStatsFor(String username) {
		return db.getStatsFor(username);
	}

	public static void main(String[] args) {
		try {
			CentralServer server = new CentralServer(Settings.Development.SERVER_PORT);
			System.out.println("CENTRAL SERVER IS RUNNING");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


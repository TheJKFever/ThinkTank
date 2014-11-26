package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import Exceptions.PortNotAvailableException;
import Game.Event;
import Game.Globals;

public class CentralServer extends ServerSocket {
	private static Logger logger = Logger.getLogger("CentralServer.log");
	private final int MAX_CAPACITY = 64; // per server
	private final int MAX_GAMES = 5; // per server
	private final int[] PORTS = {2300, 2301, 2302, 2303, 2304};
	public Map<Integer, GameServer> games;
	private Vector<CentralServerConnectionToClient> clients;
	private Connection db; // TODO: connect to DB for stats
	private Semaphore capacity;
	
	/* Create a server on port <port> that will listen for incoming requests */
	public CentralServer(int port) throws IOException {
		super(port);
		
		clients = new Vector<CentralServerConnectionToClient>();
		capacity = new Semaphore(MAX_CAPACITY);
		games = new HashMap<Integer, GameServer>();
		
		// Database Connection
		try { 
			Class.forName(Globals.Development.DB.DRIVER);
			// TODO: setup database on production server
//			db = DriverManager.getConnection(
//				Globals.Development.DB.ADDRESS + Globals.Development.DB.NAME, 
//				Globals.Development.DB.USER, 
//				Globals.Development.DB.PASSWORD
//			);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		listenForConnections();
	}
	
	public int newGame() throws PortNotAvailableException {
		if (Globals.DEBUG) System.out.println("IN CENRTAL SERVER NEWGAME()");
		if (games.size() >= MAX_GAMES) {
			if (Globals.DEBUG) System.out.println("MAX CAPACITY REACHED");
			logger.log(Level.SEVERE, "Cannot start new game. server has reached max game capacity: " + MAX_GAMES);
			return -1;
		}
		for (int port: PORTS) {
			try {
				if (Globals.DEBUG) System.out.println("TRYING PORT: " + port);
				newGame(port);
				return port;
			} catch (PortNotAvailableException e) {
				if (Globals.DEBUG) System.out.println("THIS PORT NOT AVAIALABLE: " + port);
				e.printStackTrace();
			}
		}
		System.out.println("ALL PORTS TAKEN, COULD NOT START GAME");
		logger.log(Level.SEVERE, "Cannot start new game. All available ports are taken by server");
		throw new PortNotAvailableException("Cannot start new game. All available ports are taken by server");
	}
	
	public void newGame(int port) throws PortNotAvailableException {
		System.out.println("IN CENTRAL SERVER NEWGAME(INT)");
		if (games.containsKey(port)) {
			System.out.println("GAMES ALREADY USING PORT: " + port);
			logger.log(Level.SEVERE, "Game already running on port: " + port);
			throw new PortNotAvailableException("Game already running on port: " + port);
		}
		GameServer gameServer;
		try {
			if (Globals.DEBUG) System.out.println("ABOUT TO CREATE GAME SERVER");
			gameServer = new GameServer(port);
			if (Globals.DEBUG) System.out.println("CREATED GAME SERVER");
			gameServer.thread.start();
			if (Globals.DEBUG) System.out.println("STARTED GAME SERVER THREAD");
			games.put(port, gameServer);
			if (Globals.DEBUG) System.out.println("ADDED TO GAMESERVER TO GAMES MAP");
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
		// TODO: make sure to release the permit when the client signs off
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
	
	public static void main(String[] args) {
		try {
			CentralServer server = new CentralServer(Globals.Development.SERVER_PORT);
			System.out.println("CENTRAL SERVER IS RUNNING");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


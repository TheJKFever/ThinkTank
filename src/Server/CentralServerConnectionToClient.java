package Server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Entities.ProfileObject;
import Exceptions.PortNotAvailableException;
import Game.Event;
import Server.DB.UserAlreadyExistsException;

public class CentralServerConnectionToClient extends ConnectionToClient {
	CentralServer centralServer;
	private static Logger logger = Logger.getLogger("CentralServer.log");
	
	public CentralServerConnectionToClient(Socket connection, CentralServer cs) {
		super(connection);
		this.centralServer = cs;
	}
		
	@Override
	public void receive(Object obj) {
		Event event = (Event)obj;
		int port;
		ProfileObject profile;
		switch(event.type) {
			case "new game":
				System.out.println("ATTEMPTING TO CREATE NEW GAME");
				try {
					port = centralServer.newGame((String)event.data);
					System.out.println("GAME CREATED, SENDING INFO TO CLIENT");
					sendEvent(new Event("new game", port));
					centralServer.broadcast(new Event("games info", centralServer.getGamesVector()));
					System.out.println("NEW GAME CREATED SUCCESSFULLY");
				} catch (PortNotAvailableException pnae) {
					System.out.println("FAILED TO CREATE NEW GAME");
					sendEvent(new Event("new game", "No ports available", false));
				}
				break;
			case "join game":
				List<Integer> activePorts = new ArrayList<Integer>(centralServer.games.keySet());
				System.out.println("PRINTING ACTIVE PORTS");
				for (Integer i: activePorts) {
					System.out.println("ACTIVE PORT = " + i);
				}
				port = activePorts.get(0);
				sendEvent(new Event("join game", port));
				System.out.println("TOLD CLIENT TO JOIN GAME ON PORT " + port);
				break;
			case "update games":
				this.sendEvent(new Event("games info", centralServer.getGamesVector()));
				break;
			case "create profile":
				profile = (ProfileObject)event.data;
				try {
					this.sendEvent(new Event("new profile", profile, centralServer.newProfile(profile)));
				} catch (UserAlreadyExistsException e) {
					this.sendEvent(new Event("new profile", e.getMessage(), false));
				}
				break;
			case "login":
				profile = (ProfileObject) event.data;
				try {
					boolean response = centralServer.login(profile);
					this.sendEvent(new Event("login", profile, response));
				} catch (Exception e) {
					e.printStackTrace();
					this.sendEvent(new Event("login", e.getMessage(), false));
				}
				break;
			case "logout":
				// TODO: Handle login
				break;
			case "get stats":
				try {
					DB.StatsObject stats = this.centralServer.getStatsFor((String)event.data);
					sendEvent(new Event("stats", stats, true));
				} catch (Exception e) {
					e.printStackTrace();
					sendEvent(new Event("stats", e.getMessage(), false));
				}
				break;
			default:
				logger.log(Level.INFO, "Parse error. did not understand message: " + event);
		}
	}
	
	public void listen() {
		// Listen for messages from client
		Object dataFromClient;
		try {
			while ((dataFromClient = in.readObject()) != null) {
				receive(dataFromClient);
			}
		} catch (EOFException|SocketException e) {
			centralServer.release(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}


package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Exceptions.PortNotAvailableException;
import Game.Event;

public class CentralServerConnectionToClient extends ServerThread {
	CentralServer centralServer;
	private static Logger logger = Logger.getLogger("CentralServer.log");
	
	public CentralServerConnectionToClient(Socket connection, CentralServer cs) {
		super(connection);
		this.centralServer = cs;
	}
		
	public void send(Object obj) {
		try {
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@Override
	public void receive(Object obj) {
		Event event = (Event)obj;
		int port;
		switch(event.type) {
			case "new game":
				System.out.println("ATTEMPTING TO CREATE NEW GAME");
				try {
					port = centralServer.newGame();
					System.out.println("GAME CREATED, SENDING INFO TO CLIENT");
					sendEvent(new Event("new game", port));
					System.out.println("NEW GAME CREATED SUCCESSFULLY");
				} catch (PortNotAvailableException pnae) {
					System.out.println("FAILED TO CREATE NEW GAME");
					sendEvent(new Event("new game", -1));
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

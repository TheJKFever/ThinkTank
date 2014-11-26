package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import Game.Event;
import Game.Player;
import Helper.Helper;

public abstract class ConnectionToServer extends Socket implements Runnable {
	public ObjectInputStream in;
	public ObjectOutputStream out;
	private Logger logger;
	
	public ConnectionToServer(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		in = new ObjectInputStream(getInputStream());
		out = new ObjectOutputStream(getOutputStream());
		logger = Logger.getLogger("Client");
	}
	
	public abstract void receive(Object obj);
	
	public void send(Object obj) {
		try {
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendEvent(Event event) {
		send(event.serialize());
	}
	
	private void listen() {
		// Listen for messages from server
		Object dataFromServer;
		try {
			while ((dataFromServer = in.readObject()) != null) {
				receive(dataFromServer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		listen();
	}
}

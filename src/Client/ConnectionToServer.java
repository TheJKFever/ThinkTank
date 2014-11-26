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

public class ConnectionToServer extends Socket implements Runnable {
	public ObjectInputStream in;
	public ObjectOutputStream out;
	public ThinkTankGUI gui;
	private Logger logger;
	
	public ConnectionToServer(ThinkTankGUI gui, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gui = gui;
		in = new ObjectInputStream(getInputStream());
		out = new ObjectOutputStream(getOutputStream());
		logger = Logger.getLogger("Client");
	}
	
	public void receive(Object obj) {
		// TODO Parse all possible messages
		Event event = Event.deserialize(obj);
		switch(event.type) {
			case "game update":
				gui.clientEngine.serverEventQ.add(jsonData);
			case "chat":
//				gui.chatPanel.
			case "assign player":
				gui.clientEngine.player = (Player)event.data;
			default:
				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}
	}
	
	public void send(Object obj) {
		try {
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendEvent(Event event) {
		send(event.Jsonify());
	}
	
	private void listen() {
		// Listen for messages from server
		String dataFromServer;
		try {
			while ((dataFromServer = in.readLine()) != null) {
				receive(dataFromServer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		listen();
	}
}

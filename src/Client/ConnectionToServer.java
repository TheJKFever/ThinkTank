package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import Helper.Helper;

public class ConnectionToServer extends Socket implements Runnable {
	public BufferedReader in;
	public PrintWriter out;
	public ThinkTankGUI gui;
	private Logger logger;
	
	public ConnectionToServer(ThinkTankGUI gui, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.gui = gui;
		in = new BufferedReader(new InputStreamReader(getInputStream()));
		out = new PrintWriter(getOutputStream());
		logger = Logger.getLogger("Client");
	}
	
	public void received(String data) {
		// TODO Parse all possible messages
		JSONObject jsonData = Helper.parse(data);
		String type = (String)jsonData.get("type");
		switch(type) {
			case "game update":
			gui.gameEngine.serverEventQ.add(jsonData);
			case "chat":
//				gui.chatPanel.
			default:
				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}
	}
	
	public void send(String data) {
		out.println(data);
		out.flush();
	}
	
	private void listen() {
		// Listen for messages from server
		String dataFromServer;
		try {
			while ((dataFromServer = in.readLine()) != null) {
				received(dataFromServer);
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

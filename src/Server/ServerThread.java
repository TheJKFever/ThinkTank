package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Game.Event;

public abstract class ServerThread extends Thread {
	private Heartbeat hb;
	public ObjectInputStream in;
	public ObjectOutputStream out;	
	
	public ServerThread(Socket connection) {
		try {
			in = new ObjectInputStream(connection.getInputStream());
			out = new ObjectOutputStream(connection.getOutputStream());
//			hb = new Heartbeat(connection);
//			hb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String data) {
		try {
			out.writeObject(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendEvent(Event event) {
		send(event.Jsonify());
	}
	
	public abstract void processIncomingData(String data);
	
	public abstract void listen();
	
	public void run() {
		listen();
	}
}

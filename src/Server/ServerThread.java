package Server;

import java.io.IOException;
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
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
//			hb = new Heartbeat(connection);
//			hb.start();
		} catch (IOException e) {
			e.printStackTrace();
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
		send(event);
	}
	
	public abstract void receive(Object data);
	
	public abstract void listen();
	
	public void run() {
		listen();
	}
}

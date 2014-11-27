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
		System.out.println("SERVERTHREAD: IN CONSTRUCTOR");
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
			out.reset();
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR SENDING DATA TO CLIENT");
			this.interrupt();
			e.printStackTrace();
		}
	}

	public void sendEvent(Event event) {
		System.out.println("ServerThread: Sending event co client:");
		System.out.println(event);
		send(event);
	}

	public abstract void receive(Object data);

	public abstract void listen();

	public void run() {
		listen();
	}
}

package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Game.Event;
import Game.Helper;

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
		Helper.log("Created new ServerThread");
	}

	public void send(Object obj) {
		try {
			out.reset();
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			Helper.log("ERROR SENDING DATA TO CLIENT");
			this.interrupt();
			e.printStackTrace();
		}
	}

	public void sendEvent(Event event) {
		// Helper.log("ServerThread: Sending event co client:");
		// Helper.log(event);
		send(event);
	}

	public abstract void receive(Object data);

	public abstract void listen();

	public void run() {
		listen();
	}
}

package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Game.Event;

public abstract class ConnectionToServer extends Socket implements Runnable {
	public ObjectInputStream in;
	public ObjectOutputStream out;
	
	public ConnectionToServer(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		out = new ObjectOutputStream(getOutputStream());
		in = new ObjectInputStream(getInputStream());
	}
	
	public abstract void receive(Object obj);
	
	private void send(Object obj) {
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

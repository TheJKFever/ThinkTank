package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Game.Event;
import Global.Settings;

public abstract class ConnectionToServer extends Socket implements Runnable {
	public ObjectInputStream in;
	public ObjectOutputStream out;
	
	public ConnectionToServer(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		if (Settings.DEBUG) System.out.println("CONNECTIONTOSERVER: CONSTRUCTOR");
		out = new ObjectOutputStream(getOutputStream());
		in = new ObjectInputStream(getInputStream());
		if (Settings.DEBUG) System.out.println("CONNECTIONTOSERVER: GOT INPUT AND OUTPUT STREAMS");
	}
	
	public abstract void receive(Object obj);
	
	private void send(Object obj) {
		try {
			out.reset();
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendEvent(Event event) {
		System.out.println("SENDING EVENT:\n" + event);
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
		System.out.println("CONNECTIONTOSERVER: RUN()");
		listen();
	}
}

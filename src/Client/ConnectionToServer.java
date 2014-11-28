package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Game.Event;
import Game.Globals;

public abstract class ConnectionToServer extends Socket implements Runnable {
	public ObjectInputStream in;
	public ObjectOutputStream out;
	
	public ConnectionToServer(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		out = new ObjectOutputStream(getOutputStream());
		in = new ObjectInputStream(getInputStream());
		if (Globals.DEBUG) System.out.println("CONNECTIONTOSERVER: CONNECTION SUCCESSFUL");
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
		// System.out.println("SENDING EVENT:\n" + event);
		send(event);
	}
	
	private void listen() {
		try {
		// Listen for messages from server
		Object dataFromServer;
			while (true) {
				if (this.isConnected()) {
						if ((dataFromServer = in.readObject()) != null) {
							receive(dataFromServer);
						}
				} else {
					this.close();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		listen();
	}
}

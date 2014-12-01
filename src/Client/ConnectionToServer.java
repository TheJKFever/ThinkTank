package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import Game.Event;
import Game.Helper;

public abstract class ConnectionToServer extends Socket implements Runnable {
	public ObjectInputStream in;
	public ObjectOutputStream out;
	
	public ConnectionToServer(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		Helper.log("CONNECTIONTOSERVER: CONSTRUCTOR");
		out = new ObjectOutputStream(getOutputStream());
		in = new ObjectInputStream(getInputStream());
		Helper.log("CONNECTIONTOSERVER: GOT INPUT AND OUTPUT STREAMS");
	}
	
	public abstract void receive(Object obj);
	
	// go to GameServerConnectionToClient
	private void send(Object obj) {
		try {
			out.writeObject(obj);
			out.reset();
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
		} catch (EOFException|SocketException e) {
			try {
				this.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
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

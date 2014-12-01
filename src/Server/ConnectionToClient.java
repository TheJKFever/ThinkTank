package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import Game.Event;
import Game.Helper;

public abstract class ConnectionToClient extends Thread {
	
	public ObjectInputStream in;
	public ObjectOutputStream out;	

	public ConnectionToClient(Socket connection) {
		System.out.println("ConnectionToClient: IN CONSTRUCTOR");
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
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
		} catch (SocketException se) {
			try {				
				in.close();
				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException ioe) {
			Helper.log("ERROR SENDING DATA TO CLIENT");
			this.interrupt();
			ioe.printStackTrace();
		}
	}

	public void sendEvent(Event event) {
//		 Helper.log("ServerThread: Sending event to client:");
//		 Helper.log(event);
		send(event);
	}

	public abstract void receive(Object data);

	public abstract void listen();

	public void run() {
		listen();
	}
}

package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ServerThread extends Thread {
	private Heartbeat hb;
	public BufferedReader in;
	public PrintWriter out;	
	
	public ServerThread(Socket connection) {
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream());
			hb = new Heartbeat(connection);
			hb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String data) {
		out.println(data);
		out.flush();
	}
	
	public abstract void processIncomingData(String data);
	
	public abstract void listen();
	
	public void run() {
		listen();
	}
}

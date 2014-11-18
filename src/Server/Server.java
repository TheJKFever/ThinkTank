package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.json.simple.JSONObject;

public abstract class Server extends ServerSocket {
	protected Vector<ServerThread> connections;

	public Server(int port) throws IOException {
		super(port);
		connections = new Vector<ServerThread>();
		while (!this.isClosed()) {
			listenForConnections();
		}
	}

	public abstract void received(ServerThread from, String data);

	private void listenForConnections() {
		while(!this.isClosed()) {
			// Listen for clients signing onto server
			try {
				Socket connection = this.accept();
				if (validate(connection)) {
					connections.addElement(new ServerThread(connection));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	public boolean validate(Socket connection) {
		return true;
	}

	public class ServerThread extends Thread {
		public Server server;
		public Socket connection;
		public BufferedReader in;
		public PrintWriter out;

		public ServerThread(Socket connection) {
			this.connection = connection;
			try {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				out = new PrintWriter(connection.getOutputStream());
				new Heartbeat(connection);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send(JSONObject data) {
			send(data.toJSONString());
		}

		public void send(String data) {
			out.println(data);
			out.flush();
		}

		public void run() {
			// Listen for messages from client
			String fromClient;
			try {
				while ((fromClient = in.readLine()) != null) {
					received(this, fromClient);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class Heartbeat extends Thread{
		private Socket connection;
		private PrintWriter out;
		
		public Heartbeat(Socket connection){
			this.connection = connection;
			try {
				out = new PrintWriter(connection.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run(){
			while(true) {
				try {
					sleep(10000);
					out.println("heartbeat");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}            
		}
	}
}


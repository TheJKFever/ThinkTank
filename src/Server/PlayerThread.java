package Server;

import java.net.Socket;

public class PlayerThread extends Thread {
	
	public Socket client;
	private int team;
	
	public PlayerThread(Socket client, int team) {
		this.client = client;
		this.team = team;
	}
}

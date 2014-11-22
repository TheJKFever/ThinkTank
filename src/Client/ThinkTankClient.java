package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThinkTankClient extends Socket implements Runnable {
	public BufferedReader in;
	public PrintWriter out;
	public ThinkTank game;
	
	public ThinkTankClient(ThinkTank game, String host, int port) throws UnknownHostException, IOException {
		super(host, port);
		this.game = game;
		in = new BufferedReader(new InputStreamReader(getInputStream()));
		out = new PrintWriter(getOutputStream());
	}
	
	private void received(String data) {
		
	}
	
	private void send(String data) {
		
	}
	
	@Override
	public void run() {
		
	}
}

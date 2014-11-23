package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatThread extends Thread {

	private Socket s;
	private Server server;
	private PrintWriter pw;
	int index;
	
	public ChatThread(Socket s, Server server, int index) {
		this.s = s;
		this.server = server;
		this.index = index;
		try {
			this.pw = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void run() {
		while (true) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String line = br.readLine();
				if(line==null)
					break;
				server.sendMessage(line, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

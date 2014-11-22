package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Heartbeat extends Thread{
	private PrintWriter out;

	public Heartbeat(Socket connection){
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

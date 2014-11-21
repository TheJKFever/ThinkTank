package Client.Old;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class OldThinkTankClient extends Thread {

	Socket server;
	InputStream incoming;
	OutputStream outgoing;

	/* Creates a connection to the server at <host>:<port> */
	public OldThinkTankClient(String host, int port) {
		try {
			server = new Socket(host, port);
			incoming = server.getInputStream();
			outgoing = server.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(server.isConnected()) {
			// Do something here
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

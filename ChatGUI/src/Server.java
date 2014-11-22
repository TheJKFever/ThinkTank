import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	
	Vector<ChatThread> chatVt = new Vector<ChatThread>();
	public Server(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			while(true) {
				Socket s = ss.accept();
				int index = chatVt.size();
				ChatThread ct = new ChatThread(s, this, index);
				ct.send("I:"+index);
				
				chatVt.add(ct);
				ct.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendMessage(String newMessage, ChatThread ct) {
		for(ChatThread thread: chatVt) {
			if(!thread.equals(ct)) {
				thread.send(newMessage);
			}
		}
	}
	
	public static void main(String[] args) {
		new Server(6789);
	}
}

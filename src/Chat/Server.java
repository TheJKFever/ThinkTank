package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import Game.Helper;
import Global.Settings;

public class Server {
	public ChatThread ct;
	public Vector<ChatThread> chatVt = new Vector<ChatThread>();

	public Server(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			while(true) {
				Socket s = ss.accept();
				int index = chatVt.size();
				Helper.log("Creating new ChatThread");
				ct = new ChatThread(s, this, index);
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
		new Server(Settings.CHAT_PORT);
	}
}

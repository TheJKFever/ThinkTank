package Entities;

import java.io.Serializable;
import java.util.Vector;

import Server.GameServerConnectionToClient;

public class Objects {

	public static class GameObject implements Serializable {
		private static final long serialVersionUID = 7712545999653446130L;
		public String name;
		public int port, team1=0, team2=0;
		public GameObject(String name, int port, Vector<GameServerConnectionToClient> clients) {
			this.name = name;
			this.port = port;
			for (GameServerConnectionToClient client:clients) {
				switch(client.player.team.num) {
				case 1:
					team1++;
					break;
				case 2:
					team2++;
					break;
				}
			}
		}
	}
	
	public static class ProfileObject implements Serializable {
		private static final long serialVersionUID = -7980714007376028515L;
		public String username, password;
		public ProfileObject(String username, String password) {
			this.username = username;
			this.password = password;
		}
	}

}

package Entities;

import java.io.Serializable;
import java.util.Vector;

import Server.GameServerConnectionToClient;

public class GameObject implements Serializable {
	private static final long serialVersionUID = -8115889288937642627L;
	public String name;
	public int port, team1=0, team2=0;
	public GameObject(String name, int port, Vector<GameServerConnectionToClient> clients) {
		this.name = name;
		this.port = port;
		synchronized(clients) {
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
}

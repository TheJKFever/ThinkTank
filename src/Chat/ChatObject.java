package Chat;

import java.io.Serializable;

import Game.Player;

public class ChatObject implements Serializable {
	private static final long serialVersionUID = -9029398938239009937L;
	public String to;
	public Player from;
	public String message;
	public ChatObject(String to, Player from, String message) {
		super();
		this.to = to;
		this.from = from;
		this.message = message;
	}
}

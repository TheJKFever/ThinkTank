package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Client.ThinkTankGUI;

public class JoinGameListener implements ActionListener {
	public ThinkTankGUI gui;
	public int port;
	public JoinGameListener(ThinkTankGUI gui, int port) {
		this.port = port;
		this.gui = gui;
	}
	public void actionPerformed(ActionEvent event) {
		gui.joinGame(port);
	}
}

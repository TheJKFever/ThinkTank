package Screens;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.ThinkTankGUI;
import Game.Event;
import Server.DB;
import Server.DB.StatsObject;

public class StatsScreen extends JPanel {
	
	private ThinkTankGUI gui;

	public StatsScreen(ThinkTankGUI gui) {
		this.gui = gui;
	}
	
	public void statsResponse(Event response) {
		if (response.result) {
			DB.StatsObject stats = (StatsObject) response.data;
			// TODO HERE
		} else {
			JOptionPane.showMessageDialog(null, response.data, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void getStatsFor(String username) {
		gui.centralConnection.sendEvent(new Event("get stats", username));
	}
	
}

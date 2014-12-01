package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Client.ThinkTankGUI;

public class GoToPageListener implements ActionListener {
	private ThinkTankGUI gui;
	private JPanel page;
	
	public GoToPageListener(ThinkTankGUI gui, JPanel page) {
		this.gui = gui;
		this.page = page;
	}
	public void actionPerformed(ActionEvent arg0) {
		gui.goTo(page);
	}

}

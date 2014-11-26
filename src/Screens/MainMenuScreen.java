package Screens;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import Client.ThinkTankGUI;

public class MainMenuScreen extends JPanel {
	public JButton startNewBtn, joinGameBtn, createProfileBtn, statsBtn, loginBtn, logoutBtn;
	private ThinkTankGUI gui;
	
	public MainMenuScreen(ThinkTankGUI gui) {
		this.gui = gui;
		
		startNewBtn = new JButton("Start New Game");
		joinGameBtn = new JButton("Join Game");
		createProfileBtn = new JButton("Create Profile");
		statsBtn = new JButton("View Stats");
		loginBtn = new JButton("Login");
		logoutBtn = new JButton("Logout");

		startNewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		joinGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		createProfileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		startNewBtn.addActionListener(new StartGameListener(gui));
		joinGameBtn.addActionListener(new JoinGameListener(gui));
		
		this.add(startNewBtn);
		this.add(joinGameBtn);
		if (!gui.loggedIn) {
			this.add(statsBtn);
			this.add(logoutBtn);
		} else {
			this.add(createProfileBtn);
			this.add(loginBtn);
		}
	}
	
	public class StartGameListener implements ActionListener {
		public ThinkTankGUI gui;
		public StartGameListener(ThinkTankGUI gui) {
			this.gui = gui;
		}
		public void actionPerformed(ActionEvent arg0) {
			gui.startNewGame();
		}
	}
	public class JoinGameListener implements ActionListener {
		public ThinkTankGUI gui;
		public JoinGameListener(ThinkTankGUI gui) {
			this.gui = gui;
		}
		public void actionPerformed(ActionEvent arg0) {
			gui.joinGame();
		}
	}
}

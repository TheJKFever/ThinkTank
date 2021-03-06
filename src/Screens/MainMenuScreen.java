package Screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Client.ThinkTankGUI;
import Entities.ProfileObject;
import Listeners.GoToPageListener;

public class MainMenuScreen extends JPanel {
	public JButton startNewBtn, joinGameBtn, createProfileBtn, statsBtn, loginBtn, logoutBtn;
	private ThinkTankGUI gui;
	
	public Image bgImg = new ImageIcon("images/splash.png").getImage();
	public MainMenuScreen(ThinkTankGUI gui) {
		this.gui = gui;
		
		this.setOpaque(false);
		
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
		
		Dimension d = startNewBtn.getPreferredSize();
		d.width = 500;
		joinGameBtn.setPreferredSize(d);
		createProfileBtn.setPreferredSize(d);
		statsBtn.setPreferredSize(d);
		loginBtn.setPreferredSize(d);
		logoutBtn.setPreferredSize(d);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		startNewBtn.addActionListener(new GoToPageListener(gui, ThinkTankGUI.CreateGamePage));
		joinGameBtn.addActionListener(new JoinGameListener(gui));
		createProfileBtn.addActionListener(new GoToPageListener(gui, ThinkTankGUI.CreateProfilePage));
		loginBtn.addActionListener(new GoToPageListener(gui, ThinkTankGUI.LoginPage));
		refresh();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(this.bgImg, 0, 0, null);
		super.paint(g);
//		Graphics2D g2d = (Graphics2D)g;
//		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
	}
	
	public void refresh() {
		this.removeAll();

		add(Box.createVerticalGlue());
		this.add(startNewBtn);
		add(Box.createVerticalStrut(20));
		this.add(joinGameBtn);
		add(Box.createVerticalStrut(20));
		if (gui.loggedIn) {
			statsBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					MainMenuScreen.this.gui.goTo(ThinkTankGUI.StatsPage);
				}				
			});
			this.add(statsBtn);
			add(Box.createVerticalStrut(20));
			this.add(logoutBtn);
			logoutBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainMenuScreen.this.gui.loggedIn = false;
					MainMenuScreen.this.gui.user = new ProfileObject("guest", null);
				}
			});
			add(Box.createVerticalGlue());
		} else {
			this.add(createProfileBtn);
			add(Box.createVerticalStrut(20));
			this.add(loginBtn);
			add(Box.createVerticalGlue());
		}
		this.repaint();
	}
	
	public class JoinGameListener implements ActionListener {
		public ThinkTankGUI gui;
		public JoinGameListener(ThinkTankGUI gui) {
			this.gui = gui;
		}
		public void actionPerformed(ActionEvent arg0) {
			gui.goTo(gui.LobbyPage);
		}
	}
}

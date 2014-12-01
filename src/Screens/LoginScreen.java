package Screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.ThinkTankGUI;
import Entities.ProfileObject;
import Game.Event;

public class LoginScreen extends JPanel {
	public ThinkTankGUI gui;
	private JLabel usernameLbl, passwordLbl;
	public JTextField usernameTf, passwordTf;
	private JButton loginBtn, cancelBtn;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JPanel usernameP, passwordP, buttonsP;
	private String nextPage = ThinkTankGUI.MainMenuPage;
	private Component verticalGlue;
	private Component verticalGlue_1;
	
	public LoginScreen(ThinkTankGUI gui) {
		this.gui = gui;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
						
		usernameP = new JPanel();
		add(usernameP);
		usernameP.setMaximumSize(new Dimension(32767, 50));
		
		horizontalGlue = Box.createHorizontalGlue();
		usernameP.add(horizontalGlue);
		
		
		usernameLbl = new JLabel("Username:");
		usernameP.add(usernameLbl);
		usernameTf = new JTextField();
		usernameTf.setPreferredSize(new Dimension(150, 20));
		usernameTf.setSize(new Dimension(26, 0));
		usernameP.add(usernameTf);
		
		horizontalGlue_1 = Box.createHorizontalGlue();
		usernameP.add(horizontalGlue_1);
		passwordP = new JPanel();
		add(passwordP);
		passwordP.setMaximumSize(new Dimension(32767, 50));
		
		passwordLbl = new JLabel("Password:");
		passwordP.add(passwordLbl);
		passwordTf = new JTextField();
		passwordTf.setPreferredSize(new Dimension(150, 20));
		passwordTf.setSize(new Dimension(26, 0));
		passwordP.add(passwordTf);
		buttonsP = new JPanel();
		add(buttonsP);
		buttonsP.setMaximumSize(new Dimension(32767, 5));
		
		loginBtn = new JButton("Login");
		buttonsP.add(loginBtn);
		
		loginBtn.addActionListener(new LoginListener());
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen.this.gui.goTo(ThinkTankGUI.MainMenuPage);
			}
		});
		buttonsP.add(cancelBtn);
		
		verticalGlue_1 = Box.createVerticalGlue();
		add(verticalGlue_1);
	}
	
	public void setNextPage(String page) {
		this.nextPage = page;
	}
	
	public void loginResponse(Event response) {
		System.out.println("received a login response: " + response.data);
		if (response.result) {
			// create profile success
			gui.user = (ProfileObject)response.data;
			gui.loggedIn = true;
			gui.mainMenu.refresh();
			gui.goTo(ThinkTankGUI.MainMenuPage);
			gui.stats.getStatsFor(gui.user.username);
		} else {
			JOptionPane.showMessageDialog(null, response.data, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			gui.centralConnection.sendEvent(new Event("login", new ProfileObject(usernameTf.getText(), passwordTf.getText())));
		}
	}
}

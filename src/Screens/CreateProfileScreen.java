package Screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.ThinkTankGUI;
import Entities.Objects.ProfileObject;
import Game.Event;

public class CreateProfileScreen extends JPanel {
	public ThinkTankGUI gui;
	private JLabel usernameLbl, passwordLbl;
	public JTextField usernameTf, passwordTf;
	private JButton createProfileBtn, cancelBtn;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JPanel usernameP, passwordP, buttonsP;
	private Component verticalGlue;
	private Component verticalGlue_1;
	private String nextPage;
	
	public CreateProfileScreen(ThinkTankGUI gui) {
		this.gui = gui;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		usernameP = new JPanel();
		passwordP = new JPanel();
		buttonsP = new JPanel();
		add(usernameP);
		add(passwordP);
		add(buttonsP);
		
		horizontalGlue = Box.createHorizontalGlue();
		usernameP.add(horizontalGlue);
		
		
		usernameLbl = new JLabel("Name game:");
		usernameP.add(usernameLbl);
		usernameTf = new JTextField();
		usernameTf.setPreferredSize(new Dimension(150, 20));
		usernameTf.setSize(new Dimension(26, 0));
		usernameP.add(usernameTf);

		passwordLbl = new JLabel("Name game:");
		passwordP.add(passwordLbl);
		passwordTf = new JTextField();
		passwordTf.setPreferredSize(new Dimension(150, 20));
		passwordTf.setSize(new Dimension(26, 0));
		passwordP.add(passwordTf);

		createProfileBtn = new JButton("Create Game");
		buttonsP.add(createProfileBtn);
		
		createProfileBtn.addActionListener(new CreateProfileListener());
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateProfileScreen.this.gui.goTo(CreateProfileScreen.this.gui.MainMenuPage);
			}
		});
		buttonsP.add(cancelBtn);
		
		horizontalGlue_1 = Box.createHorizontalGlue();
		usernameP.add(horizontalGlue_1);
		
		verticalGlue_1 = Box.createVerticalGlue();
		add(verticalGlue_1);
	}
	
	public void createProfileResponse(Event response) {
		if (response.result) {
			// create profile success
			gui.goTo(nextPage);
		} else {
			// create profile fail
		}
	}
	
	public class CreateProfileListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			gui.centralConnection.sendEvent(new Event("create profile", new ProfileObject(usernameTf.getText(), passwordTf.getText())));
		}
	}
}

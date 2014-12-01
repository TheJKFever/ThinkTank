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

public class CreateGameScreen extends JPanel {
	ThinkTankGUI gui;
	JLabel gameNameLbl;
	public JTextField gameNameTf;
	JButton createGameBtn, cancelBtn;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JPanel panel;
	private Component verticalGlue;
	private Component verticalGlue_1;
	private Component verticalGlue_2;
	private Component verticalGlue_3;
	
	public CreateGameScreen(ThinkTankGUI gui) {
		this.gui = gui;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		verticalGlue_3 = Box.createVerticalGlue();
		add(verticalGlue_3);
		
		panel = new JPanel();
		add(panel);
		
		horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		
		gameNameLbl = new JLabel("Name game:");
		panel.add(gameNameLbl);
		gameNameTf = new JTextField();
		gameNameTf.setPreferredSize(new Dimension(150, 20));
		gameNameTf.setSize(new Dimension(26, 0));
		panel.add(gameNameTf);
		createGameBtn = new JButton("Create Game");
		panel.add(createGameBtn);
		
		createGameBtn.addActionListener(new StartGameListener(gui, gameNameTf));
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGameScreen.this.gui.goTo(CreateGameScreen.this.gui.mainMenu);
			}
		});
		panel.add(cancelBtn);
		
		horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);
		
		verticalGlue_1 = Box.createVerticalGlue();
		add(verticalGlue_1);
		
		verticalGlue_2 = Box.createVerticalGlue();
		add(verticalGlue_2);
	}

	public class StartGameListener implements ActionListener {
		public ThinkTankGUI gui;
		public JTextField gameNameTf;
		public StartGameListener(ThinkTankGUI gui, JTextField gameNameTf) {
			this.gui = gui;
			this.gameNameTf = gameNameTf;
		}
		public void actionPerformed(ActionEvent arg0) {
			gui.startNewGame(gameNameTf.getText());
		}
	}
}

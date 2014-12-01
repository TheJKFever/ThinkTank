package Screens;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import Entities.Tank;
import Game.Player;
import Game.SimpleKeyEvent;
import Global.Settings;

public class UtilityBar extends JPanel {

	public Player player;
	GameScreen gameScreen;
	
//	public void setPlayer(GameScreen gameScreen, Player player) 
//		this.gameScreen = gameScreen;
//	}

	public Player getPlayer() {

		return this.gameScreen.engine.player;

	}

	public UtilityBar(GameScreen gameScreen) {
		
		this.gameScreen = gameScreen;
		

		ImageIcon image1 = new ImageIcon("images/button1.png");

		ImageIcon image2 = new ImageIcon("images/button2.png");

		ImageIcon image3 = new ImageIcon("images/button3.png");
		
		ImageIcon image4 = new ImageIcon("images/button4.png");

		ImageIcon image5 = new ImageIcon("images/button5.png");

		ImageIcon image6 = new ImageIcon("images/button6.png");
		
		ImageIcon image7 = new ImageIcon("images/button7.png");

		JButton button1 = new JButton(image1);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_1, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});

		JButton button2 = new JButton(image2);
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_2, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});
		
		JButton button3 = new JButton(image3);
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_3, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});
		
		JButton button4 = new JButton(image4);

		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_4, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});
		
		JButton button5 = new JButton(image5);

		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_5, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});
		
		JButton button6 = new JButton(image6);

		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_6, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});
		
		JButton button7 = new JButton(image7);

		button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleKeyEvent ke = new SimpleKeyEvent(KeyEvent.VK_7, KeyEvent.KEY_RELEASED);
				UtilityBar.this.gameScreen.sendKeyPressToClientAndServer(ke);
				UtilityBar.this.gameScreen.gamePanel.requestFocusInWindow();
			}
		});
		
		add(button1);
		add(button2);
		add(button3);
		add(button4);
		add(button5);
		add(button6);
		add(button7);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
	}

}
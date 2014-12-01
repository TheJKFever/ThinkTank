package Screens;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import Entities.Tank;
import Game.Player;
import Global.Settings;

public class UtilityBar extends JPanel {

	public Player player;

	public void setPlayer(Player player) {

		this.player = player;

	}

	public Player getPlayer() {

		return this.player;

	}

	public UtilityBar() {

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

				player.tank.updateWeapon(Settings.DOUBLE_WEAPON);
				


			}

		});

		

		JButton button2 = new JButton(image2);
		
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {


				player.tank.updateWeapon(Settings.TRIPLE_WEAPON);
				

			}

		});
		JButton button3 = new JButton(image3);
		
		button3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {


				player.tank.updateWeapon(Settings.SUPER_WEAPON);
				

			}

		});
		
		JButton button4 = new JButton(image4);

		button4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				player.team.brain.setHealth(1);
				


			}

		});
		
		JButton button5 = new JButton(image5);

		button5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				player.team.brain.setHealth(2);
				


			}

		});
		
		JButton button6 = new JButton(image6);

		button6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				player.team.brain.setHealth(3);
				


			}

		});
		
		JButton button7 = new JButton(image7);

		button7.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				player.team.brain.setHealth(4);
				


			}

		});

		JToolBar bar = new JToolBar();
		bar.add(button1);
		bar.add(button2);
		bar.add(button3);
		bar.add(button4);
		bar.add(button5);
		bar.add(button6);
		bar.add(button7);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.add(bar);
	}

}
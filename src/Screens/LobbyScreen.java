package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import Client.ThinkTankGUI;
import Entities.Objects.GameObject;
import Game.Event;
import Game.Helper;
import Listeners.GoToPageListener;
import Listeners.JoinGameListener;

public class LobbyScreen extends JPanel {
	private static final int FIRST_ROW = 90;
	private static final int HEADER_ROW = FIRST_ROW - 40;
	private static final int GAME_NAME_COLUMN=100, 
			TEAM_1_COLUMN=400, 
			TEAM_2_COLUMN=600,
			JOIN_BUTTON_COLUMN=740;
	private static final int HEIGHT_INSET = 30;
	public Vector<GameObject> games;
	public ThinkTankGUI gui;
	private JButton createGameBtn;
	
	public LobbyScreen(ThinkTankGUI gui) {
		super();
		games = new Vector<GameObject>();
		this.gui = gui;
		setLayout(null);

		createGameBtn = new JButton("Create Game");
		createGameBtn.setBounds(20, 700, 120, 25);
		createGameBtn.addActionListener(new GoToPageListener(gui, gui.createGame));
		this.add(createGameBtn);
//		RefreshGamesBtn = new JButton("Refresh");
//		RefreshGamesBtn.setBounds(120, 700, 120, 25);
//		RefreshGamesBtn.addActionListener(new GoToPageListener(gui, "createGame"));
//		this.add(RefreshGamesBtn);

		updateGames();
	}
	
	public void drawHeader(Graphics g) {
		g.drawString("NAME", GAME_NAME_COLUMN, HEADER_ROW);
		g.drawString("PLAYERS", TEAM_1_COLUMN + 90, HEADER_ROW - 30);
		g.drawString("TEAM 1", TEAM_1_COLUMN, HEADER_ROW);
		g.drawString("TEAM 2", TEAM_2_COLUMN, HEADER_ROW);
		g.drawLine(50, HEADER_ROW+5, 900, HEADER_ROW+5);
	}
	
	public void drawGames(Graphics g) {
		for (int i=0;i<games.size();i++) {
			drawGameAt(g, games.get(i), FIRST_ROW + (i * HEIGHT_INSET));
		}
	}
	
	private void drawGameAt(Graphics g, GameObject game, int row) {
		g.drawString(game.name, GAME_NAME_COLUMN, row);
		g.drawString(""+game.team1, TEAM_1_COLUMN+20, row);
		g.drawString(""+game.team2, TEAM_2_COLUMN+20, row);
		JButton joinBtn = new JButton("Join");
		joinBtn.addActionListener(new JoinGameListener(gui, game.port));
		joinBtn.setBounds(JOIN_BUTTON_COLUMN, row-20, 100, 25);
		this.add(joinBtn);
	}

	public void updateGames() {
		gui.centralConnection.sendEvent(new Event("update games"));
		// ask the server for an update
//		this.games = update from server
	}

	public void updateGames(Vector<GameObject> games) {
		this.games = games;
		Helper.log("repainting games, count: " + games.size());
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent (g);
		render(g);
		invalidate();
		revalidate();
	}
	
	private void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.PLAIN, 25));
		drawHeader(g);
		drawGames(g);
	}
}

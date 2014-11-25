package Client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Helper.Helper;
import Game.ClientEngine;
import Game.Globals;

public class ThinkTankGUI extends JFrame {
	private ConnectionToServer connection;
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel;
	//	private MainMenuScreen mainMenu;
	//	private StatsScreen stats;
	//	private CreateProfileScreen createProfile;
	//	private CreateGameScreen createGame;
	//	private WaitingScreen waiting;
	//	private JoinGameScreen joinGame;
	private ClientEngine clientEngine;
	//	private GameOverScreen gameOver;
	

	public ThinkTankGUI(String host, int port) {
		try {
			connection = new ConnectionToServer(this, host, port);
			
			// client needs to have a unique id
			// client needs to have a specific tank that it controls
			// tank needs to belong to specific client/player
			mainPanel = new JPanel();
			mainPanel.setLayout(cardLayout);
			add(mainPanel);

			// TODO: Create homepage button

			/* SCREENS:
			 * Main Menu
			 * Stats
			 * Create Profile
			 * Create Game
			 * Waiting
			 * Join Game
			 * Battle
			 * Game Over
			 */
//			 mainMenu = new MainMenuScreen(); 
//			 stats = new StatsScreen(); 
//			 createProfile = new CreateProfileScreen(); 
//			 createGame = new CreateGameScreen(); 
//			 waiting = new WaitingScreen(); 
//			 joinGame = new JoinGameScreen(); 
			clientEngine = new ClientEngine(); 
//			 gameOver = new GameOverScreen(); 

//			 mainPanel.add("mainMenu", mainMenu);
//			 mainPanel.add("stats", stats);
//			 mainPanel.add("createProfile", createProfile);
//			 mainPanel.add("createGame", createGame);
//			 mainPanel.add("waiting", waiting);
			mainPanel.add("gameScreen", clientEngine);
//			 mainPanel.add("joinGame", joinGame);
			
			
//			 mainPanel.add(gameOver);


			// TODO: put all  of these in action listeners
//			 cardLayout.show(mainPanel, "mainMenu");
//			 cardLayout.show(mainPanel, "stats");
//			 cardLayout.show(mainPanel, "createProfile");
//			 cardLayout.show(mainPanel, "createGame");
//			 cardLayout.show(mainPanel, "waiting");
			cardLayout.show(mainPanel, "gameScreen");
//			 cardLayout.show(mainPanel, "joinGame");


			// Global JFrame Settings
			setTitle("Think Tank");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ThinkTankGUI gui = new ThinkTankGUI(Globals.Development.HOST, Globals.Development.GAME_PORT);
	}
}

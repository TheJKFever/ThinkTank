package Client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Helper.Helper;

public class ThinkTank extends JFrame {
	private ThinkTankClient connection;
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel;
	//	private MainMenuScreen mainMenu;
	//	private StatsScreen stats;
	//	private CreateProfileScreen createProfile;
	//	private CreateGameScreen createGame;
	//	private WaitingScreen waiting;
	//	private JoinGameScreen joinGame;
	private GameScreen gameScreen;
	//	private GameOverScreen gameOver;
	

	public ThinkTank(String host, int port) {
		try {
			connection = new ThinkTankClient(this, host, port);

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
			gameScreen = new GameScreen(); 
//			 gameOver = new GameOverScreen(); 

//			 mainPanel.add("mainMenu", mainMenu);
//			 mainPanel.add("stats", stats);
//			 mainPanel.add("createProfile", createProfile);
//			 mainPanel.add("createGame", createGame);
//			 mainPanel.add("waiting", waiting);
			mainPanel.add("gameScreen", gameScreen);
//			 mainPanel.add("joinGame", joinGame);
			
			
//			 mainPanel.add(gameOver);


			// TODO: put all  of these in action listeners
//			 cardLayout.show(mainMenu, "mainMenu");
//			 cardLayout.show(stats, "stats");
//			 cardLayout.show(createProfile, "createProfile");
//			 cardLayout.show(createGame, "createGame");
//			 cardLayout.show(waiting, "waiting");
			cardLayout.show(gameScreen, "gameScreen");
//			 cardLayout.show(joinGame, "joinGame");


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
		ThinkTank game = new ThinkTank(Globals.Development.HOST, Globals.Development.PORT);
	}
}

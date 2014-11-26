package Client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Game.Event;
import Game.Globals;
import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.WaitingScreen;

public class ThinkTankGUI extends JFrame {
	private ConnectionToCentralServer centralConnection;
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel;
	public MainMenuScreen mainMenu;
	//	private StatsScreen stats;
	//	private CreateProfileScreen createProfile;
	//	private CreateGameScreen createGame;
	public WaitingScreen waiting;
	//	private JoinGameScreen joinGame;
	public GameScreen gameScreen;
	//	private GameOverScreen gameOver;
	public boolean loggedIn = false;

	
	public ThinkTankGUI(String host, int port) {
		try {
			centralConnection = new ConnectionToCentralServer(this, host, port); // Connects to central server
			
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
			gameScreen = new GameScreen(this);
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
			 cardLayout.show(mainPanel, "mainMenu");
//			 cardLayout.show(mainPanel, "stats");
//			 cardLayout.show(mainPanel, "createProfile");
//			 cardLayout.show(mainPanel, "createGame");
//			 cardLayout.show(mainPanel, "waiting");
//			 cardLayout.show(mainPanel, "gameScreen");
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

	public void startNewGame() {
		centralConnection.sendEvent(new Event("new game"));
		cardLayout.show(mainPanel, "waiting");
	}
	
	public void joinGame() {
		// Go to lobby
		// Select available game
		// get port from that game
		joinGame(port);
	}
	
	public void joinGame(int port) {
		// make connection to game server
		// store connection in gameScreen
		// if connection made go to waiting screen
		if (gameScreen.connectToGameServer(Globals.Development.HOST, port)) {
			cardLayout.show(mainPanel, "waiting");
		} else {
			throw new RuntimeException("Could not create game in joinGame");
		}
	}

	public static void main(String[] args) {
		ThinkTankGUI gui = new ThinkTankGUI(Globals.Development.HOST, Globals.Development.SERVER_PORT);
	}

	public void startGame() {
		cardLayout.show(mainPanel, "gameScreen");
		gameScreen.engine.s
	}

}

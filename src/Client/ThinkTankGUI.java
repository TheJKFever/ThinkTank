package Client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Game.Event;
import Game.Globals;
import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.WaitingScreen;

public class ThinkTankGUI extends JFrame {
	public static Logger logger = Logger.getLogger("ThinkTankClient.log");
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
			setTitle("Think Tank");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(Globals.GUI_WIDTH, Globals.GUI_HEIGHT);
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			    	try {
			    		centralConnection.close();
				    	if (gameScreen.gameConnection!=null) {
				    		gameScreen.gameConnection.thread.interrupt();
				    		gameScreen.gameConnection.close();
				    	}
					} catch (IOException e) {
						e.printStackTrace();
					}
		            System.exit(0);
			    }
			});

			System.out.println("host: " + host + " and port: " + port);
			centralConnection = new ConnectionToCentralServer(this, host, port); // Connects to central server
			System.out.println("Got here");
			// client needs to have a unique id
			// client needs to have a specific tank that it controls
			// tank needs to belong to specific client/player
			mainPanel = new JPanel();
			mainPanel.setLayout(cardLayout);
			add(mainPanel);

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
			 mainMenu = new MainMenuScreen(this); 
//			 stats = new StatsScreen(); 
//			 createProfile = new CreateProfileScreen(); 
//			 createGame = new CreateGameScreen(); 
			 waiting = new WaitingScreen(); 
//			 joinGame = new JoinGameScreen(); 
			 gameScreen = new GameScreen(this);
//			 gameOver = new GameOverScreen(); 

			 mainPanel.add("mainMenu", mainMenu);
//			 mainPanel.add("stats", stats);
//			 mainPanel.add("createProfile", createProfile);
//			 mainPanel.add("createGame", createGame);
			 mainPanel.add("waiting", waiting);
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startNewGame() {
		System.out.println("GUI: START NEW GAME CLICKED");
		centralConnection.sendEvent(new Event("new game"));
		cardLayout.show(mainPanel, "waiting");
	}
	
	public void joinGame() {
		System.out.println("GUI: joinGame()");
		// Go to lobby
		// Select available game
		// get port from that game
		centralConnection.sendEvent(new Event("join game"));
	}
	
	public void joinGame(int port) {
		System.out.println("GUI: joinGame(port)");
		// make connection to game server
		// store connection in gameScreen
		// if connection made go to waiting screen
		if (gameScreen.connectToGameServer(Globals.Development.HOST, port)) {
			System.out.println("GUI: gameScreen connected to game server");
			cardLayout.show(mainPanel, "waiting");
			gameScreen.gameConnection.thread.start();
		} else {
			throw new RuntimeException("Could not create game in joinGame");
		}
	}

	public void startGame() {
		System.out.println("GUI: STARTGAME()");
		cardLayout.show(mainPanel, "gameScreen");
	}

	public static void main(String[] args) {
		ThinkTankGUI gui = new ThinkTankGUI(Globals.Development.HOST, Globals.Development.SERVER_PORT);
	}
}

package Client;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Game.Event;
import Game.Helper;
import Global.Settings;
import Screens.CreateGameScreen;
import Screens.CreateProfileScreen;
import Screens.GameScreen;
import Screens.LobbyScreen;
import Screens.MainMenuScreen;
import Screens.WaitingScreen;

public class ThinkTankGUI extends JFrame {
	public static Logger logger = Logger.getLogger("ThinkTankClient.log");
	public ConnectionToCentralServer centralConnection;
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel;
	public MainMenuScreen mainMenu;
	//	private StatsScreen stats;
	private CreateProfileScreen createProfile;
	public CreateGameScreen createGame;
	public WaitingScreen waiting;
	public LobbyScreen lobby;
	public GameScreen gameScreen;
	//	private GameOverScreen gameOver;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem mainMenuItem, exitItem;
	public boolean loggedIn = false;
	
	public static final String MainMenuPage = "mainMenu", 
			CreateGamePage = "createGame", 
			WaitingPage = "waiting",
			LobbyPage = "lobby",
			GameScreenPage = "gameScreen",
			StatsPage = "stats",
			CreateProfilePage = "createProfile",
			GameOverPage = "gameOverPage";
	
	public ThinkTankGUI(String host, int port) {
		try {
			setTitle("Think Tank");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(Settings.GUI_WIDTH, Settings.GUI_HEIGHT);
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			    	try {
			    		if (inGameConfirmForeit(ThinkTankGUI.this)) {
				    		centralConnection.close();
					    	if (gameScreen.gameConnection !=null) {
					    		gameScreen.gameConnection.thread.interrupt();
					    		gameScreen.gameConnection.close();
					    	}
					    	ThinkTankGUI.this.dispose();
			    		}
		    		} catch (IOException e) {
						e.printStackTrace();
			            System.exit(0);
					}
			    }
			});

			Helper.log("Connecting to " + host + ":" + port);
			centralConnection = new ConnectionToCentralServer(this, host, port); // Connects to central server
			mainPanel = new JPanel();
			mainPanel.setPreferredSize(new Dimension(Settings.GUI_WIDTH, Settings.GUI_HEIGHT));
			mainPanel.setLayout(cardLayout);
			add(mainPanel);
			
			menuBar = new JMenuBar();
			menu = new JMenu("File");
			menuBar.add(menu);
			mainMenuItem = new JMenuItem("Main Menu");
			mainMenuItem.addActionListener(new MainMenuListener(this));
			exitItem = new JMenuItem("Exit");
			exitItem.addActionListener(new CloseWindowListener(this));
			menu.add(mainMenuItem);
			menu.add(exitItem);
			this.setJMenuBar(menuBar);

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
			 mainMenu.setName(MainMenuPage);
			 createGame = new CreateGameScreen(this);
			 createGame.setName(CreateGamePage);
			 waiting = new WaitingScreen(); 
			 waiting.setName(WaitingPage);
			 lobby = new LobbyScreen(this);
			 lobby.setName(LobbyPage);
			 gameScreen = new GameScreen(this);
			 gameScreen.setName(GameScreenPage);
			 createProfile = new CreateProfileScreen(this); 
//			 gameOver = new GameOverScreen(this); 
//			 stats = new StatsScreen(this); 

			 mainPanel.add(MainMenuPage, mainMenu);
			 mainPanel.add(CreateGamePage, createGame);
			 mainPanel.add(WaitingPage, waiting);
			 mainPanel.add(GameScreenPage, gameScreen);
			 mainPanel.add(LobbyPage, lobby);
//			 mainPanel.add("createProfile", createProfile);
			
//			 mainPanel.add(gameOver);

			 cardLayout.show(mainPanel, "mainMenu");

			 Helper.log("Finished ThinkTankGUI Constructor");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean inGameConfirmForeit(ThinkTankGUI gui) {
		if (currentScreenIsGameScreen()) {
			int confirmation = JOptionPane.showConfirmDialog (null, "Are you sure you want to forfeit this game?","Warning",JOptionPane.YES_NO_OPTION);
			if(confirmation == JOptionPane.YES_OPTION) return false;
		} 
		return true;
	}

	private boolean currentScreenIsGameScreen() {
	    for (Component comp : mainPanel.getComponents() ) {
	        if (comp.isVisible() == true) {
	            JPanel card = (JPanel)comp;
	            System.out.println(card.getName());
	            if (card.getName().equalsIgnoreCase("gameScreen")) return true;
	            return false;
	        }
	    }
	    return false;
	}

	public void startNewGame(String name) {
		Helper.log("GUI: START NEW GAME CLICKED");
		centralConnection.sendEvent(new Event("new game", name));
		goTo(WaitingPage);
	}
	
	public void joinGame() {
		Helper.log("GUI: joinGame()");
		// Go to lobby
		// Select available game
		// get port from that game
		centralConnection.sendEvent(new Event("join game"));
	}
	
	public void joinGame(int port) {
		Helper.log("GUI: joinGame(" + port + ")");
		// make connection to game server
		// store connection in gameScreen
		// if connection made go to waiting screen
		if (gameScreen.connectToGameServer(Settings.Development.HOST, port)) {
			System.out.println("GUI: gameScreen connected to game server");
			goTo(WaitingPage);
			gameScreen.gameConnection.thread.start();
		} else {
			throw new RuntimeException("Could not create game in joinGame");
		}
	}

	public void goTo(String page) {
		Helper.log("GUI: Going to page: " + page);
		cardLayout.show(mainPanel, page);
		if (page.equals(createGame.getName())) {
			createGame.gameNameTf.requestFocus();
		}
	}
	
	public class MainMenuListener implements ActionListener {
		public ThinkTankGUI gui;
		public MainMenuListener(ThinkTankGUI gui) {
			this.gui = gui;
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO: if in the game, option page to confirm, are you sure you want to forfeit?
    		if (inGameConfirmForeit(gui)) {
    			gui.goTo(MainMenuPage);
    		}
		}
	}
		
	public class CloseWindowListener implements ActionListener {
		public ThinkTankGUI gui;
		public CloseWindowListener(ThinkTankGUI gui) {
			this.gui = gui;
		}
		public void actionPerformed(ActionEvent e) {
	    	try {
	    		if (inGameConfirmForeit(gui)) {
		    		gui.centralConnection.close();
			    	if (gui.gameScreen.gameConnection !=null) {
			    		gui.gameScreen.gameConnection.thread.interrupt();
			    		gui.gameScreen.gameConnection.close();
			    	}
			    	gui.dispose();
	    		}
		    } catch (IOException ioe) {
		    	ioe.printStackTrace();
	            System.exit(0);
			}
		}
	}
	
	public static void main(String[] args) {
		ThinkTankGUI gui = new ThinkTankGUI(Settings.Development.HOST, Settings.Development.SERVER_PORT);
	}
}

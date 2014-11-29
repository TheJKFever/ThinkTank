package Client;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ThinkTank extends JFrame {
	private ThinkTankClient connection;
	private CardLayout cardLayout = new CardLayout();
	
	private JPanel all = new JPanel();
	
	private UtilityBar bar = new UtilityBar();
	
	public static JPanel mainPanel;
	//	private MainMenuScreen mainMenu;
	//	private StatsScreen stats;
	//	private CreateProfileScreen createProfile;
	//	private CreateGameScreen createGame;
	//	private WaitingScreen waiting;
	//	private JoinGameScreen joinGame;
	public static GameScreen gameScreen;
	//	private GameOverScreen gameOver;
	

	public ThinkTank(String host, int port) {
		try {
			connection = new ThinkTankClient(this, host, port);

			mainPanel = new JPanel();
			mainPanel.setFocusable(true);
			
			mainPanel.setLayout(cardLayout);
			
			all.setLayout(null);
			
			mainPanel.setBounds(0, 0, 800, 600);
			all.add(mainPanel);
			bar.setBounds(0,600,800,100);
			all.add(bar);
			add(all);

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
			
			bar.setTank(gameScreen.tank);

//			 mainPanel.add("mainMenu", mainMenu);
//			 mainPanel.add("stats", stats);
//			 mainPanel.add("createProfile", createProfile);
//			 mainPanel.add("createGame", createGame);
//			 mainPanel.add("waiting", waiting);
			mainPanel.add("gameScreen", gameScreen);
//			 mainPanel.add("joinGame", joinGame);
			

			gameScreen.addMouseListener(new MouseAdapter(){
				
				public void mouseClicked(MouseEvent e){
					
					gameScreen.requestFocus();


				}

				
			});
			
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
			setSize(Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT+130);
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

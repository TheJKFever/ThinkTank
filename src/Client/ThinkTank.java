package Client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
//			connection = new ThinkTankClient(this, host, port);

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

//			 cards.add(mainMenu);
//			 cards.add(stats);
//			 cards.add(createProfile);
//			 cards.add(createGame);
//			 cards.add(waiting);
//			 cards.add(joinGame);
//			mainPanel.add(gameScreen);
			mainPanel.add("gameScreen", gameScreen);
			
			
//			 cards.add(gameOver);


			// TODO: put all  of these in action listeners
//			 cardLayout.show(mainMenu, "Main Menu");
//			 cardLayout.show(stats, "Stats");
//			 cardLayout.show(createProfile, "Create Profile");
//			 cardLayout.show(createGame, "Create Game");
//			 cardLayout.show(waiting, "Waiting");
//			 cardLayout.show(joinGame, "Join Game");
//			cardLayout.show(gameScreen, "gameScreen");
			cardLayout.show(mainPanel, "gameScreen");
//			 cardLayout.show(gameOver, "Game Over");

			
//			cardLayout.sh


			// Global JFrame Settings
			setTitle("Think Tank");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void main(String[] args) {
		ThinkTank game = new ThinkTank(Globals.Development.HOST, Globals.Development.PORT);
	}
}

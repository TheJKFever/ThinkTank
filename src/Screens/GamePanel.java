package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Entities.Barrier;
import Entities.Brain;
import Entities.Entity;
import Entities.ThoughtPool;
import Game.GameState;
import Game.Helper;
import Game.Player;
import Global.Settings;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 123783444588707640L; // not necessary
	public GameState gameState;
	private GameScreen gameScreen;
	HashMap<String, Image> imageCache;
	Player player;
	
	// HUD LAYOUT CONFIGURATION
	
	static Font DISPLAY_TIME_FONT = new Font("Helvetica", Font.BOLD, 18);
	static Font BRAIN_HB_LBL_FONT  = new Font("Helvetica", Font.BOLD, 12);
	static Font TANK_HEALTH_FONT = new Font("Helvetica", Font.BOLD, 12);
	static Font THOUGHT_FONT = new Font("Helvetica", Font.BOLD, 12);
	
	static int TOP_HUD_Y = 25;
	static int LEFT_HUD_X = 15;
			
	// BRAINHB = BRAIN HEALTH BAR
	
	static int BRAIN_HB_LBL_X = LEFT_HUD_X;
	static int BRAIN_HB_LBL_Y = TOP_HUD_Y;

	static int BRAIN_HB_X = LEFT_HUD_X;
	static int BRAIN_HB_Y = TOP_HUD_Y + 10;
	
	static int BRAIN_HB_WIDTH = 100;
	static int BRAIN_HB_HEIGHT = 20;
	
	static int DISPLAY_TIME_X = Settings.BOARD_WIDTH/2; // TODO: display time exactly centered
	static int DISPLAY_TIME_Y = TOP_HUD_Y;

	static int TANK_HEALTH_X = LEFT_HUD_X;
	static int TANK_HEALTH_Y = BRAIN_HB_Y + BRAIN_HB_HEIGHT + 20;
	
	static int THOUGHTS_X = LEFT_HUD_X;
	static int THOUGHTS_Y = TANK_HEALTH_Y + 20;
	
	static int RIGHT_HUD_X = Settings.BOARD_WIDTH - LEFT_HUD_X - BRAIN_HB_WIDTH;

	
	public GamePanel(GameScreen gameScreen) {
		super();
		Helper.log("Creating new GamePanel");
		this.gameScreen = gameScreen;
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				GamePanel.this.requestFocusInWindow();
			}
		});
		this.imageCache = new HashMap<String, Image>();
		this.player = null;
	}

	public void paint(Graphics g) {
		super.paint(g);
		this.gameState = this.gameScreen.engine.gameState;
		this.player = gameScreen.engine.player;
		render(g);
	}
	
	
	// transform x coordinate based on player's team (i.e. reverse map for team 2)
	public int tfx(int x) {
		if (player.team.num == 2) {
			x = Settings.BOARD_WIDTH - x;
			return x;
		} else {
			return x;
		}
	}
	
	// transform y coordinate based on player's team (i.e. reverse map for team 2)
	public int tfy(int y) {
		if (player.team.num == 2) {
			y = Settings.BOARD_HEIGHT - y;
			return y;
		} else {
			return y;
		}
	}
	
	public Image getImg(String path) {
		if (imageCache.containsKey(path)) {
			return imageCache.get(path);
		} else {
			Image img = new ImageIcon(path).getImage();
			imageCache.put(path, img);
			return img;
		} 
	}
	
	public void render(Graphics g) {
		Helper.log("GAMEPANEL: RENDER");
			
		// rotate screen if player is on team 2
		Graphics2D g2d = (Graphics2D) g;
		if (this.player != null) {
			if (this.player.team.num == 2) {
				int w2 = getWidth() / 2;
		      	int h2 = getHeight() / 2;
		      	g2d.rotate(Math.PI, w2, h2);				
			}
		}
	
		drawMap(g);
		drawThoughtPools(g2d);
		drawBarriers(g2d);
		drawBrains(g2d);
		drawTanks(g2d);
		drawShots(g2d);
		
		// rotate screen back to normal before rendering HUD
		if (this.player != null) {
			if (this.player.team.num == 2) {
				int w2 = getWidth() / 2;
		      	int h2 = getHeight() / 2;
		      	g2d.rotate(Math.PI, w2, h2);				
			}
		}
		
		drawHUD(g2d);
	
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
		g.dispose();
	}

	
	public void drawHUD(Graphics g) {
		g.setColor(Color.white);
		g.setFont(DISPLAY_TIME_FONT);
		g.drawString(gameState.displayTime, DISPLAY_TIME_X, DISPLAY_TIME_Y);
		

		g.setFont(TANK_HEALTH_FONT);
		g.drawString("Health: " + player.tank.health + "/10", TANK_HEALTH_X, TANK_HEALTH_Y);
		
		g.setFont(THOUGHT_FONT);
		g.drawString("Thoughts: " + player.tank.thoughts, THOUGHTS_X, THOUGHTS_Y);
		
		drawBrainHealthBars(g);
	}
	
	public void drawBrainHealthBars(Graphics g) {
		
		int teamNum;
		int enemyTeamNum;
		
		if (this.player.team.num == 1) {
			teamNum = 1;
			enemyTeamNum = 2;
		} else {
			teamNum = 2;
			enemyTeamNum = 1;
		}
		
		// Draw your team's brain health
		Brain brain = gameState.teams[teamNum-1].brain;
		
		// Label
		g.setColor(Color.white);
		g.setFont(BRAIN_HB_LBL_FONT);
		g.drawString("Brain " + brain.team.num, BRAIN_HB_LBL_X, BRAIN_HB_LBL_Y);
				
		// Health bar
		g.setColor(Color.white);
		g.drawRect(BRAIN_HB_X, BRAIN_HB_Y, BRAIN_HB_WIDTH, BRAIN_HB_HEIGHT);
		g.setColor(Color.green);
		g.fillRect(BRAIN_HB_X, BRAIN_HB_Y, brain.health, BRAIN_HB_HEIGHT);
		
		
		// Draw enemy's team's brain health
		brain = gameState.teams[enemyTeamNum-1].brain;
		
		// Label
		g.setColor(Color.white);
		g.setFont(BRAIN_HB_LBL_FONT);
		g.drawString("Brain " + brain.team.num, RIGHT_HUD_X, BRAIN_HB_LBL_Y);
				
		// Health bar
		g.setColor(Color.white);
		g.drawRect(RIGHT_HUD_X, BRAIN_HB_Y, BRAIN_HB_WIDTH, BRAIN_HB_HEIGHT);
		g.setColor(Color.green);
		g.fillRect(RIGHT_HUD_X, BRAIN_HB_Y, brain.health, BRAIN_HB_HEIGHT);
	}
	
	public void drawMap(Graphics g) {
		g.setColor(Color.black);
		// black background
//<<<<<<< HEAD
//		g.fillRect(0, 0, Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT);
//		
//		
//		// top wall
//		g.setColor(Color.green);
//		g.fillRect(0, 0, Settings.BOARD_WIDTH, 10);
//		
//		// left wall
//		g.fillRect(0, 0, 10, Settings.BOARD_HEIGHT);
//		
//		// bottom wall
//		g.fillRect(0, Settings.BOARD_HEIGHT-10, Settings.BOARD_WIDTH, 10);
//		
//		// right wall
//		g.fillRect(Settings.BOARD_WIDTH - 10, 0, 10, Settings.BOARD_HEIGHT);
//		g.setColor(Color.black);
//=======
		g.fillRect(0, 0, Global.Settings.BOARD_WIDTH, Global.Settings.BOARD_HEIGHT);
	}
	
	public void drawBarriers(Graphics g) {
		Helper.log("GAMEPANEL: DRAW BARRIERS");
		for (Barrier barrier: gameState.barriers) {
			g.setColor(Barrier.color);
			g.fillRect(barrier.x, barrier.y, barrier.width, barrier.height);
		}
	}

	public void drawThoughtPools(Graphics g) {
		Helper.log("GAMEPANEL: DRAW THOUGHT POOLS");
		for (ThoughtPool thoughtPool: gameState.thoughtPools) {
			g.setColor(thoughtPool.color);
			g.fillRect(thoughtPool.x, thoughtPool.y, thoughtPool.width, thoughtPool.height);
		}
	}
	
	public void drawBrains(Graphics g) {
		Helper.log("GAMEPANEL: DRAW BRAINS");
		for (Brain brain: gameState.brains) {
<<<<<<< HEAD
		
			// TODO: RENDER BRAIN EXPLOSION
=======
>>>>>>> d195650dff6652e3c2512162ad1a6f2bd3dd0e8a
			if (brain.isVisible()) {
				System.out.println("path is: " + brain.imagePath);
				g.drawImage(getImg(brain.imagePath), brain.x, brain.y, this);
			}
			if (brain.isDying()) {
				brain.die();
			}
<<<<<<< HEAD
=======
			
			System.out.println(brain.team.num);
			 // draw in two different placed based on team
			if (brain.team.num == 1) {
				g.setColor(Color.white);
				g.drawRect(20, 20, 100, 20);
				g.setColor(Color.green);
				g.fillRect(20, 20, brain.health, 20);
	
				Font small = new Font("Helvetica", Font.BOLD, 14);
//				FontMetrics metr = this.getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString("Brain" + brain.team.num, 140, 20);
			} else {
				g.setColor(Color.white);
				g.drawRect(600, 20, 100, 20);
				g.setColor(Color.green);
				g.fillRect(600, 20, brain.health, 20);
	
				Font small = new Font("Helvetica", Font.BOLD, 14);
//				FontMetrics metr = this.getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString("Brain" + brain.team.num, 740, 20);
			}
>>>>>>> d195650dff6652e3c2512162ad1a6f2bd3dd0e8a
		}
	}
	
	public void drawShots(Graphics g) {
		drawEntities(g, gameState.shots);
	}
	
	public void drawTanks(Graphics g) {
		drawEntities(g, gameState.tanks);
	}
	
	public void drawEntities(Graphics g, Vector<? extends Entity> entities) {
		Helper.log("GAMEPANEL: DRAW ENTITIES");
		
		for (Entity e : entities) {
			if (e.isVisible()) {
				g.drawImage(getImg(e.imagePath), e.x, e.y, this);
			}
			if (e.isDying()) {
				e.die();
			}
		}
	}
}

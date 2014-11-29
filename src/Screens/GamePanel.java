package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import sun.security.jgss.GSSCaller;
import Entities.Barrier;
import Entities.Brain;
import Entities.Entity;
import Entities.ThoughtPool;
import Game.GameState;
import Game.Player;
import Global.Settings;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 123783444588707640L; // not necessary
	
	public GameScreen gameScreen;
	public GameState gameState;
	HashMap<String, Image> imageCache;
	Player player;
	
	public GamePanel(GameScreen gameScreen) {
		super();
		log("GAMEPANEL: IN CONSTRUCTOR");
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
		log("GAMEPANEL: RENDER");	
		drawMap(g);
		drawThoughtPools(g);
		drawBarriers(g);
		drawBrains(g);
		drawTanks(g);
		drawShots(g);
		drawHUD(g);
	
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	
	public void drawHUD(Graphics g) {
		Font clockFont = new Font("Helvetica", Font.BOLD, 18);
		g.setColor(Color.white);
		g.setFont(clockFont);
		
		g.drawString(gameState.displayTime, Settings.BOARD_WIDTH/2-10, 25);
		g.drawString("Thoughts: " + player.tank.thoughts, 20, 40);
		g.drawString("Health: " + player.tank.health + "/10", 20, 60);
	}
	
	public void drawMap(Graphics g) {
		g.setColor(Color.black);
		// black background
		g.fillRect(0, 0, Global.Settings.BOARD_WIDTH, Global.Settings.BOARD_HEIGHT);
	}
	
	public void drawBarriers(Graphics g) {
		log("GAMEPANEL: DRAW BARRIERS");
		for (Barrier barrier: gameState.barriers) {
			g.setColor(Barrier.color);
			g.fillRect(barrier.x, barrier.y, barrier.width, barrier.height);
		}
	}

	public void drawThoughtPools(Graphics g) {
		log("GAMEPANEL: DRAW THOUGHT POOLS");
		for (ThoughtPool thoughtPool: gameState.thoughtPools) {
			g.setColor(thoughtPool.color);
			g.fillRect(thoughtPool.x, thoughtPool.y, thoughtPool.width, thoughtPool.height);
		}
	}
	
	public void drawBrains(Graphics g) {
		log("GAMEPANEL: DRAW BRAINS");
		for (Brain brain: gameState.brains) {
		
			if (brain.isVisible()) {
				g.drawImage(getImg(brain.imagePath), brain.x, brain.y, this);
			}
			if (brain.isDying()) {
				brain.die();
			}
			
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
		}
	}
	
	public void drawShots(Graphics g) {
		drawEntities(g, gameState.shots);
	}
	
	public void drawTanks(Graphics g) {
		drawEntities(g, gameState.tanks);
	}
	
	public void drawEntities(Graphics g, Vector<? extends Entity> entities) {
		log("GAMEPANEL: DRAW ENTITIES");
		
		for (Entity e : entities) {
			if (e.isVisible()) {
				g.drawImage(getImg(e.imagePath), e.x, e.y, this);
			}
			if (e.isDying()) {
				e.die();
			}
		}
	}
	
    public void log(String msg) {
    	if (Settings.DEBUG) {
    		System.out.println(msg);
    	}
    }
}

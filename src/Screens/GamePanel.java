package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Entities.Barrier;
import Entities.Brain;
import Entities.Entity;
import Game.GameState;
import Game.Globals;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 123783444588707640L; // not necessary
	
	public GameScreen gameScreen;
	public GameState gameState;
	
	public GamePanel(GameScreen gameScreen) {
		super();
		log("GAMEPANEL: IN CONSTRUCTOR");
		this.gameScreen = gameScreen;
		this.setFocusable(true);
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("GAMEPANEL: ABOUT TO REPAINT");
		this.gameState = this.gameScreen.engine.gameState;
		render(g);
	}
	
	public Image getImg(String path) {
		// TODO: CREATE A HASH MAP THAT CACHES IMAGES IT HAS ALREADY CREATED
		return new ImageIcon(path).getImage();
	}
	
	public void render(Graphics g) {
		log("GAMEPANEL: RENDER");
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.Globals.BOARD_WIDTH, Game.Globals.BOARD_HEIGHT);
		
		drawBarriers(g);
		drawBrains(g);
		drawTanks(g);
		drawShots(g);
	
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void drawBarriers(Graphics g) {
		log("GAMEPANEL: DRAW BARRIERS");
		for (Barrier barrier: gameState.barriers) {
			g.setColor(Barrier.color);
			g.fillRect(barrier.x, barrier.y, barrier.getWidth(), barrier.getHeight());
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
    	if (Globals.DEBUG) {
    		System.out.println(msg);
    	}
    }
}

package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JPanel;

import Entities.Barrier;
import Entities.Brain;
import Entities.Entity;

public class GamePanel extends JPanel {

	public GameState gs;
	
	public GamePanel() {
		this.gs = null;
		this.setFocusable(true);
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		render(g);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.Globals.BOARD_WIDTH, Game.Globals.BOARD_HEIGHT);
		
		drawBarriers(g);
		drawBrains(g);
		drawEntities(g, gs.tanks);
		drawEntities(g, gs.shots);
	
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void drawBarriers(Graphics g) {
		for (Barrier barrier: gs.barriers) {
			g.setColor(Barrier.color);
			g.fillRect(barrier.x, barrier.y, barrier.getWidth(), barrier.getHeight());
		}
	}
	
	public void drawBrains(Graphics g) {
		for (Brain brain: gs.brains) {
		
			if (brain.isVisible()) {
				g.drawImage(brain.getImage(), brain.x, brain.y, this);
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
				FontMetrics metr = this.getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString("Brain" + brain.team.num, 140, 20);
			} else {
				g.setColor(Color.white);
				g.drawRect(600, 20, 100, 20);
				g.setColor(Color.green);
				g.fillRect(600, 20, brain.health, 20);
	
				Font small = new Font("Helvetica", Font.BOLD, 14);
				FontMetrics metr = this.getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString("Brain" + brain.team.num, 740, 20);
			}
		}
	}
	
	public void drawEntities(Graphics g, Vector<? extends Entity> entities) {
		for (Entity e : entities) {
			if (e.isVisible()) {
				g.drawImage(e.getImage(), e.x, e.y, this);
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

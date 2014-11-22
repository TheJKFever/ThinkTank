package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Renderer {
	
	Game game;
	
	public Renderer(Game g) {
		this.game = g;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, game.d.width, game.d.height);
		g.setColor(Color.green);
	
		if (game.ingame) {
			drawBarriers(g);
//			drawEntities(g, game.brains);
			drawBrains(g);
			drawEntities(g, game.tanks);
			drawEntities(g, game.shots);
		}
	
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void drawBarriers(Graphics g) {
		for (Barrier barrier: game.barriers) {
			g.setColor(Barrier.color);
			g.fillRect(barrier.x, barrier.y, barrier.getWidth(), barrier.getHeight());
		}
	}
	
	public void drawBrains(Graphics g) {
		for (Brain brain: game.brains) {
		
			if (brain.isVisible()) {
				g.drawImage(brain.getImage(), brain.x, brain.y, game);
			}
			if (brain.isDying()) {
				brain.die();
			}
			
			 // draw in two different placed based on team
			if (brain.team == 1) {
				g.setColor(Color.white);
				g.drawRect(20, 20, 100, 20);
				g.setColor(Color.green);
				g.fillRect(20, 20, brain.health, 20);
	
				Font small = new Font("Helvetica", Font.BOLD, 14);
				FontMetrics metr = game.getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString("Brain" + brain.team, 140, 20);
			} else {
				g.setColor(Color.white);
				g.drawRect(600, 20, 100, 20);
				g.setColor(Color.green);
				g.fillRect(600, 20, brain.health, 20);
	
				Font small = new Font("Helvetica", Font.BOLD, 14);
				FontMetrics metr = game.getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString("Brain" + brain.team, 740, 20);
			}
		}
	}
	
	public void drawEntities(Graphics g, ArrayList<? extends Entity> entities) {
		for (Entity e : entities) {
			if (e.isVisible()) {
				g.drawImage(e.getImage(), e.x, e.y, game);
			}
			if (e.isDying()) {
				e.die();
			}
		}
	}

	// public void drawEnemies(Graphics g)
	// {
	// Iterator it = enemies.iterator();
	//
	// while (it.hasNext()) {
	// Enemy enemy = (Enemy) it.next();
	//
	// if (enemy.isVisible()) {
	// g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
	// }
	//
	// if (enemy.isDying()) {
	// enemy.die();
	// }
	// }
	// }

	// public void drawPlayer(Graphics g) {
	//
	// if (tank.isVisible()) {
	// g.drawImage(tank.getImage(), tank.getX(), tank.getY(), this);
	// }
	//
	// if (tank.isDying()) {
	// tank.die();
	// ingame = false;
	// }
	// }

	// public void drawShot(Graphics g) {
	// for (Shot shot: shots) {
	// if (shot.isVisible()) {
	// g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
	// }
	// }
	// }

	// public void drawBombing(Graphics g) {
	//
	// Iterator i3 = enemies.iterator();
	//
	// while (i3.hasNext()) {
	// Enemy a = (Enemy) i3.next();
	//
	// Enemy.Bomb b = a.getBomb();
	//
	// if (!b.isDestroyed()) {
	// g.drawImage(b.getImage(), b.getX(), b.getY(), this);
	// }
	// }
	// }

//	public void renderGameOver() {
//		Graphics g = game.getGraphics();
//
//		g.setColor(Color.black);
//		g.fillRect(0, 0, Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);
//
//		g.setColor(new Color(0, 32, 48));
//		g.fillRect(50, Globals.BOARD_WIDTH / 2 - 30, Globals.BOARD_WIDTH - 100,
//				50);
//		g.setColor(Color.white);
//		g.drawRect(50, Globals.BOARD_WIDTH / 2 - 30, Globals.BOARD_WIDTH - 100,
//				50);
//
//		Font small = new Font("Helvetica", Font.BOLD, 14);
//		FontMetrics metr = game.getFontMetrics(small);
//
//		g.setColor(Color.white);
//		g.setFont(small);
//		g.drawString(
//			game.gameOverMessage,
//			(Globals.BOARD_WIDTH - metr.stringWidth(game.gameOverMessage)) / 2,
//			Globals.BOARD_WIDTH / 2
//		);	
//	}
	
    public void log(String msg) {
    	if (Globals.DEBUG) {
    		System.out.println(msg);
    	}
    }
}

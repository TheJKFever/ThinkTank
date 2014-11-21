package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Battle extends JPanel implements Runnable {

	Dimension d;
	Tank tank;
	ArrayList<Shot> shots;
	ArrayList<Brain> brains;
	ArrayList<Tank> tanks;
	// ArrayList<Entity> entities;
	// ArrayList<Enemy> enemies;
	Brain brain1;
	Brain brain2;

	// int enemyX = 150;
	// int enemyY = 5;
	// int direction = -1;
	int deaths = 0;

	boolean ingame = true;
	final String expl = "images/explosion.png";
	final String enemypix = "images/enemy.png";
	String message = "Game Over";

	private Thread animator;

	public Battle() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		d = new Dimension(Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);
		setBackground(Color.black);

		// TODO: IF THINGS STOP WORKING, LOOK HERE gameInit();
		setDoubleBuffered(true);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {
		log("gameInit()...");
		// enemies = new ArrayList<Enemy>();
		brains = new ArrayList<Brain>();
		tanks = new ArrayList<Tank>();
		shots = new ArrayList<Shot>();

		brain1 = new Brain(1);
		brain2 = new Brain(2);
		brains.add(brain1);
		brains.add(brain2);

		tank = new Tank();
		tanks.add(tank);

		// ImageIcon ii = new ImageIcon(this.getClass().getResource(enemypix));
		//
		// for (int i=0; i < 4; i++) {
		// for (int j=0; j < 6; j++) {
		// Enemy enemy = new Enemy(enemyX + 18*j, enemyY + 18*i);
		// enemy.setImage(ii.getImage());
		// enemies.add(enemy);
		// }
		// }

		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void log(String msg) {
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}

	public void drawEntities(Graphics g, ArrayList<? extends Entity> entities) {
		log("Drawing entities...");
		for (Entity e : entities) {
			if (e.isVisible()) {
				g.drawImage(e.getImage(), e.x, e.y, this);
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

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.green);

		if (ingame) {

			g.drawLine(0, Globals.GROUND, Globals.BOARD_WIDTH, Globals.GROUND);
			drawEntities(g, brains);
			drawEntities(g, tanks);
			drawEntities(g, shots);
			// drawEntities(g, enemies);

			// drawEnemies(g);
			// drawPlayer(g);
			// drawShot(g);
			// drawBombing(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {

		Graphics g = this.getGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);

		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, Globals.BOARD_WIDTH / 2 - 30, Globals.BOARD_WIDTH - 100,
				50);
		g.setColor(Color.white);
		g.drawRect(50, Globals.BOARD_WIDTH / 2 - 30, Globals.BOARD_WIDTH - 100,
				50);

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message,
				(Globals.BOARD_WIDTH - metr.stringWidth(message)) / 2,
				Globals.BOARD_WIDTH / 2);
	}

	public void animationCycle() {

		if (deaths == Globals.NUMBER_OF_ENEMIES_TO_DESTROY) {
			ingame = false;
			message = "Game won!";
		}

		// tanks
		for (Tank t : tanks) {
			t.update();
		}

		// brains
		for (Brain b : brains) {
			b.update(shots);
		}

		// shots
		for (int i = (shots.size() - 1); i >= 0; i--) {
			Shot shot = shots.get(i);
			if (!shot.isVisible()) {
				shots.remove(i);
			} else {
				shot.update(this);
			}
		}

		// enemies
		// Iterator it1 = enemies.iterator();
		//
		// while (it1.hasNext()) {
		// Enemy a1 = (Enemy) it1.next();
		// int x = a1.getX();
		//
		// if (x >= Globals.BOARD_WIDTH - Globals.BORDER_RIGHT && direction !=
		// -1) {
		// direction = -1;
		// Iterator i1 = enemies.iterator();
		// while (i1.hasNext()) {
		// Enemy a2 = (Enemy) i1.next();
		// a2.setY(a2.getY() + Globals.GO_DOWN);
		// }
		// }
		//
		// if (x <= Globals.BORDER_LEFT && direction != 1) {
		// direction = 1;
		//
		// Iterator i2 = enemies.iterator();
		// while (i2.hasNext()) {
		// Enemy a = (Enemy)i2.next();
		// a.setY(a.getY() + Globals.GO_DOWN);
		// }
		// }
		// }
		//
		//
		// Iterator it = enemies.iterator();
		//
		// while (it.hasNext()) {
		// Enemy enemy = (Enemy) it.next();
		// if (enemy.isVisible()) {
		//
		// int y = enemy.getY();
		//
		// if (y > Globals.GROUND - Globals.ENEMY_HEIGHT) {
		// ingame = false;
		// message = "Invasion!";
		// }
		//
		// enemy.update(direction);
		// }
		// }

		// bombs

		// Iterator i3 = enemies.iterator();
		// Random generator = new Random();
		//
		// while (i3.hasNext()) {
		// int shot = generator.nextInt(15);
		// Enemy a = (Enemy) i3.next();
		// Enemy.Bomb b = a.getBomb();
		// if (shot == Globals.CHANCE && a.isVisible() && b.isDestroyed()) {
		//
		// b.setDestroyed(false);
		// b.setX(a.getX());
		// b.setY(a.getY());
		// }
		//
		// int bombX = b.getX();
		// int bombY = b.getY();
		// int playerX = tank.getX();
		// int playerY = tank.getY();
		//
		// if (tank.isVisible() && !b.isDestroyed()) {
		// if ( bombX >= (playerX) &&
		// bombX <= (playerX+ Globals.PLAYER_WIDTH) &&
		// bombY >= (playerY) &&
		// bombY <= (playerY + Globals.PLAYER_HEIGHT) ) {
		// ImageIcon ii =
		// new ImageIcon(this.getClass().getResource(expl));
		// tank.setImage(ii.getImage());
		// tank.setDying(true);
		// b.setDestroyed(true);;
		// }
		// }
		//
		// if (!b.isDestroyed()) {
		// b.setY(b.getY() + 1);
		// if (b.getY() >= Globals.GROUND - Globals.BOMB_HEIGHT) {
		// b.setDestroyed(true);
		// }
		// }
		// }
	}

	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ingame) {
			repaint();
			animationCycle();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = Globals.DELAY - timeDiff;

			if (sleep < 0)
				sleep = 2;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
		gameOver();
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			tank.keyReleased(e);
		}


		public void keyPressed(KeyEvent e) {

			tank.keyPressed(e);

			int x = tank.getX();
			int y = tank.getY();

			if (ingame) {
				if (e.isAltDown()) {
					shots.add(new Shot(x, y, tank.theta));
				}
			}
		}
	}
}
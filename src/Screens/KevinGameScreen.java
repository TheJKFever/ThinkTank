package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;
import javax.swing.Renderer;

import Entities.Barrier;
import Entities.Brain;
import Entities.Shot;
import Entities.Tank;
import Game.GameState;

public class KevinGameScreen extends JPanel implements Runnable {
	JPanel chatPanel;
	public GameState gs;
	Dimension d;
	public static Tank tank;

	ArrayBlockingQueue<GameEvent> eventQ = new ArrayBlockingQueue<GameEvent>(
			100);

	Brain brain1;
	Brain brain2;

	int deaths = 0;
	boolean ingame = true;
	Renderer renderer;

	private Thread animator;

	public KevinGameScreen() {
		gs = new GameState();
		addKeyListener(new TAdapter());
		setFocusable(true);
		d = new Dimension(Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);
		setBackground(Color.black);
		setDoubleBuffered(true);
		this.renderer = new Renderer(this);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {
		log("gameInit()...");

		tank = new Tank(this.gs);
		gs.tanks.add(tank);

		gs.barriers.add(new Barrier(100, 100, 300, 10, this.gs));
		gs.barriers.add(new Barrier(200, 200, 200, 10, this.gs));
		gs.barriers.add(new Barrier(300, 300, 400, 10, this.gs));
		gs.barriers.add(new Barrier(400, 400, 100, 10, this.gs));
		gs.barriers.add(new Barrier(150, 150, 10, 200, this.gs));
		gs.barriers.add(new Barrier(250, 250, 10, 100, this.gs));
		// gs.barriers.add(new Barrier(350, 250, 10, 200, this.gs));

		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ingame) {

			processInput();

			update();

			repaint();

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
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		
		this.renderer.render(g);
		
	}

	public void update() {
		// tanks
		for (Tank t : gs.tanks) {
			t.update();
		}
		// brains
		for (Brain b : gs.brains) {
			b.update();
		}
		// barriers
		for (Barrier b : gs.barriers) {
			b.update();
		}
		// shots
		for (int i = (gs.shots.size() - 1); i >= 0; i--) {
			Shot shot = gs.shots.get(i);
			if (!shot.isVisible()) {
				gs.shots.remove(i);
			} else {
				shot.update();
			}
		}
	}

	public void processInput() {
		synchronized (eventQ) {
			for (GameEvent gameEvent : eventQ) {
				if (gameEvent.type == "KeyEvent") {
					if (gameEvent.keyEvent.getID() == KeyEvent.KEY_RELEASED) {
						
						tank.keyReleased(gameEvent.keyEvent);
						
					} else if (gameEvent.keyEvent.getID() == KeyEvent.KEY_PRESSED) {
						
						tank.keyPressed(gameEvent.keyEvent);
						
					}
				}
			}
			eventQ.clear();
		}
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			try {
				GameScreen.this.eventQ.put(new GameEvent(e));
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		public void keyPressed(KeyEvent e) {
			try {
				GameScreen.this.eventQ.put(new GameEvent(e));
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void log(String msg) {
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}
}
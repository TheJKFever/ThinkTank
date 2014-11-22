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
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable {

	Dimension d;
	Tank tank;
	ArrayList<Shot> shots;
	ArrayList<Brain> brains;
	ArrayList<Tank> tanks;
	ArrayList<Barrier> barriers;
	
	ArrayBlockingQueue<GameEvent> eventQ = new ArrayBlockingQueue<GameEvent>(100);
	
	Brain brain1;
	Brain brain2;
	
	int deaths = 0;
	boolean ingame = true;
	Renderer renderer;
	
	private Thread animator;

	public GameScreen() {
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
		
		brains = new ArrayList<Brain>();
		tanks = new ArrayList<Tank>();
		shots = new ArrayList<Shot>();
		barriers = new ArrayList<Barrier>();

		brain1 = new Brain(1, this);
		brain2 = new Brain(2, this);
		brains.add(brain1);
		brains.add(brain2);

		tank = new Tank(this);
		tanks.add(tank);
		
		barriers.add(new Barrier(100, 100, 300, 10, this));
		barriers.add(new Barrier(200, 200, 200, 10, this));
		barriers.add(new Barrier(300, 300, 400, 10, this));
		barriers.add(new Barrier(400, 400, 100, 10, this));
		barriers.add(new Barrier(150, 150, 10, 200, this));
		barriers.add(new Barrier(250, 250, 10, 100, this));
//		barriers.add(new Barrier(350, 250, 10, 200, this));

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
		for (Tank t: tanks) {
			t.update();
		}
		// brains
		for (Brain b: brains) {
			b.update();
		}
		// barriers 
		for (Barrier b: barriers) {
			b.update();
		}
		// shots
		for (int i = (shots.size() - 1); i >= 0; i--) {
			Shot shot = shots.get(i);
			if (!shot.isVisible()) {
				shots.remove(i);
			} else {
				shot.update();
			}
		}
	}
	
	public void processInput() {
		synchronized(eventQ) {
			for (GameEvent gameEvent: eventQ) {
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
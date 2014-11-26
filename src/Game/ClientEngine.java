package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;

import Client.ConnectionToServer;
import Client.Renderer;

public class ClientEngine extends JPanel implements Runnable {
	JPanel chatPanel;
	public GameState gs;
	ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	
	Renderer renderer;
	ConnectionToServer conn;
	public boolean ingame = true;
	
	private Thread engineThread;

	public ClientEngine(ConnectionToServer connection) {
		setFocusable(true);
		setBackground(Color.black);
		setDoubleBuffered(true);
		this.conn = connection;
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {
		this.renderer = new Renderer(this);
		addKeyListener(new GameInputHandler());

		if (engineThread == null || !ingame) {
			engineThread = new Thread(this);
			engineThread.start();
		}
	}

	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();

		while (ingame) {
			// non-blocking?
			gs = conn.getGameStateFromServer();
			//for Event in internalEventQ:
				// sendInputsToServer()
				// applyInputsLocally()
			gs.update();
			repaint();
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = Globals.DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
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
	
	public void processInput() {
		synchronized(eventQ) {
			for (Event event: eventQ) {
				if (event.type == "KeyEvent") {
					if (event.keyEvent.getID() == KeyEvent.KEY_RELEASED) {
						tank.keyReleased(event.keyEvent);
					} else if (event.keyEvent.getID() == KeyEvent.KEY_PRESSED) {
						tank.keyPressed(event.keyEvent);
					}
				}
			}
			eventQ.clear();
		}
	}
	
	private class GameInputHandler extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			synchronized(eventQ) {
				try {
					Event event = new Event(e);
					eventQ.put(event);
					conn.sendEvent(event);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		
		public void keyPressed(KeyEvent e) {
			synchronized(eventQ) {
				try {
					Event event = new Event(e);
					eventQ.put(new Event(e));
					conn.sendEvent(event);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}
	
	public void log(String msg) {
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}
}
package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;

import Client.ConnectionToGameServer;
import Client.Renderer;
import Screens.GameScreen;

public class ClientEngine implements Runnable {
	public GameState gs;
	ArrayBlockingQueue<Event> eventQ = new ArrayBlockingQueue<Event>(100);	
	Renderer renderer;
	ConnectionToGameServer gameConnection;
	public Player player;
	
	private Thread engineThread;
	private GameScreen gameScreen;

	public ClientEngine(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		gameScreen.gamePanel.setFocusable(true);
		gameScreen.gamePanel.setBackground(Color.black);
		gameScreen.gamePanel.setDoubleBuffered(true);
		engineThread = new Thread(this);
	}

	public void startGame() {
		this.renderer = new Renderer(this);
		engineThread.start();
	}

	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		gameScreen.gamePanel.addKeyListener(new GameInputHandler());

		while (gs.inGame) {
			// non-blocking?
			gs = gameScreen.gameConnection.getGameStateFromServer();
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
				if (event.type == "key event") {
					KeyEvent ke = ((KeyEvent)event.data);
					if (ke.getID() == KeyEvent.KEY_RELEASED) {
						player.tank.keyReleased(ke);
					} else if (ke.getID() == KeyEvent.KEY_PRESSED) {
						player.tank.keyPressed(ke);
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
					Event event = new Event("key event", e);
					eventQ.put(event);
					gameConnection.sendEvent(event);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		
		public void keyPressed(KeyEvent e) {
			synchronized(eventQ) {
				try {
					Event event = new Event("key event", e);
					eventQ.put(new Event("key event", e));
					gameConnection.sendEvent(event);
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
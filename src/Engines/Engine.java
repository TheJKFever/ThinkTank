package Engines;

import java.util.concurrent.ArrayBlockingQueue;

import Game.Event;
import Game.GameState;

public abstract class Engine implements Runnable {

	private Thread engineThread;
	public GameState gameState;
	public ArrayBlockingQueue<Event> eventQ;

	
	public abstract void start();
	
	public abstract void run();
	
	public abstract void processInput();
}

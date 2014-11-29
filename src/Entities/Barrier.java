package Entities;

import java.awt.Color;

import Game.GameState;

public class Barrier extends Entity {
	
	private static final long serialVersionUID = -1039864192350379044L;
	
	//	final String IMAGE_BARRIER = "images"
	public static final Color color = Color.orange;
	
    public Barrier(int x, int y, int width, int height, GameState gs) {
    	this.gs = gs;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = Integer.MAX_VALUE;
    }

	public void update() {
    	super.update();
	}
	
	public void executeCollisionWith(Entity e) {}
}

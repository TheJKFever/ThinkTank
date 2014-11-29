package Entities;

import java.awt.Color;

import Game.GameState;

public class ThoughtPool extends Entity {
	
	private static final long serialVersionUID = 708897813125520555L;

	public static final Color color = Color.pink;
	
    public ThoughtPool(int x, int y, int width, int height, GameState gs) {
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
	
	public void collideWith(Entity e) {}
}

package Entities;

import java.awt.Color;

import Game.GameState;

public class ThoughtPool extends Entity {
	
	private static final long serialVersionUID = 708897813125520555L;

	public Color color;
	public int miningRate;
	
    public ThoughtPool(int x, int y, int width, int height, GameState gs) {
    	this.gs = gs;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = Integer.MAX_VALUE;
        this.miningRate = (int)(Math.random() * 10);
        this.color = new Color(miningRate * 25, miningRate * 25, miningRate * 25);
    }

	public void update() {
    	super.update();
	}
	
	public void executeCollisionWith(Entity e) {}
}

package Entities;

import java.awt.Color;
import java.io.Serializable;

import Game.GameState;

public class Barrier extends Entity implements Serializable  {
	private static final long serialVersionUID = -1039864192350379044L;
	
	//	final String IMAGE_BARRIER = "images"
	public static final Color color = Color.orange;
	
    public Barrier(int x, int y, int width, int height, GameState gs) {
    	type = "Barrier";
    	this.gs = gs;
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        this.health = Integer.MAX_VALUE;
    }

	public void update() {
		for (Shot shot: gs.shots) {
			if (collidesWith(shot.getRect())) {
				hitBy(shot);
			}
		}
//        log("BARRIER X: " + this.x + " Y: " + this.y + " WIDTH: " + this.getWidth() + " HEIGHT: " + this.getHeight());
//        checkForCollisionWithShots();
	}
}

package Entities;

import java.io.Serializable;

import Game.GameState;
import Game.Globals;

public class Shot extends Entity implements Serializable  {

	private static final long serialVersionUID = -5027836598744895479L;
	
	static final String IMAGE_SHOT_UP = "images/shots/bulletUp.png";
    static final String IMAGE_SHOT_RIGHT = "images/shots/bulletRight.png";
    static final String IMAGE_SHOT_DOWN = "images/shots/bulletDown.png";
    static final String IMAGE_SHOT_LEFT = "images/shots/bulletLeft.png";
    static final int SHOT_WIDTH_VERTICAL = 13;
    static final int SHOT_HEIGHT_VERTICAL = 16;
    static final int SHOT_WIDTH_HORIZONTAL = 16;
    static final int SHOT_HEIGHT_HORIZONTAL = 13;
    
    int shotSpeed = 4;
    int damage = 1;
    boolean exploding = false;

    // TODO: CREATE EXPLOSION ANIMATION
    // TODO: ENSURE THAT BULLETS LAUNCH FROM THE PROPER LOCATION
    // TODO: REGULARIZE THE EXPLODING/DYING/VISIBLE METHODS
    
    public Shot(int x, int y, int theta, GameState gs) {
    	this.gs = gs;
        this.setX(x);
        this.y = y;   
        
        this.theta = theta;
        if (theta == 0) {
        	setImagePath(IMAGE_SHOT_UP);
        	width = SHOT_WIDTH_VERTICAL;
        	height = SHOT_HEIGHT_VERTICAL;
        } else if (theta == 90) {
        	setImagePath(IMAGE_SHOT_RIGHT);
        	width = SHOT_WIDTH_HORIZONTAL;
        	height = SHOT_HEIGHT_HORIZONTAL;
        }  else if (theta == 180) {
        	setImagePath(IMAGE_SHOT_DOWN);
        	width = SHOT_WIDTH_VERTICAL;
        	height = SHOT_HEIGHT_VERTICAL;
        }  else if (theta == 270) {
        	setImagePath(IMAGE_SHOT_LEFT);
        	width = SHOT_WIDTH_HORIZONTAL;
        	height = SHOT_HEIGHT_HORIZONTAL;
        }
 
    }
 
    public void update() {
//        int shotX = getX();
//        int shotY = getY();
        updatePosition();
    }
    
    public void updatePosition() {
        if (theta == 0) {
        	y -= shotSpeed;
            if (y < 0) {
                die();
            }
        } else if (theta == 180) {
        	y += shotSpeed;
        	if (y > Globals.BOARD_HEIGHT) {
        		die();
        	}
        }  else if (theta == 90) {
        	x += shotSpeed;
        	if (x > Globals.BOARD_WIDTH) {
        		die();
        	}
        }  else if (theta == 270) {
        	x -= shotSpeed;
        	if (x < 0) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }
    }
    
    public void hitSomething() {
    	this.exploding = true;
    	this.dying = true;
    	this.visible = false;
    }
}
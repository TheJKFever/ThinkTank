package Entities;

import java.io.Serializable;

import javax.swing.ImageIcon;

import Game.GameState;
import Game.Globals;

public class Shot extends Entity implements Serializable  {

	private static final long serialVersionUID = -5027836598744895479L;
	static final String IMAGE_SHOT_UP = "images/bulletUp.png";
    static final String IMAGE_SHOT_RIGHT = "images/bulletRight.png";
    static final String IMAGE_SHOT_DOWN = "images/bulletDown.png";
    static final String IMAGE_SHOT_LEFT = "images/bulletLeft.png";
    static final int SHOT_WIDTH_VERTICAL = 13;
    static final int SHOT_HEIGHT_VERTICAL = 16;
    static final int SHOT_WIDTH_HORIZONTAL = 16;
    static final int SHOT_HEIGHT_HORIZONTAL = 13;
    
    int shotSpeed = 4;
    int damage = 1;
    boolean exploding = false;

    public Shot(int x, int y, int theta, GameState gs) {
    	this.gs = gs;
        this.setX(x);
        this.y = y;
        
        this.theta = theta;
        
        ImageIcon ii = null;
        
        if (theta == 0) {
        	ii = new ImageIcon(IMAGE_SHOT_UP);
        } else if (theta == 90) {
        	ii = new ImageIcon(IMAGE_SHOT_RIGHT);
        }  else if (theta == 180) {
        	ii = new ImageIcon(IMAGE_SHOT_DOWN);
        }  else if (theta == 270) {
        	ii = new ImageIcon(IMAGE_SHOT_LEFT);
        }
        setImage(ii.getImage());
        this.setWidth(ii.getImage().getWidth(null));
    	this.setHeight(ii.getImage().getHeight(null));
    }
    
    public void update() {
        int shotX = getX();
        int shotY = getY();
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
        	if (y > Globals.BOARD_HEIGHT) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }  else if (theta == 90) {
        	x += shotSpeed;
        	if (x > Globals.BOARD_WIDTH) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }  else if (theta == 270) {
        	x -= shotSpeed;
        	if (x < 0) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }
    }
}
package Entities;

import Game.GameState;
import Global.Settings;

public class Shot extends Entity {

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
    	super.update();
    	updatePosition();
    	// TODO: replace this will barriers at the walls of the map
//    	checkForCollisionWithWalls();
    	checkForCollisionWithEntities(gs.barriers);
        checkForCollisionWithEntities(gs.brains);
        checkForCollisionWithEntities(gs.tanks);
    }
    
    public void executeCollisionWith(Entity entity) {
    	entity.hitBy(this);
    	die();
    }
    
    public void updatePosition() {
        if (theta == 0) {
        	y -= shotSpeed;
        } else if (theta == 180) {
        	y += shotSpeed;
        }  else if (theta == 90) {
        	x += shotSpeed;
        }  else if (theta == 270) {
        	x -= shotSpeed;
        }
    }
    
    public void die() {
    	// TODO: SHOT EXPLOSION ON DEATH
    	this.exploding = true;
    	this.dying = true;
    	this.visible = false;
    }
}
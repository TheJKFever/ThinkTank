package Entities;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import Game.Player;
import Game.Rect;
import Game.GameState;
import Game.Globals;
import Game.SimpleKeyEvent;
import Game.Team;

public class Tank extends Entity implements Serializable  {

	private static final long serialVersionUID = 4397815103071777225L;
	
	static final String imageDir = "images/tanks/";
    static final String IMAGE_TANK_UP = imageDir + "blue/up1.png";
    static final String IMAGE_TANK_RIGHT = imageDir + "blue/right1.png";
    static final String IMAGE_TANK_DOWN = imageDir + "blue/down1.png";
    static final String IMAGE_TANK_LEFT = imageDir + "blue/left1.png";
    
    static final int MAX_TANK_HEALTH = 10;
    static final int TANK_HEIGHT = 16;
    static final int TANK_WIDTH = 16;
    
    static final int TANK_SPAWN_X  = 0;
    static final int TANK_SPAWN_Y  = 50;
    public static int TankCount=0;
    public int tankID;
    
    public boolean firing;
    Team team;
    Player player;

    // TODO: Allow tanks to detect collisions and tank damage from shots
    // TODO: Allow tanks to die and re-spawn
    // TODO: Create explosion animation tank dies 
    // TODO: Add waiting period between re-spawns
    
    public Tank(Player p, GameState gs) {
    	this.tankID = TankCount++;
    	this.player = p;
    	this.team = player.team;
    	this.gs = gs;
    	this.gs.tanks.addElement(this);
    	this.health = MAX_TANK_HEALTH;
    	this.setWidth(TANK_HEIGHT);
        this.setHeight(TANK_WIDTH);
        this.theta = 0;
        this.dtheta = 0;
        this.firing = false;
        this.spawn(); // sets x and y
        this.updateImagePath();
    }
    
    public void spawn() {
        // TODO: Spawn tanks properly
    	this.x = 100;
//    	this.x = TANK_SPAWN_X + (50 * this.tankID);
    	if (team.num == 1) {
    		this.y = TANK_SPAWN_Y;
    	} else {
    		this.y = Globals.BOARD_HEIGHT - TANK_SPAWN_Y;
    	}
    }
    
    public void updateImagePath() {
    	if (theta == 0) {
    		this.imagePath = IMAGE_TANK_UP;
    	} else if (theta == 90) {
    		this.imagePath = IMAGE_TANK_RIGHT;
    	} else if (theta == 180) {
    		this.imagePath = IMAGE_TANK_DOWN;
    	} else if (theta == 270) {
    		this.imagePath = IMAGE_TANK_LEFT;
    	}
    }
    
    public void keyPressed(SimpleKeyEvent e) { 
    	log("TANK: KEY PRESSED");
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
        	dp = 2;
        }
        if (key == KeyEvent.VK_DOWN) {
        	dp = -2;
        }
    }

    public void keyReleased(SimpleKeyEvent e) {
    	log("TANK: KEY RELEASED");
        int key = e.getKeyCode();

        // Sets velocity back to 0 when user lets go of up/down arrow 
        if (key == KeyEvent.VK_UP) {
            dp = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dp = 0;
        }
        if (key == KeyEvent.VK_LEFT) {
        	dtheta -= 90;
        }
		if (key == KeyEvent.VK_RIGHT) {
			dtheta += 90;
		}
		// TODO: Enable tank to shoot multiple shots 
		// (use counter instead of boolean)
        if (key == KeyEvent.VK_SPACE) {
			this.firing = true;
		}
    }
    
    public void update() {
    	prevY = y;
    	prevX = x;
    	
    	updateOrientation();
    	updatePosition();
    	
    	// TODO: Subtract health when run into things?
    	checkForCollisionWithWalls();
    	checkForCollisionWithObjects(gs.tanks);
        checkForCollisionWithObjects(gs.brains);
        checkForCollisionWithObjects(gs.barriers);
        checkForCollisionWithShots();
        
        if (this.firing) {
        	fireShot();
        }
        
        // log("TANK X: " + this.x + " Y: " + this.y + " WIDTH: " + this.getWidth() + " HEIGHT: " + this.getHeight());
    }
    
    public void updateOrientation() {
    	log("TANK: Updating Orientation");
    	log("Before:");
    	log("theta = " + theta);
    	log("dtheta = " + dtheta);
    	theta = wrapDegrees(theta + dtheta); 
    	dtheta = 0; // reset to 0 after updating position
    	updateImagePath();	
    	log("After:");
    	log("theta = " + theta);
    	log("dtheta = " + dtheta);
    }
    
    public void updatePosition() {
    	log("TANK: UPDATING POSITION");
    	log("BEFORE");
    	log(this.toString());
    	
    	if (theta == 0) {
    		y -= dp;
    	} else if (theta == 180) {
    		y += dp;
    	} else if (theta == 90) {
    		x += dp;
    	} else if (theta == 270) {
    		x -= dp;
    	}
    	
    	log("AFTER");
    	log(this.toString());
    }
    
    public void checkForCollisionWithWalls() {
        if (x < 0) {
            x = 0;
        } else if (x >= (Globals.BOARD_WIDTH - width)) { 
        	setX(Globals.BOARD_WIDTH - width);
        }
        
        if (y < 0) {
        	y = 0;
        } else if (y >= Globals.BOARD_HEIGHT - height) {
        	y = (Globals.BOARD_HEIGHT - height);
        }
    }
    
    public void fireShot() {
    	int shotX = 0;
    	int shotY = 0;
    	
    	if (theta == 0) {
    		shotX = x + width/2 - Shot.SHOT_WIDTH_VERTICAL/2;
    		shotY = y - Shot.SHOT_HEIGHT_VERTICAL;
    	} else if (theta == 90) {
    		shotX = x + width;
    		shotY = y + height/2 - Shot.SHOT_HEIGHT_HORIZONTAL/2;
    	} else if (theta == 180) {
    		shotX = x + width/2 - Shot.SHOT_WIDTH_VERTICAL/2;
    		shotY = y + height;
    	} else if (theta == 270) {
    		shotX = x - Shot.SHOT_WIDTH_HORIZONTAL;
    		shotY = y + height/2 - Shot.SHOT_HEIGHT_HORIZONTAL/2;
    	}
    	
    	gs.shots.add(new Shot(shotX, shotY, theta, gs));
    	firing = false;
    }
}
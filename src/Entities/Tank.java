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

    // TODO: ALLOW TANKS TO TAKE DAMAGE FROM OTHER TANKS
    // TODO: ALLOW TANKS TO DIE AND RESPAWN
    // TODO: ADD WAITING PERIOD BETWEEN RESPAWN PERIOD
    
    public Tank(Player p, GameState gs) {
    	this.tankID = TankCount++;
    	this.player = p;
    	this.team = player.team;
    	this.gs = gs;
        gs.tanks.addElement(this);
        this.theta = 0;
        this.dtheta = 0;
        this.health = MAX_TANK_HEALTH;
        this.firing = false;
        spawn();
        updateImage(theta);
        this.setWidth(TANK_HEIGHT);
        this.setHeight(TANK_WIDTH);
    }
    
    public void spawn() {
    	this.x = 100;
//    	this.x = TANK_SPAWN_X + (50 * this.tankID);
    	if (team.num == 1) {
    		this.y = TANK_SPAWN_Y;
    	} else {
    		this.y = Globals.BOARD_HEIGHT - TANK_SPAWN_Y;
    	}
    }
    
    public void updateImage(int theta) {
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

    public void update() {
    	theta = wrapDegrees(theta + dtheta); 
    	dtheta = 0;
    	updateImage(theta);
    	
    	prevY = y;
    	prevX = x;
    	
    	// apply movement
    	if (theta == 0) {
    		setY(getY() - dp);
    	} else if (theta == 180) {
    		setY(getY() + dp);
    	} else if (theta == 90) {
    		setX(getX() + dp);
    	} else if (theta == 270) {
    		setX(getX() - dp);
    	}
    	
    	// check for collision with walls
        if (x <= 2) 
            setX(2);
        if (x >= Globals.BOARD_WIDTH - getWidth()) { 
        	setX(Globals.BOARD_WIDTH - getWidth());
        }
        
        if (y < 0) {
        	setY(0);
        } else if (y >= Globals.BOARD_HEIGHT - 50) {
        	setY(Globals.BOARD_HEIGHT - 50);
        }
        
        checkForCollisionWithShots();
        checkForCollisionWithObstacles(gs.brains);
        checkForCollisionWithObstacles(gs.barriers);
        
        if (this.firing) {
        	fireShot();
        }
        
//        log("TANK X: " + this.x + " Y: " + this.y + " WIDTH: " + this.getWidth() + " HEIGHT: " + this.getHeight());
    }
    
    public void fireShot() {
    	int shotX = 0;
    	int shotY = 0;
    	
    	if (theta == 0) {
    		shotX = this.x + this.getWidth()/2 - Shot.SHOT_WIDTH_VERTICAL/2;
    		shotY = this.y - Shot.SHOT_HEIGHT_VERTICAL;
    	} else if (theta == 90) {
    		shotX = this.x + this.getWidth();
    		shotY = this.y + this.getHeight()/2 - Shot.SHOT_HEIGHT_HORIZONTAL/2;
    	} else if (theta == 180) {
    		shotX = this.x + this.getWidth()/2 - Shot.SHOT_WIDTH_VERTICAL/2;
    		shotY = this.y + this.getHeight();
    	} else if (theta == 270) {
    		shotX = this.x - Shot.SHOT_WIDTH_HORIZONTAL;
    		shotY = this.y + this.getHeight()/2 - Shot.SHOT_HEIGHT_HORIZONTAL/2;
    	}
    	
    	gs.shots.add(new Shot(shotX, shotY, this.theta, this.gs));
    	this.firing = false;
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

        if (key == KeyEvent.VK_UP) {
            dp = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dp = 0;
        }
        if (key == KeyEvent.VK_LEFT) {
			  dtheta = -90;
        }
		if (key == KeyEvent.VK_RIGHT) {
			  dtheta = 90;
		}
        if (key == KeyEvent.VK_SPACE) {
			this.firing = true;
		}
    }
    
    public boolean collidesWith(Rect other) {
		boolean yCollision = false;
		boolean xCollision = false;
		
		Rect my = this.getRect();
		
		if (my.top <= other.bottom && my.top >= other.top) {
			yCollision = true;
		} else if (my.bottom >= other.top && my.bottom <= other.bottom) {
			yCollision = true;
		}

		if (my.right >= other.left && my.right <= other.right) {
			xCollision = true;
		} else if (my.left <= other.right && my.left >= other.left) {
			xCollision = true;
		}
		
		
		return (xCollision && yCollision);
	}
}
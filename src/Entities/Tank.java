package Entities;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Game.Player;
import Game.Rect;
import Game.GameState;
import Game.Globals;
import Game.Team;

public class Tank extends Entity {

    static final String imageDir = "images/tanks/";
    static final String IMAGE_TANK_UP = imageDir + "blue/up1.png";
    static final String IMAGE_TANK_RIGHT = imageDir + "blue/right1.png";
    static final String IMAGE_TANK_DOWN = imageDir + "blue/down1.png";
    static final String IMAGE_TANK_LEFT = imageDir + "blue/left1.png";
    
    static final int MAX_TANK_HEALTH = 10;
    static final int TANK_HEIGHT = 16;
    static final int TANK_WIDTH = 16;
    
    static final int TANK_SPAWN_X  = 0;
    static final int TANK_SPAWN_Y  = 0;
    public int tankNum;
    
    public boolean firing;
    Team team;
    Player player;
    
    public Tank(int tankNum, Player p, GameState gs) {
    	this.tankNum = tankNum;
    	this.player = p;
    	this.team = player.team;
    	this.gs = gs;
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
    	this.x = TANK_SPAWN_X + (50 * this.tankNum);
    	if (team.teamNumber == 1) {
    		this.y = TANK_SPAWN_Y;
    	} else {
    		this.y = Globals.BOARD_HEIGHT - TANK_SPAWN_Y;
    	}
    }
    public void updateImage(int theta) {
    	String filename = null;
    	if (theta == 0) {
    		filename = IMAGE_TANK_UP;
    	} else if (theta == 90) {
    		filename = IMAGE_TANK_RIGHT;
    	} else if (theta == 180) {
    		filename = IMAGE_TANK_DOWN;
    	} else if (theta == 270) {
    		filename = IMAGE_TANK_LEFT;
    	}
    	ImageIcon ii = new ImageIcon(filename);
    	this.setImage(ii.getImage());
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
    
    public void keyPressed(KeyEvent e) { 
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
        	dp = 2;
        }
        
        if (key == KeyEvent.VK_DOWN) {
        	dp = -2;
        }
    }

    public void keyReleased(KeyEvent e) {
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
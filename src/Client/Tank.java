package Client;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tank extends Entity {

    private final int START_Y = 280; 
    private final int START_X = 270;

    static final String imageDir = "images/tanks/";
    static final String IMAGE_TANK_UP = imageDir + "blue/up1.png";
    static final String IMAGE_TANK_RIGHT = imageDir + "blue/right1.png";
    static final String IMAGE_TANK_DOWN = imageDir + "blue/down1.png";
    static final String IMAGE_TANK_LEFT = imageDir + "blue/left1.png";
    
    static final int MAX_TANK_HEALTH = 10;
    
    public boolean firing;
    
    public Tank(Game game) {
    	type = "Tank";
    	this.game = game;
        setX(START_X);
        setY(START_Y);
        this.theta = 0;
        this.dtheta = 0;
        this.health = MAX_TANK_HEALTH;
        this.firing = false;
        
        updateImage(theta);

//        ImageIcon ii = new ImageIcon(IMAGE_TANK_UP);
//        this.width = ii.getImage().getWidth(null); 
//        this.height = ii.getImage().getWidth(null);
        this.setWidth(16);
        this.setHeight(16);
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
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(filename));
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
        checkForCollisionWithObstacles(game.brains);
        checkForCollisionWithObstacles(game.barriers);
        
        if (this.firing) {
        	fireShot();
        }
        
//        log("TANK X: " + this.x + " Y: " + this.y + " WIDTH: " + this.getWidth() + " HEIGHT: " + this.getHeight());
    }
    
    public void fireShot() {
    	int shotX = 0;
    	int shotY = 0;
    	
    	if (theta == 0) {
    		shotX = this.x + this.getWidth()/2;
    		shotY = this.y;
    	} else if (theta == 90) {
    		shotX = this.x + this.getWidth();
    		shotY = this.y + this.getHeight()/2;
    	} else if (theta == 180) {
    		shotX = this.x + this.getWidth()/2;
    		shotY = this.y + this.getHeight();
    	} else if (theta == 270) {
    		shotX = this.x;
    		shotY = this.y + this.getWidth()/2;
    	}
    	
    	game.shots.add(new Shot(shotX, shotY, this.theta, this.game));
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
		
        if (key == KeyEvent.VK_ALT) {
			this.firing = true;
		}
    }
    
    public boolean collidesWith(GameRect other) {
		boolean yCollision = false;
		boolean xCollision = false;
		
		GameRect my = this.getRect();
		
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
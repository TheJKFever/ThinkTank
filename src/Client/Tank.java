package Client;

import java.awt.event.KeyEvent;

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
    
    public Tank(Game game) {
    	type = "Tank";
    	this.game = game;
        setX(START_X);
        setY(START_Y);
        this.theta = 0;
        this.dtheta = 0;
        this.health = MAX_TANK_HEALTH;
        
        updateImage(theta);

        ImageIcon ii = new ImageIcon(IMAGE_TANK_UP);
        this.width = ii.getImage().getWidth(null); 
        this.height = ii.getImage().getWidth(null); 
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
    	
    	if (theta == 0) {
    		y -= dp;
    	} else if (theta == 180) {
    		y += dp;
    	} else if (theta == 90) {
    		x += dp;
    	} else if (theta == 270) {
    		x -= dp;
    	}
    	
        if (x <= 2) 
            x = 2;
        if (x >= Globals.BOARD_WIDTH - 2*width) { 
            x = Globals.BOARD_WIDTH - 2*width;
        }
        
        if (y < 0) {
        	y = 0;
        } else if (y >= Globals.BOARD_HEIGHT - 50) {
        	y = Globals.BOARD_HEIGHT - 50;
        }
        
        checkForCollisionWithShots();
        checkForCollisionWithBrains();
    }
    
    public void checkForCollisionWithBrains() {
    	for (Brain brain: game.brains) {
    		
    		GameRect brainRect = brain.getRect();
    		
			if (collidesWith(brainRect)) {
				if (theta == 0) {
					this.y = brainRect.bottom; 
				} else if (theta == 90) {
					this.x = brainRect.left; 
				} else if (theta == 180) {
					this.y = brainRect.top; 
				} else if (theta == 270) {
					this.x = brainRect.right; 
				}
			}
		}
    }

    
    public int wrapDegrees(int d) {
    	if (d < 0) {
    		d += 360;
    	} else if (d >= 360) {
    		d -= 360;
    	}
    	return d;
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

        if (key == KeyEvent.VK_UP)
        {
            dp = 0;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dp = 0;
        }
        
      if (key == KeyEvent.VK_LEFT)
      {
    	  dtheta = -90;
      }

      if (key == KeyEvent.VK_RIGHT)
      {
    	  dtheta = 90;
      }
      
    }
}
package Client;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public abstract class Entity {

    boolean visible;
    Image image;
    boolean dying;
    
    int x;
    int y;
    int dx;
    int dy;
    int dp;
    int prevX;
    int prevY;
    int theta = 0;
    int dtheta;
    
    int height;
    int width;
    
    int health;
    String type;
    Game game;
    
    public Entity() {
        visible = true;
    }
    
//    public abstract void update();

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
    
    public void checkForCollisionWithShots() {
		for (Shot shot: game.shots) {
			if (shot.x >= this.x && 
				shot.x <= (this.x + this.width) &&
				shot.y >= this.y && 
				shot.y <= (this.y + this.height)) {
				hitBy(shot);
			}
		}
    }
    
	public void hitBy(Shot shot) {
		this.health -= shot.damage;
		log(this.type + " HEALTH = " + health);
		if (this.health == 0) {
			this.setDying(true);
		}
		shot.exploding = true;
		shot.dying = true;
	}
	
	public GameRect getRect() {
		return new GameRect(this.x, this.y, this.width, this.height);
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
	
    public void resetPositionOnCollision(GameRect rect) {
    	int deltaY = y - prevY;
    	int deltaX = x -  prevX;
    	
    	if (deltaY != 0) {
			if (deltaY > 0) {
				this.y = rect.top - 10;
			} else {
				this.y = rect.bottom + 10;
			}
    	} else if (deltaX != 0) {
			if (deltaX > 0) {
				this.x = rect.left - 10;
			} else {
				this.x = rect.right + 10;	
			}
		}
    }
    
	public void checkForCollisionWithObstacles(ArrayList<? extends Entity> obstacles) {
    	for (Entity obstacle: obstacles) {
    		
    		GameRect rect = obstacle.getRect();
			if (collidesWith(rect)) {
				resetPositionOnCollision(rect);
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
    
	public void log(String msg) {
		if (Globals.DEBUG) {
			System.out.println(msg);
		}
	}
}

package Entities;

import java.io.Serializable;
import java.util.Vector;

import Game.Rect;
import Game.GameState;
import Game.Globals;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -4882402415044435970L;
	
	public boolean visible;
    public String imagePath;
    public boolean dying;
    
    public int x, y, dx, dy, dp, prevX, prevY, theta, dtheta, height, width, health;
    GameState gs;
    
    public Entity() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
    
    public void checkForCollisionWithShots() {
		for (Shot shot: gs.shots) {
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
		log(this.getClass().getName() + " HEALTH = " + health);
		if (this.health == 0) {
			this.setDying(true);
		}
		shot.hitSomething();
	}
	
	public Rect getRect() {
		return new Rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
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
		
        log("MY X: " + my.x + " Y: " + my.y + " WIDTH: " + my.width + " HEIGHT: " + my.height);
        log("OT X: " + other.x + " Y: " + other.y + " WIDTH: " + other.width + " HEIGHT: " + other.height);

		return (xCollision && yCollision);
	}
	
    public void resetPositionOnCollision(Rect rect) {
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
    
	public void checkForCollisionWithObstacles(Vector<? extends Entity> obstacles) {
    	for (Entity obstacle: obstacles) {
    		
    		Rect rect = obstacle.getRect();
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
	
	@Override
	public String toString() {
		return this.getClass().getName() + " {\n\tx: " + x + ", y: " + y + ", theta: " + theta + ",\n\theight: " + height + ", width: " + width + ",\n\thealth: " + health + ", visible: " + visible + "\n}\n";
	}
}

package Entities;

import java.io.Serializable;
import java.util.Vector;

import Game.Rect;
import Game.GameState;
import Global.Settings;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -4882402415044435970L;
	
	GameState gs;
	public boolean visible;
    public String imagePath;
    public boolean dying;
    public int x, y, dx, dy, dp, prevX, prevY, theta, dtheta, height, width, health;    
    public boolean yCollision = false;
    public boolean xCollision = false;
    public boolean topCollision = false;
	public boolean bottomCollision = false;
	public boolean leftCollision = false;
	public boolean rightCollision = false;
	public boolean xCollisionPrev = false;
	public boolean yCollisionPrev = false;
    
    public Entity() {
        visible = true;
        x = 0;
        y = 0;
        dx = 0;
        dy = 0;
        dp = 0;
        prevX = 0;
        prevY = 0;
        theta = 0;
        dtheta = 0;
        health = 0;
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
		log(this.getClass().getName() + ": collidesWith()");
		
		// store previous values
		yCollisionPrev = new Boolean(yCollision);
		xCollisionPrev = new Boolean(xCollision);
		
		// reset values
		yCollision = false;
		xCollision = false;
		leftCollision = false;
		rightCollision = false;
		topCollision = false;
		bottomCollision = false;
		
		Rect my = this.getRect();
		
		if (my.top <= other.bottom && my.top >= other.top) {
			yCollision = true;
			topCollision = true;
		} else if (my.bottom >= other.top && my.bottom <= other.bottom) {
			yCollision = true;
			bottomCollision = true;
		}

		if (my.right >= other.left && my.right <= other.right) {
			xCollision = true;
			rightCollision = true;
		} else if (my.left <= other.right && my.left >= other.left) {
			xCollision = true;
			leftCollision = true;
		}
		
//        log("MY X: " + my.x + " Y: " + my.y + " WIDTH: " + my.width + " HEIGHT: " + my.height);
//        log("OT X: " + other.x + " Y: " + other.y + " WIDTH: " + other.width + " HEIGHT: " + other.height);

		log(this.getClass().getName() + " xCollision: " + xCollision + " yCollision: " + yCollision);
		boolean collision = xCollision && yCollision;
		if (collision) {
			log("COLLISION");
		}
		return collision;
	}
	
    public void resetPositionOnCollision(Rect rect) {
    	log(this.getClass().getName() + ": resetPositionOnCollision()");
    	
    	if (xCollisionPrev) {
    		if (topCollision) {
    			y = rect.bottom + 1;
    		} else if (bottomCollision) {
    			y = rect.top - height - 1;
    		}
    		this.yCollision = false;
    	} else if (yCollisionPrev) {
    		if (rightCollision) {
    			x = rect.left - width - 1;
    		} else if (leftCollision){
    			x = rect.right + 1;
    		}
    		this.xCollision = false;
    	}	
    }
    
	public void checkForCollisionWithObjects(Vector<? extends Entity> obstacles) {
    	for (Entity obstacle: obstacles) {
    		
    		Rect rect = obstacle.getRect();
			if (collidesWith(rect)) {
				resetPositionOnCollision(rect);
			}
		}
    }

    public int wrapDegrees(int d) {
    	while (d < 0) {
    		d += 360;
    	}
    	while (d >= 360) {
    		d -= 360;
    	}
    	return d;
    }
    
	public void log(String msg) {
		if (Settings.DEBUG) {
			System.out.println(msg);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + " {\n\tx: " + x + ", y: " + y + ", theta: " + theta + "\n\tdp: " + dp + ", dx: " + dx + ", dy: " + dy + ", dtheta: " + dtheta + ",\n\theight: " + height + ", width: " + width + ",\n\thealth: " + health + ", visible: " + visible + "\n}\n";
	}
}

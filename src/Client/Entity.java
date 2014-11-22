package Client;

import java.awt.Image;
import java.awt.Rectangle;

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
    
    public void log(String msg) {
    	if (Globals.DEBUG) {
    		System.out.println(msg);
    	}
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
}

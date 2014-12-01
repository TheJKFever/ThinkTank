package Entities;

import java.io.Serializable;
import java.util.Vector;

import Game.GameState;
import Game.Helper;
import Game.Player;
import Game.Rect;
import Game.Team;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -4882402415044435970L;

	GameState gs;
	public boolean visible;
	public String imagePath;
	public boolean dying;
	public boolean exploding;
	public int x, y, dx, dy, dp, prevX, prevY, theta, dtheta, height, width, health;    

	public boolean yCollision = false;
	public boolean xCollision = false;

	public boolean topCollision = false;
	public boolean bottomCollision = false;
	public boolean leftCollision = false;
	public boolean rightCollision = false;

	public Team team;
	public Player player;
	
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
		exploding = false;
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

	public void hitBy(Shot shot) {
		this.health -= shot.weapon.damage;
		shot.player.numHits++;
		
		if (this.health == 0) {
			this.die();
			shot.player.numKills++;
		}
	}

	public Rect getRect() {
		return new Rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public void update() {
		prevX = x;
		prevY = y;
	}

	public boolean checkForCollisionWith(Rect other) {
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
		} else if (other.top <= my.bottom && other.top >= my.top) {
			yCollision = true;
			topCollision = true;
		} else if (my.bottom >= other.top && my.bottom <= other.bottom) {
			yCollision = true;
			bottomCollision = true;
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

		return (xCollision && yCollision);
	}

	public void resetPositionOnCollision(Rect rect) {
//		Helper.log(this.getClass().getName() + ": resetPositionOnCollision()");
//		Helper.log("xCollision: " + xCollision + " yCollision: " + yCollision);

		boolean movedUp = false;
		boolean movedDown = false;
		boolean movedLeft = false;
		boolean movedRight = false;

		if (y - prevY > 0) {
			movedDown = true;
		} else if (y - prevY < 0) {
			movedUp = true;
		}

		if (x - prevX > 0) {
			movedRight = true;
		} else if (x - prevX < 0) {
			movedLeft = true;
		}

		if (movedUp) {
			y = rect.bottom + 1;
		} else if (movedDown) {
			y = rect.top - height - 1;
		}

		if (movedRight) {
			x = rect.left - width - 1;
		} else if (movedLeft){
			x = rect.right + 1;
		}	
	}

	public void checkForCollisionWithEntities(Vector<? extends Entity> entities) {
		for (Entity entity: entities) {
			if ((this.hashCode() != entity.hashCode())) {
				if (checkForCollisionWith(entity.getRect())) {
					executeCollisionWith(entity);	
				}
			}
		}
	}

	public abstract void executeCollisionWith(Entity e);

	public int wrapDegrees(int d) {
		while (d < 0) {
			d += 360;
		}
		while (d >= 360) {
			d -= 360;
		}
		return d;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " {\n\tx: " + x + ", y: " + y + ", theta: " + theta + "\n\tdp: " + dp + ", dx: " + dx + ", dy: " + dy + ", dtheta: " + dtheta + ",\n\theight: " + height + ", width: " + width + ",\n\thealth: " + health + ", visible: " + visible + "\n}\n";
	}
}

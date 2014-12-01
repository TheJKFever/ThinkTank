package Entities;

import java.awt.event.KeyEvent;

import Game.GameState;
import Game.Helper;
import Game.Player;
import Game.SimpleKeyEvent;
import Game.Team;
import Global.Settings;

public class Tank extends Entity {

	private static final long serialVersionUID = 4397815103071777225L;

	static final String IMAGE_TANK_UP_BLUE = "images/tanks/blue/up1.png";
	static final String IMAGE_TANK_RIGHT_BLUE = "images/tanks/blue/right1.png";
	static final String IMAGE_TANK_DOWN_BLUE = "images/tanks/blue/down1.png";
	static final String IMAGE_TANK_LEFT_BLUE = "images/tanks/blue/left1.png";

	static final String IMAGE_TANK_UP_RED = "images/tanks/red/up1.png";
	static final String IMAGE_TANK_RIGHT_RED = "images/tanks/red/right1.png";
	static final String IMAGE_TANK_DOWN_RED = "images/tanks/red/down1.png";
	static final String IMAGE_TANK_LEFT_RED = "images/tanks/red/left1.png";
	
	static final int MAX_TANK_HEALTH = 10;
	static final int TANK_HEIGHT = 16;
	static final int TANK_WIDTH = 16;

	static final int TANK_SPAWN_X = 0;
	static final int TANK_SPAWN_Y = 50;

	public static int TankCount = 0;

	public int weaponType;

	public int tankID;
	public boolean firing;
	public boolean mining;
	public int thoughts;

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
		this.thoughts = 0;
		this.mining = false;
		this.weaponType = Settings.DEFAULT_WEAPON;
	}

	public void spawn() {
		// TODO: Spawn tanks properly
		this.x = 100;
		 this.x = TANK_SPAWN_X + (50 * this.tankID/2);
		if (team.num == 1) {
			this.y = TANK_SPAWN_Y;
		} else {
			this.y = Settings.BOARD_HEIGHT - TANK_SPAWN_Y;
		}
	}

	public void updateImagePath() {
		
		if (team.num == 1) {
			if (theta == 0) {
				this.imagePath = IMAGE_TANK_UP_BLUE;
			} else if (theta == 90) {
				this.imagePath = IMAGE_TANK_RIGHT_BLUE;
			} else if (theta == 180) {
				this.imagePath = IMAGE_TANK_DOWN_BLUE;
			} else if (theta == 270) {
				this.imagePath = IMAGE_TANK_LEFT_BLUE;
			}
		} else {
			if (theta == 0) {
				this.imagePath = IMAGE_TANK_UP_RED;
			} else if (theta == 90) {
				this.imagePath = IMAGE_TANK_RIGHT_RED;
			} else if (theta == 180) {
				this.imagePath = IMAGE_TANK_DOWN_RED;
			} else if (theta == 270) {
				this.imagePath = IMAGE_TANK_LEFT_RED;
			}
		}
	}

	public void keyPressed(SimpleKeyEvent e) {
		Helper.log("TANK: KEY PRESSED");
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			dp = 2;
		}
		if (key == KeyEvent.VK_DOWN) {
			dp = -2;
		}
		if (key == KeyEvent.VK_M) {
			mining = true;
		}
	}

	public void keyReleased(SimpleKeyEvent e) {
		Helper.log("TANK: KEY RELEASED");
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
		if (key == KeyEvent.VK_M) {
			mining = false;
		}

		if (key == KeyEvent.VK_SPACE) {
			this.firing = true;
		}
	}

	public void update() {
		super.update();
		// Helper.log("TANK: UPDATE() BEFORE");
		// Helper.log(this.toString());

		updateOrientation();
		updatePosition();

		// TODO: Subtract health when run into things?
		checkForCollisionWithEntities(gs.barriers);
		checkForCollisionWithEntities(gs.brains);
		checkForCollisionWithEntities(gs.tanks);

		if (this.mining) {
			mineForThoughts();
		} else if (this.firing) {
			fireShot();
		}
	}

	public void mineForThoughts() {
		for (ThoughtPool thoughtPool : gs.thoughtPools) {
			if (checkForCollisionWith(thoughtPool.getRect())) {
				this.thoughts += thoughtPool.miningRate;
			}
		}
	}

	public void updateOrientation() {
		theta = wrapDegrees(theta + dtheta);
		dtheta = 0; // reset to 0 after updating position
		updateImagePath();
	}

	public void updatePosition() {

		if (theta == 0) {
			y -= dp;
		} else if (theta == 180) {
			y += dp;
		} else if (theta == 90) {
			x += dp;
		} else if (theta == 270) {
			x -= dp;
		}
	}

	public void executeCollisionWith(Entity entity) {
		resetPositionOnCollision(entity.getRect());
	}

	public void die() {
		this.player.numDeaths++;
		// TODO: TANK DEATH
		visible = false;
		exploding = true;
	}

	public void updateWeapon(int type) {
		this.weaponType = type;
	}

	public void fireShot() {
		this.player.numShots++;
		gs.shots.add(new Shot(x, y, theta, gs, this.player, this.weaponType));
		firing = false;
	}
}
package Entities;

import javax.swing.ImageIcon;

import Game.GameState;
import Global.Settings;

public class Shot extends Entity {
	/*
	 * static final String IMAGE_SHOT_UP = "images/bulletUp.png"; static final
	 * String IMAGE_SHOT_RIGHT = "images/bulletRight.png"; static final String
	 * IMAGE_SHOT_DOWN = "images/bulletDown.png"; static final String
	 * IMAGE_SHOT_LEFT = "images/bulletLeft.png";
	 */
	// int shotSpeed = 4;
	// int damage = 1;
	// boolean exploding = false;

	private static final long serialVersionUID = 5824474896043889830L;
	static final int SHOT_WIDTH_VERTICAL = 13;
    static final int SHOT_HEIGHT_VERTICAL = 16;
    static final int SHOT_WIDTH_HORIZONTAL = 16;
    static final int SHOT_HEIGHT_HORIZONTAL = 13;
    
	// ADDED STUFF
	public Weapon weapon;
	private int weaponType = Settings.DEFAULT_WEAPON;

	// ADDED STUFF

	public Weapon getWeapon() {

		return weapon;

	}

	public void upgradeWeapon(int weapontype) {

		this.weaponType = weapontype;

	}

	

	public Shot(int x, int y, int theta, GameState gs,int weaponType) {
		this.gs = gs;
		this.setX(x);
		this.y = y;
		this.weaponType = weaponType;
		this.theta = theta;

		ImageIcon ii = null;

		if (this.weaponType == Settings.DEFAULT_WEAPON) {

			this.weapon = new DefaultWeapon(theta);

		} else if (this.weaponType == Settings.DOUBLE_WEAPON) {

			this.weapon = new DoubleStrengthWeapon();

		} else if (this.weaponType == Settings.TRIPLE_WEAPON) {

			this.weapon = new TripleStrengthWeapon();

		} else if (this.weaponType == Settings.SUPER_WEAPON) {

			this.weapon = new SuperWeapon();

		}

		ii = new ImageIcon(this.weapon.getImagePath());

		setImage(ii.getImage());
		this.setWidth(ii.getImage().getWidth(null));
		this.setHeight(ii.getImage().getHeight(null));
	}

	public void update() {
		super.update();
    	updatePosition();
    	// TODO: replace this will barriers at the walls of the map
//    	checkForCollisionWithWalls();
    	checkForCollisionWithEntities(gs.barriers);
        checkForCollisionWithEntities(gs.brains);
        checkForCollisionWithEntities(gs.tanks);
    
	}
	
	public void executeCollisionWith(Entity entity) {
    	entity.hitBy(this);
    	die();
    }

	public void updatePosition() {
        if (theta == 0) {
        	y -= weapon.shotSpeed;
        } else if (theta == 180) {
        	y += weapon.shotSpeed;
        }  else if (theta == 90) {
        	x += weapon.shotSpeed;
        }  else if (theta == 270) {
        	x -= weapon.shotSpeed;
        }
    }
	
	public void die() {
    	// TODO: SHOT EXPLOSION ON DEATH
    	this.exploding = true;
    	this.dying = true;
    	this.visible = false;
    }
}
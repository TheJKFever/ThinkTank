package Entities;

import javax.swing.ImageIcon;

import Client.GameState;
import Client.Globals;

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

	// ADDED STUFF
	private Weapon weapon;
	private int weaponType = Globals.DEFAULT_WEAPON;

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

		if (this.weaponType == Globals.DEFAULT_WEAPON) {

			this.weapon = new DefaultWeapon(theta);

		} else if (this.weaponType == Globals.DOUBLE_WEAPON) {

			this.weapon = new DoubleStrengthWeapon();

		} else if (this.weaponType == Globals.TRIPLE_WEAPON) {

			this.weapon = new TripleStrengthWeapon();

		} else if (this.weaponType == Globals.SUPER_WEAPON) {

			this.weapon = new SuperWeapon();

		}

		ii = new ImageIcon(this.weapon.getImagePath());

		setImage(ii.getImage());
		this.setWidth(ii.getImage().getWidth(null));
		this.setHeight(ii.getImage().getHeight(null));
	}

	public void update() {
		int shotX = getX();
		int shotY = getY();
		updatePosition();
	}

	public void updatePosition() {
		if (theta == 0) {
			y -= this.weapon.getShotSpeend();
			if (y < 0) {
				die();
			}
		} else if (theta == 180) {
			y += this.weapon.getShotSpeend();
			if (y > Globals.BOARD_HEIGHT) { // TODO: FIX THIS TO BE SOME GLOBAL
											// CONSTANT
				die();
			}
		} else if (theta == 90) {
			x += this.weapon.getShotSpeend();
			if (x > Globals.BOARD_WIDTH) { // TODO: FIX THIS TO BE SOME GLOBAL
											// CONSTANT
				die();
			}
		} else if (theta == 270) {
			x -= this.weapon.getShotSpeend();
			if (x < 0) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
				die();
			}
		}
	}
}
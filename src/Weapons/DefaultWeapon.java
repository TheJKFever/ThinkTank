package Weapons;

import java.io.Serializable;


public class DefaultWeapon extends Weapon implements Serializable {

	private static final long serialVersionUID = 5906015376417876273L;
	
	public DefaultWeapon(int theta) {
		WIDTH_VERTICAL = 13;
		HEIGHT_VERTICAL = 16;
		WIDTH_HORIZONTAL = 16;
		HEIGHT_HORIZONTAL = 13;
		
		this.theta = theta;

		this.shotSpeed = 4;
		this.damage = 1;
		exploding = false;
		
		if (theta == 0) {
			this.imagePath = "images/shots/defaultUp.png";
			this.height = HEIGHT_VERTICAL;
			this.width = WIDTH_VERTICAL;
		} else if (theta == 90) {
			this.imagePath = "images/shots/defaultRight.png";
			this.height = HEIGHT_HORIZONTAL;
			this.width = WIDTH_HORIZONTAL;
		} else if (theta == 180) {
			this.imagePath = "images/shots/defaultDown.png";
			this.height = HEIGHT_VERTICAL;
			this.width = WIDTH_VERTICAL;
		} else if (theta == 270) {
			this.imagePath = "images/shots/defaultLeft.png";
			this.height = HEIGHT_HORIZONTAL;
			this.width = WIDTH_HORIZONTAL;
		}

	}
	
}

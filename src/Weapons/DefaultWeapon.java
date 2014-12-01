package Weapons;

import java.io.Serializable;


public class DefaultWeapon extends Weapon implements Serializable {

	private static final long serialVersionUID = 5906015376417876273L;
	
	public static final int WIDTH_VERTICAL = 13;
	public static final int HEIGHT_VERTICAL = 16;
	public static final int WIDTH_HORIZONTAL = 16;
	public static final int HEIGHT_HORIZONTAL = 13;
    
	public DefaultWeapon(int theta) {

		this.shotSpeed = 4;
		this.damage = 1;
		exploding = false;
		
		if (theta == 0) {
			this.imagePath = "images/shots/defaultUp.png";
			this.height = DefaultWeapon.HEIGHT_VERTICAL;
			this.width = DefaultWeapon.WIDTH_VERTICAL;
		} else if (theta == 90) {
			this.imagePath = "images/shots/defaultRight.png";
			this.height = DefaultWeapon.HEIGHT_HORIZONTAL;
			this.width = DefaultWeapon.WIDTH_HORIZONTAL;
		} else if (theta == 180) {
			this.imagePath = "images/shots/defaultDown.png";
			this.height = DefaultWeapon.HEIGHT_VERTICAL;
			this.width = DefaultWeapon.WIDTH_VERTICAL;
		} else if (theta == 270) {
			this.imagePath = "images/shots/defaultLeft.png";
			this.height = DefaultWeapon.HEIGHT_HORIZONTAL;
			this.width = DefaultWeapon.WIDTH_HORIZONTAL;
		}

	}
	
}

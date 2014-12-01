package Weapons;

import java.io.Serializable;

public class SuperWeapon extends Weapon implements Serializable {
	
	private static final long serialVersionUID = -3779413651701962445L;
	
	public SuperWeapon(int theta) {
		WIDTH_VERTICAL = 16;
		HEIGHT_VERTICAL = 16;
		WIDTH_HORIZONTAL = 16;
		HEIGHT_HORIZONTAL = 16;
		
		this.theta = theta;
		
		this.shotSpeed = 8;
		this.damage = 2;
		exploding = false;

		this.imagePath = "images/shots/Super.png";
		this.width = 16;
		this.height = 16;
	}
	
}

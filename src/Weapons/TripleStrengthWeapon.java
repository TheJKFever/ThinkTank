package Weapons;

import java.io.Serializable;

public class TripleStrengthWeapon extends Weapon implements Serializable {

	private static final long serialVersionUID = -3832398223187946474L;
	
	public TripleStrengthWeapon(int theta) {
		WIDTH_VERTICAL = 16;
		HEIGHT_VERTICAL = 16;
		WIDTH_HORIZONTAL = 16;
		HEIGHT_HORIZONTAL = 16;
		
		this.theta = theta;
		
		this.shotSpeed = 8;
		this.damage = 2;
		exploding = false;

		this.imagePath = "images/shots/Triple.png";
		
		this.width = 12;
		this.height = 12;		
	}
	
}

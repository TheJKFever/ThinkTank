package Weapons;

import java.io.Serializable;

public class SuperWeapon extends Weapon implements Serializable {
	
	private static final long serialVersionUID = -3779413651701962445L;
	
	public static final int WIDTH_VERTICAL = 16;
	public static final int HEIGHT_VERTICAL = 16;
	public static final int WIDTH_HORIZONTAL = 16;
	public static final int HEIGHT_HORIZONTAL = 16;
	
	public SuperWeapon(int theta) {

		this.theta = theta;
		
		this.shotSpeed = 8;
		this.damage = 2;
		exploding = false;

		this.imagePath = "images/shots/Super.png";
		this.width = 16;
		this.height = 16;
	}
	
}

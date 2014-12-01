package Weapons;

import java.io.Serializable;

public class DoubleStrengthWeapon extends Weapon implements Serializable {

	private static final long serialVersionUID = 4076791396132078903L;
	
	public static final int WIDTH_VERTICAL = 12;
	public static final int HEIGHT_VERTICAL = 12;
	public static final int WIDTH_HORIZONTAL = 12;
	public static final int HEIGHT_HORIZONTAL = 12;
	
	public DoubleStrengthWeapon() {

		this.shotSpeed = 8;
		this.damage = 2;
		exploding = false;

		this.imagePath = "images/shots/Double.png";
		this.width = 12;
		this.height = 12;
	}
	
}
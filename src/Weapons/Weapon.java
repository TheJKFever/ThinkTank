package Weapons;

import java.io.Serializable;

public abstract class Weapon implements Serializable {

	private static final long serialVersionUID = 1473438964608702421L;
	
	public int WIDTH_VERTICAL = 0;
	public int HEIGHT_VERTICAL = 0;
	public int WIDTH_HORIZONTAL = 0;
	public int HEIGHT_HORIZONTAL = 0;
	
	public int theta;
	
	public int shotSpeed;
	public int damage;
	public boolean exploding;
	public String imagePath;
	public int width;
	public int height;

	public int getShotSpeed(){
		return this.shotSpeed;
	}

	public int getDamage(){
		return this.damage;
	}
	
	public boolean getExploding(){
		return this.exploding;
	}
	
	public void setExploding(){
		this.exploding = !this.exploding;
	}
	
	public String getImagePath(){
		return this.imagePath;
	}
}

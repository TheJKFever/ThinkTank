package Entities;


public class DefaultWeapon extends Weapon {

	DefaultWeapon(int theta) {

		this.shotSpeed = 4;
		this.damage = 1;
		exploding = false;

		if (theta == 0) {
			this.imagePath = "images/bulletUp.png";
		} else if (theta == 90) {
			this.imagePath = "images/bulletRight.png";
		} else if (theta == 180) {
			this.imagePath = "images/bulletDown.png";
		} else if (theta == 270) {
			this.imagePath = "images/bulletLeft.png";
		}

	}
	
	public int getShotSpeend(){
		
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

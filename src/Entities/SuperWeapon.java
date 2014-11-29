package Entities;

public class SuperWeapon extends Weapon{
	SuperWeapon() {

		this.shotSpeed = 8;
		this.damage = 2;
		exploding = false;

		
		this.imagePath = "images/Super.png";
		

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

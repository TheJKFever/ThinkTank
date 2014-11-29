package Entities;

public class DoubleStrengthWeapon extends Weapon{

	DoubleStrengthWeapon() {

		this.shotSpeed = 8;
		this.damage = 2;
		exploding = false;

		
		this.imagePath = "images/bulletUp.png";
		

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

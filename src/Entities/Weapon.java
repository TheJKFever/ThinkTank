package Entities;

abstract class Weapon {

	protected int shotSpeed;
	protected int damage;
	protected boolean exploding;

	protected String imagePath;

	public abstract int getShotSpeend();

	public abstract int getDamage() ;

	public abstract boolean getExploding() ;

	public abstract void setExploding() ;

	public abstract String getImagePath() ;

}

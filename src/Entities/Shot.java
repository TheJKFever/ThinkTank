package Entities;

import Weapons.DefaultWeapon;
import Weapons.DoubleStrengthWeapon;
import Weapons.SuperWeapon;
import Weapons.TripleStrengthWeapon;
import Weapons.Weapon;
import Game.GameState;
import Game.Player;
import Global.Settings;

public class Shot extends Entity {

	private static final long serialVersionUID = 5824474896043889830L;
	
	public Weapon weapon;
	private int weaponType = Settings.DEFAULT_WEAPON;

	public Shot(Entity shooterEntity, int theta, GameState gs, Player shooter, int weaponType) {
		this.gs = gs;
		this.weaponType = weaponType;
		this.theta = theta;
		this.player = shooter;

		if (this.weaponType == Settings.DEFAULT_WEAPON) {
			this.weapon = new DefaultWeapon(theta);
		} else if (this.weaponType == Settings.DOUBLE_WEAPON) {
			this.weapon = new DoubleStrengthWeapon(theta);
		} else if (this.weaponType == Settings.TRIPLE_WEAPON) {
			this.weapon = new TripleStrengthWeapon(theta);
		} else if (this.weaponType == Settings.SUPER_WEAPON) {
			this.weapon = new SuperWeapon(theta);
		}

		setImagePath(this.weapon.imagePath);
		this.setWidth(this.weapon.width);
		this.setHeight(this.weapon.height);
		
		if (theta == 0) {
			this.x = shooterEntity.x + shooterEntity.width/2 - this.weapon.WIDTH_VERTICAL/2;
			this.y = shooterEntity.y - this.weapon.HEIGHT_VERTICAL;
		} else if (theta == 90) {
			this.x = shooterEntity.x + shooterEntity.width;
			this.y = shooterEntity.y + shooterEntity.height/2 - this.weapon.HEIGHT_HORIZONTAL / 2;
		} else if (theta == 180) {
			this.x = shooterEntity.x + shooterEntity.width/2 -  this.weapon.WIDTH_VERTICAL / 2;
			this.y = shooterEntity.y + shooterEntity.height;
		} else if (theta == 270) {
			this.x = shooterEntity.x -  this.weapon.WIDTH_HORIZONTAL;
			this.y = shooterEntity.y + shooterEntity.height/2 - this.weapon.HEIGHT_VERTICAL/2;
		}
		
		System.out.println("***SHOT FIRED***");
		System.out.println(shooterEntity);
		System.out.println(this);
		System.out.println("***SHOT COMPLETE***");
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void upgradeWeapon(int weapontype) {
		this.weaponType = weapontype;
	}

	public void update() {
		super.update();
    	updatePosition();
    	checkForCollisionWithEntities(gs.barriers);
    	checkForCollisionWithEntities(gs.turrets);
        checkForCollisionWithEntities(gs.brains);
        checkForCollisionWithEntities(gs.tanks);
	}
	
	public void executeCollisionWith(Entity entity) {
		if (entity.player != this.player) {
	    	entity.hitBy(this);
	    	die();
		}
    }

	public void updatePosition() {
        if (theta == 0) {
        	y -= weapon.shotSpeed;
        } else if (theta == 180) {
        	y += weapon.shotSpeed;
        }  else if (theta == 90) {
        	x += weapon.shotSpeed;
        }  else if (theta == 270) {
        	x -= weapon.shotSpeed;
        }
    }
	
	public void die() {
		super.die();
    	this.exploding = true;
    	this.dying = true;
    	this.visible = false;
    }
}
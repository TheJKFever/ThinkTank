package Entities;

import java.io.Serializable;
import Game.GameState;
import Game.Player;
import Global.Settings;

public class Turret extends Entity implements Serializable {
	
	private static final long serialVersionUID = -6840628133892347660L;

	static final String imageDir="images/turrets/";
	
	static final String IMAGE_TURRET_UP = imageDir + "turretUp";
	static final String IMAGE_TURRET_LEFT = imageDir + "turretLeft";
	static final String IMAGE_TURRET_DOWN = imageDir + "turretDown";
	static final String IMAGE_TURRET_RIGHT = imageDir + "turretRight";

	private static final int MAX_TURRET_HEALTH = 50;
	private static final int RADIUS = 200;
	
	public boolean firing;
	public int shootFrequency;
	
	public Turret(int x, int y,  Player p, GameState gs)
	{
		this.shootFrequency=0;
		this.gs=gs;
		setX(x);
		setY(y);
		this.theta=0;
		this.health=MAX_TURRET_HEALTH;
		this.firing=false;
		this.setWidth(37);
		this.setHeight(45);
		this.player=p;
		this.team=p.team;
		updateImagePath();
		gs.turrets.add(this);
	}
	
    public void updateImagePath() {
    	if (theta == 0) {
    		this.imagePath = IMAGE_TURRET_UP + this.team.num + ".png";
    		this.setWidth(37);
    		this.setHeight(45);
    	} else if (theta == 90) {
    		this.imagePath = IMAGE_TURRET_RIGHT + this.team.num + ".png";
    		this.setWidth(45);
    		this.setHeight(37);
    	} else if (theta == 180) {
    		this.imagePath = IMAGE_TURRET_DOWN + this.team.num + ".png";
    		this.setWidth(37);
    		this.setHeight(45);
    	} else if (theta == 270) {
    		this.imagePath = IMAGE_TURRET_LEFT + this.team.num + ".png";
    		this.setWidth(45);
    		this.setHeight(37);
    	}
    }
	
    public void update()
    {
    	super.update();
    	fireShot();
    	updateImagePath();
    }
    
    public void fireShot()
    {
    	int shotX = 0;
    	int shotY = 0;
    	//boolean hasEnermy=false;
    	
    	for (Tank tank:gs.tanks)
    	{
    		if (this.team.num != tank.team.num)
    		{
    			if (tank.getX()>(this.getX()-20)
    					&& tank.getX()<(this.getX()+20)
	    				&& tank.getY()<(this.getY()) 
	    				&& tank.getY()>(this.getY()-RADIUS))//Enermy coming from upwards
	    		{
	    			theta=0;
	        		shotX = this.x + this.getWidth()/2;
	        		shotY = this.y;
	        		firing=true;
	        		break;
	    		}
	    		else if (tank.getY()>(this.getY()-20)
	    				&& tank.getY()<(this.getY()+20)
	    				&& tank.getX()>(this.getX())
	    				&& tank.getX()<(this.getX()+RADIUS))//Enermy coming from right
	    		{
	    			theta=90;
	        		shotX = this.x + this.getWidth();
	        		shotY = this.y + this.getHeight()/2;
	        		firing=true;
	        		break;
	    		}
	    		else if ((tank.getX()>(this.getX()-20)
	    				&& tank.getX()<(this.getX()+20)
	    	 			&&tank.getY()>(this.getY()) 
	    	 			&& tank.getY()<(this.getY()+RADIUS)))//Enermy coming from downwards
	    	 	{
	    	 		theta=180;
	        		shotX = this.x + this.getWidth()/2;
	        		shotY = this.y + this.getHeight();
	        		firing=true;
	        		break;
	    	 	}
	    		else if (tank.getY()>(this.getY()-20)
	    				&& tank.getY()<(this.getY()+20)
	    				&& tank.getX()<(this.getX())
	    				&& tank.getX()>(this.getX()-RADIUS))//Enermy coming from left
	    		{
	    			theta=270;
	        		shotX = this.x;
	        		shotY = this.y + this.getWidth()/2;
	        		firing=true;
	        		break;
	    		}

    		}
    	}
    	
		if (firing==true)
		{
			if (this.shootFrequency<15)
			{
				this.shootFrequency++;
			}
			else if (this.shootFrequency==15)
			{
				gs.shots.add(new Shot(this, this.theta, this.gs, player, Settings.DEFAULT_WEAPON));
				firing=false;
				shootFrequency=0;
			}
		}
    }

	@Override
	public void executeCollisionWith(Entity e) {
		// TODO Auto-generated method stub		
	}
	
}
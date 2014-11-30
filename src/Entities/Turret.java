package Entities;

import javax.swing.ImageIcon;

import Client.GameState;
import Client.Player;
import Client.Team;

public class Turret extends Entity{
	
	static final String imageDir="images/turrets/";
	static final String IMAGE_TURRET_UP=imageDir+"turret_up.png";
	static final String IMAGE_TURRET_LEFT=imageDir+"turret_left.png";
	static final String IMAGE_TURRET_DOWN=imageDir+"turret_down.png";
	static final String IMAGE_TURRET_RIGHT=imageDir+"turret_right.png";

	
	private static final int MAX_TURRET_HEALTH = 15;
	
	public boolean firing;
	public int teamNumber;
	public int shootFrequency;
	
	public Turret(int x, int y, int teamNum, GameState gs)
	{
		//System.out.println("Hey I am the turret!");
		type="Turret";
		this.shootFrequency=0;
		this.gs=gs;
		setX(x);
		setY(y);
		this.theta=0;
		this.health=MAX_TURRET_HEALTH;
		this.firing=false;
		updateImage(0);
		this.setWidth(60);
		this.setHeight(60);
		teamNumber=teamNum;
		gs.turrets.add(this);
	}
	
    public void updateImage(int theta) {
    	String filename = null;
    	if (theta == 0) {
    		filename = IMAGE_TURRET_UP;
    	} else if (theta == 90) {
    		filename = IMAGE_TURRET_RIGHT;
    	} else if (theta == 180) {
    		filename = IMAGE_TURRET_DOWN;
    	} else if (theta == 270) {
    		filename = IMAGE_TURRET_LEFT;
    	}
    	else
    	{
    		System.out.println("No image");
    	}
    	ImageIcon ii = new ImageIcon(filename);
    	this.setImage(ii.getImage());
    	//System.out.println("turret!");
    }
    
	
    public void update()
    {
    	//TODO create flag of teams for tanks and turrets
    	// The radius is 100
    	
    	//theta = wrapDegrees(theta + dtheta); 
    	//dtheta = 0;
    	//theta=wrapDegrees(theta);
    	fireShot();

    	updateImage(theta);
    	

    	
    	/*
    	if (this.teamNumber==gs.team1.teamNumber)
    	{
	    	 for (Tank tank:gs.tanks)//Check through all the tanks
	    	 {
	    		for (Player player:gs.team2.players)//For each tank, check if it is enemy
	    		{
	    			if (tank==player.tank)//If it is an enemy, check whether it is in the shooting scope
	    			{
	    				if (tank.getX()==this.getX() 
	    	    				&& tank.getY()<(this.getY()) 
	    	    				&& tank.getY()>(this.getY()-100))//Enermy coming from upwards
	    	    		{
	    	    			theta=0;
	    	        		shotX = this.x + this.getWidth()/2;
	    	        		shotY = this.y;
	    	        		firing=true;
	    	        		break;
	    	    		}
	    	    		else if (tank.getY()==this.getY()
	    	    				&& tank.getX()>(this.getX())
	    	    				&& tank.getX()<(this.getX()+100))//Enermy coming from right
	    	    		{
	    	    			theta=90;
	    	        		shotX = this.x + this.getWidth();
	    	        		shotY = this.y + this.getHeight()/2;
	    	        		firing=true;
	    	        		break;
	    	    		}
	    	    		else if ((tank.getX()==this.getX() 
	    	    	 			&&tank.getY()>(this.getY()) 
	    	    	 			&& tank.getY()<(this.getY()+100)))//Enermy coming from downwards
	    	    	 	{
	    	    	 		theta=180;
	    	        		shotX = this.x + this.getWidth()/2;
	    	        		shotY = this.y + this.getHeight();
	    	        		firing=true;
	    	        		break;
	    	    	 	}
	    	    		else if (tank.getY()==this.getY()
	    	    				&& tank.getX()<(this.getX())
	    	    				&& tank.getX()>(this.getX()-100))//Enermy coming from left
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
    	    		gs.shots.add(new Shot(shotX, shotY, this.theta, this.gs));
    	    		break;
	    		}

	    	 }
    	}
    	else if (this.teamNumber==gs.team2.teamNumber)
    	{
	    	 for (Tank tank:gs.tanks)//Check through all the tanks
	    	 {
	    		for (Player player:gs.team1.players)//For each tank, check if it is enermy
	    		{
	    			if (tank==player.tank)//If it is an enermy, check whether it is in the shooting scope
	    			{
	    				if (tank.getX()==this.getX() 
	    	    				&& tank.getY()<(this.getY()) 
	    	    				&& tank.getY()>(this.getY()-100))//Enermy coming from upwards
	    	    		{
	    	    			theta=0;
	    	        		shotX = this.x + this.getWidth()/2;
	    	        		shotY = this.y;
	    	        		firing=true;
	    	        		break;
	    	    		}
	    	    		else if (tank.getY()==this.getY()
	    	    				&& tank.getX()>(this.getX())
	    	    				&& tank.getX()<(this.getX()+100))//Enermy coming from right
	    	    		{
	    	    			theta=90;
	    	        		shotX = this.x + this.getWidth();
	    	        		shotY = this.y + this.getHeight()/2;
	    	        		firing=true;
	    	        		break;
	    	    		}
	    	    		else if ((tank.getX()==this.getX() 
	    	    	 			&&tank.getY()>(this.getY()) 
	    	    	 			&& tank.getY()<(this.getY()+100)))//Enermy coming from downwards
	    	    	 	{
	    	    	 		theta=180;
	    	        		shotX = this.x + this.getWidth()/2;
	    	        		shotY = this.y + this.getHeight();
	    	        		firing=true;
	    	        		break;
	    	    	 	}
	    	    		else if (tank.getY()==this.getY()
	    	    				&& tank.getX()<(this.getX())
	    	    				&& tank.getX()>(this.getX()-100))//Enermy coming from left
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
   	    		gs.shots.add(new Shot(shotX, shotY, this.theta, this.gs));
   	    		break;
	    		}
	    	 }
    	}*/
    	//updateImage(theta);

    }
    
    public void fireShot()
    {
    	int shotX = 0;
    	int shotY = 0;
    	//boolean hasEnermy=false;
    	
    	for (Tank tank:gs.tanks)
    	{
    		if (this.teamNumber!=tank.team)
    		{
    			if (tank.getX()>(this.getX()-10)
    					&& tank.getX()<(this.getX()+10)
	    				&& tank.getY()<(this.getY()) 
	    				&& tank.getY()>(this.getY()-100))//Enermy coming from upwards
	    		{
	    			theta=0;
	        		shotX = this.x + this.getWidth()/2;
	        		shotY = this.y;
	        		firing=true;
	        		break;
	    		}
	    		else if (tank.getY()>(this.getY()-10)
	    				&& tank.getY()<(this.getY()+10)
	    				&& tank.getX()>(this.getX())
	    				&& tank.getX()<(this.getX()+100))//Enermy coming from right
	    		{
	    			theta=90;
	        		shotX = this.x + this.getWidth();
	        		shotY = this.y + this.getHeight()/2;
	        		firing=true;
	        		break;
	    		}
	    		else if ((tank.getX()>(this.getX()-10)
	    				&& tank.getX()<(this.getX()+10)
	    	 			&&tank.getY()>(this.getY()) 
	    	 			&& tank.getY()<(this.getY()+100)))//Enermy coming from downwards
	    	 	{
	    	 		theta=180;
	        		shotX = this.x + this.getWidth()/2;
	        		shotY = this.y + this.getHeight();
	        		firing=true;
	        		break;
	    	 	}
	    		else if (tank.getY()>(this.getY()-10)
	    				&& tank.getY()<(this.getY()+10)
	    				&& tank.getX()<(this.getX())
	    				&& tank.getX()>(this.getX()-100))//Enermy coming from left
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
				gs.shots.add(new Shot(shotX, shotY, this.theta, this.gs, player));
				firing=false;
				shootFrequency=0;
			}
		}
    }
	


}

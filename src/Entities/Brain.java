package Entities;

import Game.GameState;
import Game.Team;
import Global.Settings;

public class Brain extends Entity  {
	
	private static final long serialVersionUID = 6669994797629316542L;
	
	int MAX_BRAIN_HEALTH = 100;
	static String IMAGE_BRAIN_1 = "images/brains/brain1.png";
	static String IMAGE_BRAIN_2 = "images/brains/brain2.png";
	public static int BRAIN_WIDTH = 76;
	public static int BRAIN_HEIGHT = 70;
	public Team team;
	
	//TODO: Create explosion animation brain when it dies
	//TODO: End game when brain dies
	
	public Brain(Team team, GameState gs) {
		this.gs = gs;
		this.team = team;
		this.health = MAX_BRAIN_HEALTH;	
		this.setWidth(BRAIN_WIDTH);
		this.setHeight(BRAIN_HEIGHT);
		
		// center brain horizontally
        this.y = (Settings.BOARD_HEIGHT/2) - (height/2);
        
        // set brain's vertical position based on team
        // TODO: position brain more precisely
		if (team.num == 1) {
			this.setImagePath(IMAGE_BRAIN_1); 
			this.x = 20;
		} else if (team.num == 2) {
			this.setImagePath(IMAGE_BRAIN_2);
	        this.x = Settings.BOARD_WIDTH - this.height - 20;
		}
		gs.brains.addElement(this);
	}
	
	public void update() {
    	super.update();
	}
	
public void setHealth(int type){
		
		if(type == 1){
			if(health+20<100)
				health += 20;
			else
				health = 100;
			
		} else if(type == 2){
			
			if(health+40<100)
				health += 40;
			else
				health = 100;			
		} else if(type == 3){
			
			if(health+60<100)
				health += 60;
			else
				health = 100;
			
		}else{
			
			int add = (int)Math.random()*100;
			
			if(health+add<100)
				health += add;
			else
				health = 100;
			
		}
		
	}
	
	public void die() {
    	// TODO: BRAIN DEATH
		super.die();
    	visible = false;
    	exploding = true;
    	gs.endGame();
    }
	
	public void executeCollisionWith(Entity e) {}
}
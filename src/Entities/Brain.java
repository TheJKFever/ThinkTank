package Entities;

import java.io.Serializable;

import Game.GameState;
import Game.Team;
import Global.Settings;

public class Brain extends Entity implements Serializable  {
	
	private static final long serialVersionUID = 6669994797629316542L;
	
	int MAX_BRAIN_HEALTH = 100;
	static String IMAGE_BRAIN_1 = "images/brains/brain1.png";
	static String IMAGE_BRAIN_2 = "images/brains/brain2.png";
	public static int BRAIN_WIDTH = 89;
	public static int BRAIN_HEIGHT = 88;
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
        this.x = (Settings.BOARD_WIDTH/2) - (getWidth()/2);
        
        // set brain's vertical position based on team
        // TODO: position brain more precisely
		if (team.num == 1) {
			this.setImagePath(IMAGE_BRAIN_1); 
			this.y = Settings.BOARD_HEIGHT - this.getHeight() - 10;
		} else if (team.num == 2) {
			this.setImagePath(IMAGE_BRAIN_2);
	        this.y = 10;
		}
	}
	
	public void update() {
        checkForCollisionWithShots();
	}
}
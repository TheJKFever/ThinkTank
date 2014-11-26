package Entities;

import java.io.Serializable;

import Game.GameState;
import Game.Globals;
import Game.Team;

public class Brain extends Entity implements Serializable  {
	
	private static final long serialVersionUID = 6669994797629316542L;
	
	int MAX_BRAIN_HEALTH = 100;
	static String IMAGE_BRAIN = "images/brain3D.png";
	static String IMAGE_BRAIN_ENEMY = "images/brain3DEnemy.png";
	public static int BRAIN_WIDTH = 89;
	public static int BRAIN_HEIGHT = 88;
	public Team team;
	
	public Brain(Team team, GameState gs) {
		this.type = "Brain";
		this.gs = gs;
		this.team = team;
		this.health = MAX_BRAIN_HEALTH;	
		
		if (team.num == 1) {
			this.setImagePath(IMAGE_BRAIN); 
			this.y = Globals.BOARD_HEIGHT - (this.getHeight() + 20);		
		} else if (team.num == 2) {
			this.setImagePath(IMAGE_BRAIN_ENEMY);
	        this.y = 0;
		}
		setWidth(BRAIN_WIDTH);
		setHeight(BRAIN_HEIGHT);
        this.x = Globals.BOARD_WIDTH/2 - getWidth()/2;
	}
	
	public void update() {
//		for (Shot shot: game.shots) {
//			if (collidesWith(shot.getRect())) {
//				hitBy(shot);
//			}
//		}
        checkForCollisionWithShots();
	}
}
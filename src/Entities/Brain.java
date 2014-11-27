package Entities;

import java.io.Serializable;

import javax.swing.ImageIcon;

import Game.GameState;
import Game.Globals;
import Game.Team;

public class Brain extends Entity implements Serializable  {
	int MAX_BRAIN_HEALTH = 100;
	static String IMAGE_BRAIN = "images/brain3D.png";
	static String IMAGE_BRAIN_ENEMY = "images/brain3DEnemy.png";
	public Team team;
	
	public Brain(Team team, GameState gs) {
		this.type = "Brain";
		this.gs = gs;
		this.team = team;
		this.health = MAX_BRAIN_HEALTH;	
		ImageIcon ii = null;
		
		if (team.num == 1) {
			ii = new ImageIcon(IMAGE_BRAIN); 
			this.y = Globals.BOARD_HEIGHT - (this.getHeight() + 20);		
		} else if (team.num == 2) {
			ii = new ImageIcon(IMAGE_BRAIN_ENEMY);
	        this.y = 0;
		}
		this.setImage(ii.getImage());
		setWidth(89);
		setHeight(88);
        this.x = Globals.BOARD_WIDTH/2 - getWidth()/2;
		gs.brains.addElement(this);
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
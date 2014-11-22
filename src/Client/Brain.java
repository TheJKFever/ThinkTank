package Client;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Brain extends Entity {
	int team;
	int MAX_BRAIN_HEALTH = 100;
	static String IMAGE_BRAIN = "images/brain3D.png";
	static String IMAGE_BRAIN_ENEMY = "images/brain3DEnemy.png";
	
	public Brain(int team, Game game) {
		this.game = game;
		this.type = "Brain";
		this.team = team;
		this.health = MAX_BRAIN_HEALTH;	
		ImageIcon ii = null;
		
		if (team == 1) {
			ii = new ImageIcon(this.getClass().getResource(IMAGE_BRAIN));
			width = ii.getImage().getWidth(null); 
	        height = ii.getImage().getWidth(null);
			this.y = Globals.BOARD_HEIGHT - (height + 20);		
		} else if (team == 2) {
			ii = new ImageIcon(this.getClass().getResource(IMAGE_BRAIN_ENEMY));
			width = ii.getImage().getWidth(null); 
	        height = ii.getImage().getWidth(null);
	        this.y = 0;
		}
		this.setImage(ii.getImage());
        this.x = Globals.BOARD_WIDTH/2 - width/2;
	}
	
	public void update(ArrayList<Shot> shots) {
		for (Shot shot: shots) {
			if (shot.x >= this.x && 
				shot.x <= (this.x + this.width) &&
				shot.y >= this.y && 
				shot.y <= (this.y + this.height)) {
				hitBy(shot);
			}
		}
	}

}

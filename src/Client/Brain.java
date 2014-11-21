package Client;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Brain extends Entity {
	int team;
	int MAX_BRAIN_HEALTH = 100;
	static String IMAGE_BRAIN = "images/brain.png";
	
	public Brain(int team) {
		type = "Brain";
		this.team = team;
//		this.x = Globals.BOARD_WIDTH/2;
		this.x = 100;
		this.y = 100;
		health = MAX_BRAIN_HEALTH;
//		
//		if (team == 1) {
//			this.y = 0;
//			// this.image == TEAM 1 IMAGE	
//		} else if (team == 2){
//			this.y = Globals.BOARD_HEIGHT - 100;
//		}

        ImageIcon ii = new ImageIcon(IMAGE_BRAIN);
        width = ii.getImage().getWidth(null); 
        height = ii.getImage().getWidth(null);
        this.setImage(ii.getImage());
	}
	
	public void update(ArrayList<Shot> shots) {
		for (Shot shot: shots) {
			if (shot.x >= this.x && 
				shot.x <= (this.x+width) &&
				shot.y >= this.y && 
				shot.y <= (this.y+height)) {
				hitBy(shot);
			}
		}
	}
	
	public void hitBy(Shot shot) {
		this.health -= shot.damage;
		System.out.println("BRAIN HEALTH = " + health);
		if (this.health == 0) {
			this.setDying(true);
		}
		shot.exploding = true;
	}

}

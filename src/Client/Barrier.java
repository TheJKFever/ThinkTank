package Client;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Barrier extends Entity {
//	final String IMAGE_BARRIER = "images"
	static final Color color = Color.orange;
	
    public Barrier(int x, int y, int width, int height, GameScreen gameScreen) {
    	type = "Barrier";
    	this.gameScreen = gameScreen;
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        this.health = Integer.MAX_VALUE;
    }

	public void update() {
		for (Shot shot: gameScreen.shots) {
			if (collidesWith(shot.getRect())) {
				hitBy(shot);
			}
		}
//        log("BARRIER X: " + this.x + " Y: " + this.y + " WIDTH: " + this.getWidth() + " HEIGHT: " + this.getHeight());
//        checkForCollisionWithShots();
	}
}

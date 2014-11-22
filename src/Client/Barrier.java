package Client;

import java.awt.Color;

import javax.swing.ImageIcon;

public class Barrier extends Entity {
//	final String IMAGE_BARRIER = "images"
	static final Color color = Color.orange;
	
    public Barrier(int x, int y, int width, int height, Game game) {
    	type = "Barrier";
    	this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}

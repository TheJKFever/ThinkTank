package Client;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Entity {

    boolean visible;
    Image image;
    boolean dying;
    
    int x;
    int y;
    int dx;
    int dy;
    int dp;
    int theta = 0;
    int dtheta;
    
    int height;
    int width;
    
    int health;
    String type;

    public Entity() {
        visible = true;
    }
    
//    public abstract void update();

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
    
    public void log(String msg) {
    	if (Globals.DEBUG) {
    		System.out.println(msg);
    	}
    }
}

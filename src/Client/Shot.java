package Client;

import java.util.Iterator;

import javax.swing.ImageIcon;

public class Shot extends Entity {

    static final String IMAGE_SHOT = "images/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;
    int shotSpeed = 4;
    int damage = 1;
    boolean exploding = false;

    public Shot(int x, int y, int theta) {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(IMAGE_SHOT));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
        this.theta = theta;
    }
    
    public void update(Battle battle) {
//        Iterator it = battle.aliens.iterator();
        int shotX = getX();
        int shotY = getY();
        updatePosition();

//        // check for collisions with aliens
//        while (it.hasNext()) {
//            Enemy enemy = (Enemy) it.next();
//            int alienX = enemy.getX();
//            int alienY = enemy.getY();
//
//            if (enemy.isVisible()) {
//                if (shotX >= (alienX) && 
//                    shotX <= (alienX + Globals.ALIEN_WIDTH) &&
//                    shotY >= (alienY) &&
//                    shotY <= (alienY+ Globals.ALIEN_HEIGHT) ) {
//                        ImageIcon ii = new ImageIcon(getClass().getResource(battle.expl));
//                        enemy.setImage(ii.getImage());
//                        enemy.setDying(true);
//                        battle.deaths++;
//                        die();
//                    }
//            }
//        }
    }
    
    public void updatePosition() {
        if (theta == 0) {
        	y -= shotSpeed;
            if (y < 0) {
                die();
            }
        } else if (theta == 180) {
        	y += shotSpeed;
        	if (y > 550) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }  else if (theta == 90) {
        	x += shotSpeed;
        	if (x > 600) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }  else if (theta == 270) {
        	x -= shotSpeed;
        	if (x < 0) { // TODO: FIX THIS TO BE SOME GLOBAL CONSTANT
        		die();
        	}
        }
    }
}
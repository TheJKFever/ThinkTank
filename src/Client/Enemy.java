package Client;
import javax.swing.ImageIcon;

public class Enemy extends Entity {

    private Bomb bomb;
    private final String shot = "images/enemy.png";

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());

    }

    public void update(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public class Bomb extends Entity {

        private final String bomb = "images/bomb.png";
        private boolean destroyed;

        public Bomb(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(this.getClass().getResource(bomb));
            this.image = ii.getImage();
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
package Client;

public interface Globals {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 600;
    public static final int GROUND = 290;
    public static final int BOMB_HEIGHT = 5;
    public static final int ENEMY_HEIGHT = 12;
    public static final int ENEMY_WIDTH = 12;
    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 5;
    public static final int GO_DOWN = 15;
    public static final int NUMBER_OF_ENEMIES_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 10;
	public static final boolean DEBUG = true;
    
    public class Development {
    	public static final int PORT = 3000;
    	public static final String HOST = "localhost";

    	public class DB {
        	public static final String ADDRESS = "jdbc:mysql://localhost/";
        	public static final String NAME = "lab11";
        	public static final String DRIVER = "com.mysql.jdbc.Driver";
        	public static final String USER = "root";
        	public static final String PASSWORD = "root";
    	}
    }
    
    public class Production {
    	public static final String HOST = "thinktank.herokuapp.com";
    	public static final int PORT = 2400;
    	public class DB {
        	public static final String ADDRESS = "jdbc:mysql://localhost/";
        	public static final String NAME = "lab11";
        	public static final String DRIVER = "com.mysql.jdbc.Driver";
        	public static final String USER = "root";
        	public static final String PASSWORD = "root";
    	}
    }


}
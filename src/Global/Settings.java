package Global;

public interface Settings {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 600;
    
    public static final int GUI_WIDTH = 1000;
    public static final int GUI_HEIGHT = 800;
    
    public static final int DELAY = 20;
	
    public static final boolean DEBUG = false;
    
	public static final int DEFAULT_WEAPON = 1;
	public static final int DOUBLE_WEAPON = 2;
	public static final int TRIPLE_WEAPON = 3;
	public static final int SUPER_WEAPON = 4;
    
	public int CHAT_PORT = 6789;
    
    public class Development {
    	public static final int SERVER_PORT = 3000;
    	public static final String HOST = "localhost";
//    	public static final String HOST = "10.120.74.242";

    	public class DB {
        	public static final String ADDRESS = "jdbc:mysql://localhost/";
        	public static final String NAME = "thinktank";
        	public static final String DRIVER = "com.mysql.jdbc.Driver";
        	public static final String USER = "root";
        	public static final String PASSWORD = "root";
    	}
    }
    
    public class Production {
    	public static final String HOST = "thinktank.herokuapp.com";
    	public static final int SERVER_PORT = 2400;
    	public class DB {
        	public static final String ADDRESS = "jdbc:mysql://localhost/";
        	public static final String NAME = "thinktank";
        	public static final String DRIVER = "com.mysql.jdbc.Driver";
        	public static final String USER = "root";
        	public static final String PASSWORD = "root";
    	}
    }
}
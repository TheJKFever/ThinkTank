package Global;

public interface Settings {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 600;
    
    public static final int GUI_WIDTH = 1000;
    public static final int GUI_HEIGHT = 800;
    
    public static final int DELAY = 50;
	
    public static final boolean DEBUG = true;
    
	public int CHAT_PORT = 6789;
    
    public class Development {
    	public static final int SERVER_PORT = 3000;
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
    	public static final int SERVER_PORT = 2400;
    	public class DB {
        	public static final String ADDRESS = "jdbc:mysql://localhost/";
        	public static final String NAME = "lab11";
        	public static final String DRIVER = "com.mysql.jdbc.Driver";
        	public static final String USER = "root";
        	public static final String PASSWORD = "root";
    	}
    }
}
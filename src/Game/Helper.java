package Game;

public class Helper {
	
    public static void log(Object msg) {
        if (Globals.DEBUG) {
            System.out.println(msg.toString());
        }
    }
}

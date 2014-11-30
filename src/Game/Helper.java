package Game;

import Global.Settings;

public class Helper {
	
    public static void log(Object msg) {
        if (Settings.DEBUG) {
            System.out.println(msg.toString());
        }
    }
}

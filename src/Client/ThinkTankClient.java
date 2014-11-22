package Client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ThinkTankClient extends JFrame {

    public ThinkTankClient()
    {
        add(new Game());    	
        setTitle("Think Tank");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Globals.BOARD_WIDTH, Globals.BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new ThinkTankClient();
    }
}

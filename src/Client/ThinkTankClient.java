package Client;

import javax.swing.JFrame;

public class ThinkTankClient extends JFrame {

    public ThinkTankClient()
    {
        add(new Battle());
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

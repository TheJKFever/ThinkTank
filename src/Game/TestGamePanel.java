package Game;

import javax.swing.JFrame;

import Screens.GamePanel;

public class TestGamePanel extends JFrame {
	public TestGamePanel() {
		this.setSize(500, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new GamePanel());
	}
	
	public static void main(String[] args) {
		TestGamePanel test = new TestGamePanel();
	}
}


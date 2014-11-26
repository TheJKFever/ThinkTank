package Screens;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingScreen extends JPanel {
	public JLabel waitingLbl;
	
	public WaitingScreen() {
		waitingLbl =new JLabel("WAITING...");
		this.add(waitingLbl);
	}
}

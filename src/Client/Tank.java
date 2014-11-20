package Client;

import java.util.Vector;


public class Tank extends Thread {
	
	int x, y;
	gui mygui;
	boolean forward = false;
	boolean backward = false;
	boolean leftward = false;
	boolean rightward = false;
	
	// this ammo vector dynamically change, once an ammo goes out of battle field, remove that ammo
	Vector<Ammo> ammoVt = new Vector<Ammo>();
	
	public Tank(gui mygui) {
		this.x = 300;
		this.y = 300;
		this.mygui = mygui;	
		this.start();
	}
	
	
	public void shoot() {
		Ammo ammo = new Ammo(this);
		ammoVt.add(ammo);
	}
	
	
	public void moveForward() {
		try {
			y -= 2;
			this.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void moveBackward() {
		try {
			y += 2;
			this.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void moveLeft() {
		try {
			x -= 2;
			this.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void moveRight() {
		try {
			x += 2;
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	public void run() {
		while(true) {
			if (forward == true) 
				moveForward();
			if(backward == true)
				moveBackward();
			if(leftward == true)
				moveLeft();
			if(rightward == true)
				moveRight();
		}
	}
}

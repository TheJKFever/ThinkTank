package Client;

public class Ammo extends Thread {

	int x,y;
	Tank tank;
	boolean visible = true;
	
	public Ammo(Tank tank) {
		x = tank.x+20;
		y = tank.y;
		this.tank = tank;
		
		this.start();
	}
	
	public void run() {
		while(y!=0) {
			try {
				y -= 1;				
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// after the ammo going out of bound, remove this ammo from this tank
		this.tank.ammoVt.remove(this);
	}
	
}

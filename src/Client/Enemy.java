package Client;

import javax.swing.JOptionPane;


public class Enemy extends Thread {
	
	int x,y;
	int change = 1;
	gui mygui;
	boolean visible = true;
	
	public Enemy(gui mygui) {
		x = 0;
		y = 100;
		this.mygui = mygui;
		this.start();
	}

	
	public void run() {
		while(true) {
			try {
				if(x>600 || x<0)
					change = -change;
				x+= change;
				
				for(int i=0;i< mygui.tank.ammoVt.size();i++) {
					Ammo ammo = mygui.tank.ammoVt.get(i);
					if((ammo.x>=this.x && ammo.x<=this.x+30) 
							&& (ammo.y>=this.y && ammo.y<=this.y+40)) {
						mygui.tank.ammoVt.remove(ammo);
						this.visible = false;
						
					}
				}

				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

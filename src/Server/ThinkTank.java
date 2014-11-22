package Server;

import Client.GameState;

public class ThinkTank {
	/* This should have:
	 * update loop
	 * render
	 * gamestate
	 */ 
	public GameState gs;
	
	private void update() {
		// tanks, bullets, turrets, resources
		// gs.updatables can be a Vector<UpdateableObject> with references to anything that needs to be updated
		for (UpdatableObject obj:gs.updatables) {
			obj.update();
		}
		render();
	}
	
	private void render() {
		
	}
	
}

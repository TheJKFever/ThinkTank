package Game;

public class Rect {
	public int x, y, width, height, top, bottom, left, right;
	
	public Rect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.top = y;
		this.bottom = y + height;
		this.left = x;
		this.right = x + width;
	}
}

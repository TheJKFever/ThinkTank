package GameEffect;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;



public abstract class Bomb
{
	int x, y;
	public int step=0;
	static final Toolkit TLK=Toolkit.getDefaultToolkit();

	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}

	public abstract void draw(Graphics g,JPanel j);
}

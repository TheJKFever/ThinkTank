package GameEffect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class DoubleBomb extends Bomb
{

	private static final Image[] SBIMGS=
	{
		new ImageIcon("images/ShotBomb/1.png").getImage(),
		new ImageIcon("images/ShotBomb/2.png").getImage(),
		new ImageIcon("images/ShotBomb/3.png").getImage(),
		new ImageIcon("images/ShotBomb/4.png").getImage(),
		new ImageIcon("images/ShotBomb/5.png").getImage(),
	};
	
	public DoubleBomb(int x,int y)
	{
		super(x, y);
	}

	public void draw(Graphics g,JPanel j)
	{
		j.setBackground(Color.black);
		
		
		if(step<5){
			
			g.drawImage(SBIMGS[step],x,y,j);
			
		}
		else{
			
			
		}
		step++;
	}
}
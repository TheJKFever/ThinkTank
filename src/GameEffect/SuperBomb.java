package GameEffect;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class SuperBomb extends Bomb
{

	private static final Image[] SBIMGS=
	{
		new ImageIcon("images/SuperBomb/1.png").getImage(),
		new ImageIcon("images/SuperBomb/2.png").getImage(),
		new ImageIcon("images/SuperBomb/3.png").getImage(),
		new ImageIcon("images/SuperBomb/4.png").getImage(),
		new ImageIcon("images/SuperBomb/5.png").getImage(),
		new ImageIcon("images/SuperBomb/6.png").getImage(),
		new ImageIcon("images/SuperBomb/7.png").getImage(),
		new ImageIcon("images/SuperBomb/8.png").getImage(),
		new ImageIcon("images/SuperBomb/9.png").getImage(),
		new ImageIcon("images/SuperBomb/10.png").getImage(),
		new ImageIcon("images/SuperBomb/11.png").getImage(),
		new ImageIcon("images/SuperBomb/12.png").getImage(),
		new ImageIcon("images/SuperBomb/13.png").getImage(),
		new ImageIcon("images/SuperBomb/14.png").getImage(),
		new ImageIcon("images/SuperBomb/15.png").getImage(),
		new ImageIcon("images/SuperBomb/16.png").getImage()
	};
	
	public SuperBomb(int x,int y)
	{
		super(x, y);
	}

	public void draw(Graphics g,JPanel j)
	{
		j.setBackground(Color.black);
		
		
		if(step<16){
			
			g.drawImage(SBIMGS[step],x,y,j);
			
		}
		else{
			
			
		}
		step++;
	}
}
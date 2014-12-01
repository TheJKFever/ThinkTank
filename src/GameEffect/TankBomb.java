package GameEffect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TankBomb extends Bomb
{
	public static final Image[] TBIMGS=
	{
		new ImageIcon("images/TankBomb/1.png").getImage(),
		new ImageIcon("images/TankBomb/2.png").getImage(),
		new ImageIcon("images/TankBomb/3.png").getImage(),
		new ImageIcon("images/TankBomb/4.png").getImage(),
		new ImageIcon("images/TankBomb/5.png").getImage(),
		new ImageIcon("images/TankBomb/6.png").getImage(),
		new ImageIcon("images/TankBomb/7.png").getImage(),
		new ImageIcon("images/TankBomb/8.png").getImage(),
		new ImageIcon("images/TankBomb/9.png").getImage(),
		new ImageIcon("images/TankBomb/10.png").getImage(),
		new ImageIcon("images/TankBomb/11.png").getImage(),
		new ImageIcon("images/TankBomb/12.png").getImage(),
		new ImageIcon("images/TankBomb/13.png").getImage(),
		new ImageIcon("images/TankBomb/14.png").getImage(),
		new ImageIcon("images/TankBomb/15.png").getImage(),
		new ImageIcon("images/TankBomb/16.png").getImage(),
		new ImageIcon("images/TankBomb/17.png").getImage(),
		new ImageIcon("images/TankBomb/18.png").getImage(),
		new ImageIcon("images/TankBomb/19.png").getImage(),
		new ImageIcon("images/TankBomb/20.png").getImage(),
		new ImageIcon("images/TankBomb/21.png").getImage(),
		new ImageIcon("images/TankBomb/22.png").getImage(),
		new ImageIcon("images/TankBomb/23.png").getImage(),
		new ImageIcon("images/TankBomb/24.png").getImage(),
		new ImageIcon("images/TankBomb/25.png").getImage(),
		new ImageIcon("images/TankBomb/26.png").getImage(),
		new ImageIcon("images/TankBomb/27.png").getImage(),
		new ImageIcon("images/TankBomb/28.png").getImage(),
		new ImageIcon("images/TankBomb/29.png").getImage(),
		new ImageIcon("images/TankBomb/30.png").getImage(),
		new ImageIcon("images/TankBomb/31.png").getImage(),
		new ImageIcon("images/TankBomb/32.png").getImage(),
		new ImageIcon("images/TankBomb/33.png").getImage(),
		new ImageIcon("images/TankBomb/34.png").getImage(),
		new ImageIcon("images/TankBomb/35.png").getImage(),
		new ImageIcon("images/TankBomb/36.png").getImage()
		
	};
	

	public TankBomb(int x,int y)
	{
		super(x, y);
	}

	public void draw(Graphics g,JPanel j)
	{
		j.setBackground(Color.black);
		
		
		if(step<36){
			
			g.drawImage(TBIMGS[step],x,y,null);
			
		}
		else{
			
			
		}
		step++;
	}
}
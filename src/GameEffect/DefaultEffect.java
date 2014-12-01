package GameEffect;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class DefaultEffect extends Bomb
{

	private static final Image[] SBIMGS=
	{
		new ImageIcon("images/ShotBomb/1.png").getImage(),
		new ImageIcon("images/ShotBomb/2.png").getImage(),
		new ImageIcon("images/ShotBomb/3.png").getImage(),

	};
	
	public DefaultEffect(int x,int y)
	{
		super(x, y);
	}

	public void draw(Graphics g,JPanel j)
	{
		j.setBackground(Color.black);
		
		
		if(step<3){
			
			g.drawImage(SBIMGS[step],x,y,j);
			
		}
		else{
			
			
		}
		step++;
	}
}
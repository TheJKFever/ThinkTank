package Screens;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Tank;



public class Hitbar
{
	public static final int HITPOINTSIZE=30; 
	public int topNumber;  
	public int number;  
	Tank tk;

	public Hitbar(int topNum,Tank tk)
	{
		topNumber=topNum;
		number=topNum;
		this.tk=tk;
	}

	public void draw(Graphics g)
	{
		if(number>0){
		g.setColor(Color.white);
		g.drawRect(tk.x-8,tk.y-8,HITPOINTSIZE,5);
		g.setColor(Color.red);
		g.fillRect(tk.x-7,tk.y-7,number*HITPOINTSIZE/topNumber-1,4);		
		}
	}

	
}
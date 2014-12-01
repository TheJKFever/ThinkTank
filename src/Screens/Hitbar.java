package Screens;

import java.awt.Color;
import java.awt.Graphics;

import Entities.*;



public class Hitbar
{
	public static final int HITPOINTSIZE=30; 
	public int topNumber;  
	public int number;  
	Turret tk;

	public Hitbar(int topNum,Turret tk)
	{
		topNumber=topNum;
		this.tk=tk;
		number=tk.health;

	}

	public void draw(Graphics g)
	{
		if(number>0){
		g.setColor(Color.white);
		g.drawRect(tk.x,tk.y-8,HITPOINTSIZE,5);
		g.setColor(Color.red);
		g.fillRect(tk.x-1,tk.y-7,number*HITPOINTSIZE/topNumber-1,4);		
		}
	}

	
}
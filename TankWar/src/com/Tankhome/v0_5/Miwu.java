package com.Tankhome.v0_5;

import java.awt.Graphics;

public class Miwu {
	private int x;
	private int y;
	private boolean isAlive;
	private Blood blood;

	
	public Blood getBlood() {
		return blood;
	}

	public void setBlood(Blood blood) {
		this.blood = blood;
	}

	public Miwu(int x,int y,boolean isAlive,Blood blood){
		this.x=x;
		this.y=y;
		this.isAlive=isAlive;
		this.blood=blood;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void draw(Graphics g){
		
		g.drawLine(x, y, 0,GameConfig.GAME_HEIGHT);
		blood.draw(g);
	}
	
	

}



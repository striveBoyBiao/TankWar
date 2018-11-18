package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Commonwall {
	private int x;
	private int y;
	private boolean isAlive;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImages = null;

	static {
		wallImages = new Image[] {tk.getImage(Commonwall.class.getClassLoader().getResource("Images/commonWall.gif")),		
		};
		
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, GameConfig.COMMONWALL_WIDTH, GameConfig.COMMONWALL_HEIGHT);
	}

	public Commonwall() {

	}

	public Commonwall(int x, int y, boolean isAlive) {
		this.x = x;
		this.y = y;
		this.isAlive = isAlive;	
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

	public void draw(Graphics g) {
		g.drawImage(wallImages[0], x, y, null);	
	}
}
		

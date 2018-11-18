package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class River {
	private int x;
	private int y;
	private TankWar tc;

	public River(int x, int y, TankWar tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, GameConfig.RIVER_WIDTH, GameConfig.RIVER_HEIGHT);
	}

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImages = null;

	static {
		riverImages = new Image[] { tk.getImage(River.class.getClassLoader().getResource("Images/river_S.jpg")) };
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

	public TankWar getTc() {
		return tc;
	}

	public void setTc(TankWar tc) {
		this.tc = tc;
	}

	public void draw(Graphics g) {
		g.drawImage(riverImages[0], x, y, null);
	}

}

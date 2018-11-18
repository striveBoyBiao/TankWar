package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Metalwall {
	private int x;
	private int y;
	
	//获取对象的矩形范围
	public Rectangle getRect() {
		return new Rectangle(x, y, GameConfig.METALWALL_WIDTH, GameConfig.METALWALL_HEIGHT);
	}
	
	//贴图
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] metalwalls = null;

	static {
		metalwalls = new Image[] { tk.getImage(Metalwall.class.getClassLoader().getResource("Images/metalWall.gif"))
		};
	}
	
	public Metalwall(int x,int y){
		this.x=x;
		this.y=y;
	}
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
	public void draw(Graphics g){
		g.drawImage(metalwalls[0], x, y, null);
	}
}

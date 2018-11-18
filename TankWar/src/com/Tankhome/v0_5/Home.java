package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * 
 *
 *Home.java 文件使用说明
 * 说明：老家<br/>
 *  公开方法：
 * <ul>
 * <li>Home：</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Home {
	private int x;
	private int y;
	private boolean isAlive;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] homeImages = null;
	static{
		homeImages = new Image[]{tk.getImage(Home.class.getClassLoader().getResource("Images/home.jpg"))		
		};
	}
	
	/* 获取老大的边界 */
	public Rectangle getRect() {
		return new Rectangle(x, y, GameConfig.HOME_WIDTH, GameConfig.HOME_HEIGHT);
	}
	
	public Home(int x,int y,boolean isAlive){
		this.x=x;
		this.y=y;
		this.isAlive=isAlive;
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
		g.drawImage(homeImages[0], x, y, null);
	}
	

}

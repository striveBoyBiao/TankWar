 package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * 
 *
 *Bomb.java 文件使用说明
 * 说明：<br/>
 * 公开方法：
 * <ul>
 * <li>Bomb：爆炸类</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Bomb {
    /**爆炸区域的中心x坐标*/
	private int x;
	/**爆炸区域的中心y坐标*/
	private int y;
	/**炸弹存活的标记*/
	private boolean isAlive = true; 
	private int index;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bombImages = 
				{tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/1.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/2.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/3.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/4.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/5.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/6.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/7.png")),
				tk.getImage(Bomb.class.getClassLoader().getResource("Images/Bomb/8.png")),
				};
		
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
	
	public Bomb(){
		
	}
	public Bomb(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public void draw(Graphics g){
		if(index == bombImages.length){
			isAlive=false;
			index = 0;
			return;
		}	
		g.drawImage(bombImages[index], x, y, null);
		index++;
	
		}
	}


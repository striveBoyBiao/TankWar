package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 *
 *Tree.java 文件使用说明
 * 说明：界面树<br/>
 *  公开方法：
 * <ul>
 * <li>Tree：</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Tree {
	private int x;
	private int y;
	private TankWar tc;
	
	public Tree(int x,int y,TankWar tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}

	private static Toolkit tk =Toolkit.getDefaultToolkit();
	private static Image[] treeImages = null;
	static{
		treeImages = new Image[]{tk.getImage(Tree.class.getClassLoader().getResource("Images/tree.gif"))		
		};
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
	
	public void draw(Graphics g){
		g.drawImage(treeImages[0], x, y, null);
	}
	
}

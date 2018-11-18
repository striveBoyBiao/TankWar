package com.Tankhome.v0_5;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 *
 *Blood.java 文件使用说明
 * 说明：<br/>
 *  公开方法：
 * <ul>
 * <li>Blood：血条</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Blood {
    /**需要附加血条效果的目标对象*/
	private Object target;
	private int height;
	/**血量*/
	private int bloodNum;
	/**血总量*/
	private int bloodCapacity;
	
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getBloodNum() {
		return bloodNum;
	}
	public void setBloodNum(int bloodNum) {
		this.bloodNum = bloodNum;
	}
	public int getBloodCapacity() {
		return bloodCapacity;
	}
	public void setBloodCapacity(int bloodCapacity) {
		this.bloodCapacity = bloodCapacity;
	}
	
	public Blood (Object target,int bloodNum,int bloodCapacity){
		this.target=target;
		this.height=GameConfig.BLOOD_HEIGHT;
		this.bloodNum=bloodNum;
		this.bloodCapacity=bloodCapacity;
	}
	
	//扣血的方法
	public void minus(int num){
		if(bloodNum>=num){
			bloodNum-=num;
		}
	}
	
	/*判定是否有血量*/
	public boolean isAlive(){
		return bloodNum>0;
	}
	
	/**
	 * 若设计Wall与Tank有公共的父类Rectangle，则本段代码可重构
	 * 
	 */
	
	public void draw(Graphics g){
		
		if(target instanceof Tank){
			Tank t=(Tank)target;
			g.setColor(Color.BLACK);
			g.drawRect(t.getX(),t.getY()-GameConfig.BLOOD_HEIGHT,GameConfig.TANK_WIDTH, height);
			g.setColor(Color.RED);	
			g.fillRect(t.getX(),t.getY()-GameConfig.BLOOD_HEIGHT,GameConfig.TANK_WIDTH*bloodNum/bloodCapacity, height);
		}
		if(target instanceof Miwu){
			Miwu t=(Miwu)target;
			g.setColor(Color.gray);	
			g.fillRect(t.getX()+2,t.getY()+2,GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT*bloodNum/bloodCapacity);
			
		}
		
	}
	
	
	

}

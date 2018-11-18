package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;


/**
 * 
 *
 *Prop.java 文件使用说明
 * 说明：<br/>
 *  公开方法：
 * <ul>
 * <li>Bomb：道具类</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Prop {
    private int x;
    private int y;
    private boolean isAlive;
    private boolean isBlood;
    private TankWar tc;
    Random r = new Random();
    private int space = 0;  //道具出现的间隔变量初始值
    private int lastime = 0;    //道具的出现的持续时间的初始值
    private int space_blood = 0;    //道具出现的间隔
    private int lastime_blood = 0;
    
    //获取道具的大小
    public Rectangle getRect() {
        return new Rectangle(x, y, GameConfig.TANK_WIDTH, GameConfig.TANK_HEIGHT);
    }

    private static Toolkit tk = Toolkit.getDefaultToolkit();// 控制面板
    private static Image[] propImages = null;// 存储全局静态

    static {
        propImages = new Image[] { tk.getImage(Prop.class.getClassLoader().getResource("Images/star.png")),
                tk.getImage(Prop.class.getClassLoader().getResource("Images/hp.png")), };
    }

    public Prop() {

    }

    public Prop(int x, int y, boolean isAlive, TankWar tc, boolean isBlood) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        this.tc = tc;
        this.isBlood = isBlood;
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

    public boolean isBlood() {
        return isBlood;
    }

    public void setBlood(boolean isBlood) {
        this.isBlood = isBlood;
    }

    public TankWar getTc() {
        return tc;
    }

    public void setTc(TankWar tc) {
        this.tc = tc;
    }

    public void draw(Graphics g) {
        //道具血出现的时间以及持续的时间
        if (isBlood) {
            space_blood += GameConfig.THREAD_UPDATE_TIME;
            if (space_blood > GameConfig.PROP_SPACE) {
                lastime_blood += GameConfig.THREAD_UPDATE_TIME;
                if (lastime_blood > GameConfig.PROP_LAST_TIME * (r.nextInt(3) + 5)) {
                    space_blood = 0;
                    lastime_blood = 0;
                    tc.propnumber_blood++;
                    return;
                }
                g.drawImage(propImages[1], x, y, null);
            }
        } else {
            //道具子弹的出现的时间间隔以及持续的时间
            space += GameConfig.THREAD_UPDATE_TIME;
            if (space > GameConfig.PROP_SPACE*2) {
                lastime += GameConfig.THREAD_UPDATE_TIME;
                if (lastime > GameConfig.PROP_LAST_TIME * (r.nextInt(5) + 5)) {
                    space = 0;
                    lastime = 0;
                    tc.propnumber++;
                    return;
                }
                g.drawImage(propImages[0], x, y, null);
            }
        }
    }
}

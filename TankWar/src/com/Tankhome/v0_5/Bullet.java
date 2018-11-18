package com.Tankhome.v0_5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * 
 *
 *Bullet.java 文件使用说明
 * 说明：<br/>
 *  公开方法：
 * <ul>
 * <li>Bullet：圆形子弹</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Bullet {
    private int x; // 子弹的圆心横坐标
    private int y; // 子弹的圆心纵坐标
    private int bullet_distance = 0;// 子弹移动的距离
    private Direction dir; // 子弹的方向
    private boolean isEnemy; // 区分是否是敌人的子弹，true->敌人的，false->我的
    private boolean isAlive; // 存活的标记
    private boolean multiple; // 子弹速度的倍率

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] bulletImages = null;

    static {
        bulletImages = new Image[] { tk.getImage(Bullet.class.getClassLoader().getResource("Images/bulletU.gif")),
                tk.getImage(Bullet.class.getClassLoader().getResource("Images/bulletD.gif")),
                tk.getImage(Bullet.class.getClassLoader().getResource("Images/bulletL.gif")),
                tk.getImage(Bullet.class.getClassLoader().getResource("Images/bulletR.gif")),
                tk.getImage(Bullet.class.getClassLoader().getResource("Images/NOR2.png")), };
    }

    /* 获取子弹的边界 */
    public Rectangle getRect() {

        return new Rectangle(x - GameConfig.BULLET_WIDTH / 2, y - GameConfig.BULLET_HEIGHT / 2, GameConfig.BULLET_WIDTH,
                GameConfig.BULLET_HEIGHT);
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

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setisEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }
    
    public Bullet(){
        
    }

    public Bullet(int x, int y, Direction dir, boolean isEnemy, boolean isAlive,  boolean multiple) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.isEnemy = isEnemy;
        this.isAlive = isAlive; 
        this.multiple = multiple;
    }

    public Bullet(int x, int y, Direction dir, boolean isEnemy, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.isEnemy = isEnemy;
        this.isAlive = isAlive;
    }

    /**
     * 绘制子弹的方法
     */
    public void draw(Graphics g) {
        bullet_distance += GameConfig.THREAD_UPDATE_TIME;

        if (isEnemy) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.yellow);
        }
        if (isEnemy) {
            //敌方子弹的绘制
            switch (dir) {
            case U:
                g.drawImage(bulletImages[0], x - GameConfig.BULLET_WIDTH / 2 + 1, y, null);
                break;
            case D:
                g.drawImage(bulletImages[1], x - GameConfig.BULLET_WIDTH / 2, y, null);
                break;
            case L:
                g.drawImage(bulletImages[2], x, y+1 , null);
                break;
            case R:
                g.drawImage(bulletImages[3], x, y+1 , null);
                break;
            default:
                break;
            }
        } else if (!isEnemy && !multiple) {
            //我方子弹且没有道具效果的绘制
            switch (dir) {
            case U:
                g.drawImage(bulletImages[0], x - GameConfig.BULLET_WIDTH / 2+2, y, null);
                break;
            case D:
                g.drawImage(bulletImages[1], x - GameConfig.BULLET_WIDTH / 2, y, null);
                break;
            case L:
                g.drawImage(bulletImages[2], x, y - 2, null);
                break;
            case R:
                g.drawImage(bulletImages[3], x, y - 2, null);
                break;
            default:
                break;
            }
    
        } else if (!isEnemy && multiple) {
            //我方子弹且有道具时候子弹的绘制
            switch (dir) {
            case U:
                g.drawImage(bulletImages[0], x - GameConfig.BULLET_WIDTH / 2 , y, null);
                break;
            case D:
                g.drawImage(bulletImages[1], x - GameConfig.BULLET_WIDTH / 2 , y, null);
                break;
            case L:
                g.drawImage(bulletImages[2], x, y - 2, null);
                break;
            case R:
                g.drawImage(bulletImages[3], x, y - 2, null);
                break;
            default:
                break;
            }
            switch (dir) {
            case U:
                g.drawImage(bulletImages[0], x - GameConfig.BULLET_WIDTH / 2 + 5, y, null);
                break;
            case D:
                g.drawImage(bulletImages[1], x - GameConfig.BULLET_WIDTH / 2 + 5, y, null);
                break;
            case L:
                g.drawImage(bulletImages[2], x, y + 2, null);
                break;
            case R:
                g.drawImage(bulletImages[3], x, y + 2, null);
                break;
            default:
                break;
            }   
    
        }
    }

    /**
     * 子弹移动的方法
     */
    public void move() {
        if (isEnemy) {
            if (dir == Direction.L) {
                this.x -= GameConfig.BULLET_SPEED;
            } else if (dir == Direction.R) {
                this.x += GameConfig.BULLET_SPEED;
            } else if (dir == Direction.U) {
                this.y -= GameConfig.BULLET_SPEED;
            } else if (dir == Direction.D) {
                this.y += GameConfig.BULLET_SPEED;
            }
        } else {
            if (dir == Direction.L) {
                this.x -= GameConfig.BULLET_SPEED*2;
            } else if (dir == Direction.R) {
                this.x += GameConfig.BULLET_SPEED*2;
            } else if (dir == Direction.U) {
                this.y -= GameConfig.BULLET_SPEED*2;
            } else if (dir == Direction.D) 
                this.y += GameConfig.BULLET_SPEED*2;
            }

        
         // 判断子弹是否飞离射击范围，如果超出射击范围，则消失
         if (bullet_distance > 1500) {
         this.setAlive(false);
         bullet_distance = 0;
         }

        // 判断子弹 是否出界
        // 如果出界就删除
        if (x < 0 || x >= GameConfig.GAME_WIDTH - GameConfig.BULLET_WIDTH || y < 20
                || y > GameConfig.GAME_HEIGHT - GameConfig.BULLET_HEIGHT)
            this.setAlive(false);
    }

}

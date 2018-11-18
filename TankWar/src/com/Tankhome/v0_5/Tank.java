package com.Tankhome.v0_5;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;
/**
 * 
 *
 *Tank.java 文件使用说明
 * 说明：<br/>
 *  公开方法：
 * <ul>
 * <li>Tank：坦克类</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class Tank {
	private int x; // 坦克的横坐标
	private int y; // 坦克的纵坐标
	private Direction dir; // tank运行的方向
	private boolean isEnemy; // 敌我识别的标记 true->敌方 false—>我方
	private int backup_x; // 保存点前的坦克横坐标
	private int backup_y; // 保存点前的坦克纵坐标
	private int fire_rate = 0;// 开火的频率
	private boolean isAlive; // 坦克存活的标记
	private Blood blood;// 坦克的血条
	private Bullet bullet;// 坦克的子弹
	private boolean isProp_bullet; // 是否有子弹的buff
	
	Random seed = new Random();

	private static Toolkit tk = Toolkit.getDefaultToolkit();// 控制面板
	private static Image[] tankImages = null;// 存储全局静态

	static {
		tankImages = new Image[] { tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				// 我方坦克
				tk.getImage(Tank.class.getClassLoader().getResource("Images/mytankU.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("Images/mytankD.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("Images/mytankL.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("Images/mytankR.png")),

		};
	}

	/* 获取坦克的边界 */
	public Rectangle getRect() {
		return new Rectangle(x, y, GameConfig.TANK_WIDTH, GameConfig.TANK_HEIGHT);
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public Blood getBlood() {
		return blood;
	}

	public void setBlood(Blood blood) {
		this.blood = blood;
	}

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Tank(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Tank(int x, int y, Direction dir, boolean isEnemy,boolean isAlive, Blood blood,
			boolean isProp_bullet) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.isEnemy = isEnemy;
		this.isAlive = isAlive;
		this.blood = blood;
		this.isProp_bullet = isProp_bullet;
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

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public boolean isProp_bullet() {
		return isProp_bullet;
	}

	public void setProp_bullet(boolean isProp_bullet) {
		this.isProp_bullet = isProp_bullet;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void load() {
		x = backup_x;
		y = backup_y;
	}

	public void save() {
		backup_x = x;
		backup_y = y;
	}

	/**
	 * 绘制tank的方法
	 *
	 */
	public void draw(Graphics g) {
		fire_rate += GameConfig.THREAD_UPDATE_TIME;
		if (isEnemy) {
			switch (dir) {
			// 根据方向选择坦克的图片
			case U:
				g.drawImage(tankImages[0], x, y, null);
				break;
			case D:
				g.drawImage(tankImages[1], x, y, null);
				break;
			case L:
				g.drawImage(tankImages[2], x, y, null);
				break;
			case R:
				g.drawImage(tankImages[3], x, y, null);
				break;
            default:
                break;

			}

		} else {
			switch (dir) {
			// 根据方向选择坦克的图片
			case U:
				g.drawImage(tankImages[4], x, y, null);
				break;
			case D:
				g.drawImage(tankImages[5], x, y, null);
				break;
			case L:
				g.drawImage(tankImages[6], x, y, null);
				break;
			case R:
				g.drawImage(tankImages[7], x, y, null);
				break;
            default:
                break;
			}
		}

		// 绘制血条
		blood.draw(g);

	}

	public void move() {
		/**
		 * move函数描述了向键码所指的方向进行移动， 共四个方向：上、下、左、右 如果是敌方坦克，自动控制行走方向，换方向的概率为1/20;
		 */
		if (isEnemy && seed.nextInt(20) == 1) {
			int i = seed.nextInt(4);
			Direction[] dirs = Direction.values();
			dir = dirs[i];
		}
		// 如果是敌方坦克，坦克发射子弹，发射子弹的概率是1/5
		if (isEnemy && seed.nextInt(5) == 1) {
			this.fire(true);	
		}
		// //保存移动前的状态（x，y）
		// save();
		if (isEnemy) {
			if (dir == Direction.L) {
				this.x -= GameConfig.TANK_SPEED_X;
			} else if (dir == Direction.R) {
				this.x += GameConfig.TANK_SPEED_X;
			} else if (dir == Direction.U) {
				this.y -= GameConfig.TANK_SPEED_Y;
			} else if (dir == Direction.D) {
				this.y += GameConfig.TANK_SPEED_Y;
			}
		} else {
			if (dir == Direction.L) {
				this.x -= GameConfig.TANK_SPEED_X * 3 / 2;
			} else if (dir == Direction.R) {
				this.x += GameConfig.TANK_SPEED_X * 3 / 2;
			} else if (dir == Direction.U) {
				this.y -= GameConfig.TANK_SPEED_Y * 3 / 2;
			} else if (dir == Direction.D) {
				this.y += GameConfig.TANK_SPEED_Y * 3 / 2;
			}
		}
		// 再坦克向指定的dir方向移动后，判定是否有越界
		// 横坐标判定越界
		// 纵坐标判定越界
		if (x < 0 || x + GameConfig.TANK_WIDTH > GameConfig.GAME_WIDTH || y < 20
				|| y + GameConfig.TANK_HEIGHT > GameConfig.GAME_HEIGHT) {
			load();
		}

	}

	public void fire(boolean isEnemy) {
		// 判定两次开火的时间间隔是否满足设定
		if (fire_rate < GameConfig.INIT_BULLET_RATE) {
			return;
		}
		int bx = 0, by = 0;
		if (dir == Direction.L) {
			bx = x;
			by = y + GameConfig.TANK_HEIGHT / 2;
		} else if (dir == Direction.R) {
			bx = x + GameConfig.TANK_WIDTH;
			by = y + GameConfig.TANK_HEIGHT / 2;
		} else if (dir == Direction.U) {
			bx = x + GameConfig.TANK_WIDTH / 2;
			by = y;
		} else if (dir == Direction.D) {
			bx = x + GameConfig.TANK_WIDTH / 2;
			by = y + GameConfig.TANK_HEIGHT;
		}
		// 时间间隔计数器清零
		fire_rate = 0;
		//创建一个子弹，根据是否为敌我来创建子弹，在根据是否吃有道具来增加子弹数量的创建，来决定子弹的创建数。
		if (isEnemy) {
			Bullet b = new Bullet(bx, by, dir, true, true);
			TankWar.enemybullets.add(b);
		} else {
			if (isProp_bullet) {
				Bullet b = new Bullet(bx, by, dir, false, true, true);
				Bullet b1 = new Bullet(bx, by, dir, false, true, true);
				TankWar.mybullets.add(b);
				TankWar.mybullets.add(b1);
			} else {
				Bullet b = new Bullet(bx, by, dir, false, true, false);
				TankWar.mybullets.add(b);
			}
		}
	}
}

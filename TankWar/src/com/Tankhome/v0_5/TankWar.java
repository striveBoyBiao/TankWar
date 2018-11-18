package com.Tankhome.v0_5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
/**
 * 
 *TankWar.java 文件使用说明
 * 说明：主界面<br/>
 *  公开方法：
 * <ul>
 * <li>TankWar：</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class TankWar extends JFrame implements KeyListener, Runnable {
    /** 
    * @Fields serialVersionUID : 
    */ 
    private static final long serialVersionUID = 1L;
    // 绘制我方tank
    Tank myTank;
    // 绘制老鹰
    Home myHome;
    // 普通墙
    ArrayList<Commonwall> commonwalls;
    // 金属墙
    ArrayList<Metalwall> metalwalls;
    // 敌方坦克的集合
    ArrayList<Tank> enemyTanks;
    // 敌方子弹的集合
    static ArrayList<Bullet> enemybullets;
    // 我方子弹的集合
    static ArrayList<Bullet> mybullets;
    // 爆炸效果
    ArrayList<Bomb> bombs;
    // 树
    ArrayList<Tree> trees;
    // 河
    ArrayList<River> rivers;
    // 道具_子弹
    ArrayList<Prop> prop_bullets;
    // 道具——血
    ArrayList<Prop> prop_bloods;
    Miwu miwu;
    int propnumber = 0;
    int propnumber_blood = 0;

    public static int screen = 0;

    Random r = new Random();

    // 初始化四个方向
    boolean b_left = false;
    boolean b_right = false;
    boolean b_up = false;
    boolean b_down = false;
    // 游戏结束
    boolean isGameover = false;
    Image img;

    public TankWar(int screen) {
        this.setBounds(100, 0, GameConfig.GAME_WIDTH + 200, GameConfig.GAME_HEIGHT);
        this.setTitle("❤坦克大战❤                  开发者：聂明、梁迪、曹聪、何彪、陶伯瑞              开发开始时间：2016-07-27");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0, 0, 0));

        // 监听器
        this.addKeyListener(this);
        // 初始化我方坦克
        myTank = new Tank(GameConfig.GAME_WIDTH * 9 / 26, GameConfig.GAME_HEIGHT - GameConfig.TANK_HEIGHT * 3 / 2,
                Direction.U, false, true, null, false);
        myTank.setBlood(new Blood(myTank, 3, 3));

        // 初始化敌方坦克i * 395
        enemyTanks = new ArrayList<Tank>();
        for (int i = 0; i < GameConfig.INIT_ENEMY_TANK; i++) {
            Tank t = new Tank(3*i * GameConfig.TANK_WIDTH, 25, Direction.D, true,true, null, false);
            t.setBlood(new Blood(t, 2, 2));
            enemyTanks.add(t);
        }
        
         //初始化迷雾
        miwu = new Miwu(0,0,true,null);
        miwu.setBlood(new Blood(miwu, 100, 100));
        
        // 初始化道具—子弹的位置
        prop_bullets = new ArrayList<Prop>();
        {
            for (int i = 0; i < GameConfig.PROP_NUMBER; i++) {
                Prop t = new Prop(r.nextInt(700) + 50, r.nextInt(500) + 100, true, this, false);
                prop_bullets.add(t);
            }
        }
        //初始化道具—血的位置
        prop_bloods = new ArrayList<Prop>();
        {
            for (int i = 0; i < GameConfig.PROP_NUMBER; i++) {
                Prop t = new Prop(r.nextInt(700) + 100, r.nextInt(500) + 50, true, this, true);
                prop_bloods.add(t);
            }

        }

        // 初始化我方子弹集合
        mybullets = new ArrayList<Bullet>();
        // 初始化敌方坦克的集合
        enemybullets = new ArrayList<Bullet>();
        // 初始化爆炸效果的集合
        bombs = new ArrayList<Bomb>();

        // 线程的开始
        Thread gameThread = new Thread(this);
        gameThread.start();

        // 可视化和不可拉伸
        this.setResizable(false);
        this.setVisible(true);
        map(screen);
    }

    public void map(int screen) {
        // 初始化我方老鹰
        myHome = new Home(GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2,
                GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 9 / 5, true);

        switch (screen % 3) {
        // map1
        case 0: {
            // 初始化 普通墙的位置
            commonwalls = new ArrayList<Commonwall>();
            {
                // 家附近的墙
                for (int i = 0; i < 5; i++) {
                    // 家左边的墙
                    Commonwall t1 = new Commonwall(
                            GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2 - GameConfig.COMMONWALL_WIDTH - 10,
                            GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 + GameConfig.COMMONWALL_WIDTH * i,
                            true);
                    commonwalls.add(t1);
                    // 家右边的墙
                    Commonwall t2 = new Commonwall(GameConfig.GAME_WIDTH / 2 + GameConfig.HOME_WIDTH + 5,
                            GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 + GameConfig.COMMONWALL_WIDTH * i,
                            true);
                    commonwalls.add(t2);
                    // 家上面的墙
                    Commonwall t3 = new Commonwall(
                            GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2 - GameConfig.COMMONWALL_WIDTH - 10
                                    + GameConfig.COMMONWALL_WIDTH * i,
                            GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 - GameConfig.COMMONWALL_HEIGHT, true);
                    commonwalls.add(t3);
                }

                for (int t = 0; t < 8; t++) {
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 3; j++) {
                            Commonwall t3 = new Commonwall(40 + 110 * t + GameConfig.COMMONWALL_WIDTH * j,
                                    90 + GameConfig.COMMONWALL_WIDTH * i * 2, true);
                            commonwalls.add(t3);

                        }
                    }
                }
            }
            // 初始化金属墙
            metalwalls = new ArrayList<Metalwall>();
            {
                for (int i = 0; i < 5; i++) {
                    Metalwall t = new Metalwall(GameConfig.METALWALL_WIDTH * i, 400);
                    Metalwall k = new Metalwall(GameConfig.GAME_WIDTH - (i + 1) * GameConfig.METALWALL_WIDTH, 400);
                    metalwalls.add(t);
                    metalwalls.add(k);
                }
                for (int i = 0; i < 18; i++) {
                    Metalwall t = new Metalwall(GameConfig.GAME_WIDTH / 2 - 200 + GameConfig.METALWALL_WIDTH * i,
                            500 + GameConfig.TREE_HEIGHT * 2);
                    metalwalls.add(t);
                }
            }

            // 初始化树的位置
            trees = new ArrayList<Tree>();
            {
                for (int i = 0; i < 30; i++) {
                    Tree t = new Tree(5 + GameConfig.TREE_WIDTH * i, 500, this);
                    trees.add(t);
                }
            }

            // 初始化河的位置
            rivers = new ArrayList<River>();
            {
                River t1 = new River(176, 331, this);
                rivers.add(t1);
                River t2 = new River(680, 331, this);
                rivers.add(t2);
            }
        }
            break;

        // map2
        case 1: {
            // 初始化 普通墙的位置
            commonwalls = new ArrayList<Commonwall>();
            {
                // 家附近的墙
                for (int i = 0; i < 5; i++) {
                    // 家左边的墙
                    Commonwall t1 = new Commonwall(
                            GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2 - GameConfig.COMMONWALL_WIDTH - 10,
                            GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 + GameConfig.COMMONWALL_WIDTH * i,
                            true);
                    commonwalls.add(t1);
                    // 家右边的墙
                    Commonwall t2 = new Commonwall(GameConfig.GAME_WIDTH / 2 + GameConfig.HOME_WIDTH + 5,
                            GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 + GameConfig.COMMONWALL_WIDTH * i,
                            true);
                    commonwalls.add(t2);
                    // 家上面的墙
                    Commonwall t3 = new Commonwall(
                            GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2 - GameConfig.COMMONWALL_WIDTH - 10
                                    + GameConfig.COMMONWALL_WIDTH * i,
                            GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 - GameConfig.COMMONWALL_HEIGHT, true);
                    commonwalls.add(t3);
                }
                // 普通墙
                for (int i = 0; i < 23; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (i < 8 || i > 12) {
                            Commonwall t1 = new Commonwall(6 + GameConfig.COMMONWALL_WIDTH * i * 2,
                                    220 + GameConfig.COMMONWALL_HEIGHT * j * 2, true);
                            commonwalls.add(t1);
                        }
                    }

                }

            }
            // 初始化金属墙
            metalwalls = new ArrayList<Metalwall>();
            {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Metalwall t = new Metalwall(320 + i * 80, 210 + j * 80);
                        metalwalls.add(t);
                    }

                }
                for (int i = 0; i < 2; i++) {
                    Metalwall t = new Metalwall(0 + GameConfig.METALWALL_WIDTH * i, 160);
                    Metalwall t1 = new Metalwall(839 + GameConfig.METALWALL_WIDTH * i, 160);
                    metalwalls.add(t);
                    metalwalls.add(t1);
                }
                for (int i = 0; i < 4; i++) {
                    Metalwall t = new Metalwall(381 + GameConfig.METALWALL_WIDTH * i, 480);
                    metalwalls.add(t);
                }
            }

            // 初始化树的位置
            trees = new ArrayList<Tree>();
            {
                for (int i = 0; i < 10; i++) {
                    Tree t = new Tree(220 + GameConfig.TREE_WIDTH * i, 550, this);
                    Tree t1 = new Tree(80 + GameConfig.TREE_WIDTH * i, 80, this);
                    Tree t2 = new Tree(450 + GameConfig.TREE_WIDTH * i, 130, this);
                    trees.add(t);
                    trees.add(t1);
                    trees.add(t2);
                }
                for (int i = 0; i < 3; i++) {
                    Tree t1 = new Tree(14 + GameConfig.TREE_WIDTH * i, 550, this);
                    Tree t2 = new Tree(581 + GameConfig.TREE_WIDTH * i, 550, this);
                    Tree t3 = new Tree(732 + GameConfig.TREE_WIDTH * i, 550, this);
                    trees.add(t1);
                    trees.add(t2);
                    trees.add(t3);
                }
            }

            // 初始化河的位置
            rivers = new ArrayList<River>();
            {
                River t = new River(110, 433, this);
                River t1 = new River(165, 433, this);
                River t2 = new River(526, 433, this);
                River t3 = new River(677, 433, this);
                River t4 = new River(828, 433, this);
                River t5 = new River(856, 433, this);
                rivers.add(t);
                rivers.add(t1);
                rivers.add(t2);
                rivers.add(t3);
                rivers.add(t4);
                rivers.add(t5);
            }
            break;
        }
        case 2:
            // 初始化 普通墙的位置
            commonwalls = new ArrayList<Commonwall>(); {
            // 家附近的墙
            for (int i = 0; i < 5; i++) {
                // 家左边的墙
                Commonwall t1 = new Commonwall(
                        GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2 - GameConfig.COMMONWALL_WIDTH - 10,
                        GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 + GameConfig.COMMONWALL_WIDTH * i, true);
                commonwalls.add(t1);
                // 家右边的墙
                Commonwall t2 = new Commonwall(GameConfig.GAME_WIDTH / 2 + GameConfig.HOME_WIDTH + 5,
                        GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 + GameConfig.COMMONWALL_WIDTH * i, true);
                commonwalls.add(t2);
                // 家上面的墙
                Commonwall t3 = new Commonwall(
                        GameConfig.GAME_WIDTH / 2 - GameConfig.HOME_WIDTH / 2 - GameConfig.COMMONWALL_WIDTH - 10
                                + GameConfig.COMMONWALL_WIDTH * i,
                        GameConfig.GAME_HEIGHT - GameConfig.HOME_HEIGHT * 2 - GameConfig.COMMONWALL_HEIGHT, true);
                commonwalls.add(t3);
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(j <= i){
                        Commonwall t = new Commonwall(250+GameConfig.COMMONWALL_WIDTH*4*i,100+GameConfig.COMMONWALL_HEIGHT*3*j,true);
                        commonwalls.add(t); 
                    }
                    if(j >= i){
                        Commonwall t = new Commonwall(30+GameConfig.COMMONWALL_WIDTH*4*i,150+GameConfig.COMMONWALL_HEIGHT*3*j,true);
                        commonwalls.add(t); 
                    }   
                }
            }
        }
            // 初始化金属墙
            metalwalls = new ArrayList<Metalwall>(); {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i == j) {
                        Metalwall t = new Metalwall(40 + GameConfig.METALWALL_WIDTH * i * 3,
                                70 + GameConfig.METALWALL_HEIGHT * j * 2);
                        Metalwall t1 = new Metalwall(76 + GameConfig.METALWALL_WIDTH* i * 3,
                                70 + GameConfig.METALWALL_HEIGHT * j * 2);
                        metalwalls.add(t);
                        metalwalls.add(t1);
                    }
                }
            }
        }

            // 初始化树的位置
            trees = new ArrayList<Tree>(); {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (i == j) {
                        Tree b = new Tree(76 + GameConfig.METALWALL_WIDTH * i * 3,
                                107 + GameConfig.METALWALL_HEIGHT * j * 2, this);   
                        Tree b1 = new Tree(112 + GameConfig.METALWALL_WIDTH * i * 3,
                                107 + GameConfig.METALWALL_HEIGHT * j * 2, this);
                        Tree b2 = new Tree(148 + GameConfig.METALWALL_WIDTH * i * 3,
                                107 + GameConfig.METALWALL_HEIGHT * j * 2, this);
                        trees.add(b);
                        trees.add(b1);
                        trees.add(b2);  
                    }
                }
            }
        }

            // 初始化河的位置
            rivers = new ArrayList<River>(); {
            // River t = new River(200, 200, this);
            // rivers.add(t);
        }

        }
    }

    /**
     * 双缓冲： 目的：防止屏幕扫面试抖动 原理：先将一个想要画的内容画在一张画布上，然后再将其投放到屏幕之上，自然不会抖动
     */
    @Override
    public void paint(Graphics g) {
        if (img == null) {
            // new一个新的图片，参数分别是“图片的宽”、“图片的高度”、“图片的色彩深度8位色深度”。
            img = new BufferedImage(GameConfig.GAME_WIDTH + 200, GameConfig.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        } else {
            // 清屏幕
            img.getGraphics().clearRect(0, 0, GameConfig.GAME_WIDTH + 200, GameConfig.GAME_HEIGHT);
        }
        // 画图片的里面的内容
        cachPaint(img.getGraphics());
        g.drawImage(img, 0, 0, null);
        img.flush();
    }

    /**
     * 绘制画板上面的内容
     */
    public void cachPaint(Graphics g) {
        g.clearRect(0, 0, GameConfig.GAME_WIDTH + 200, GameConfig.GAME_HEIGHT);
        g.setColor(Color.white);
        g.drawRect(0, 0, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        g.drawString("上：↑", GameConfig.GAME_WIDTH + 80, GameConfig.GAME_HEIGHT * 3 / 30);
        g.drawString("下：↓", GameConfig.GAME_WIDTH + 80, GameConfig.GAME_HEIGHT * 4 / 30);
        g.drawString("左：←", GameConfig.GAME_WIDTH + 80, GameConfig.GAME_HEIGHT * 5 / 30);
        g.drawString("右：→", GameConfig.GAME_WIDTH + 80, GameConfig.GAME_HEIGHT * 6 / 30);
//      g.drawString("暂停：p", GameConfig.GAME_WIDTH + 75, GameConfig.GAME_HEIGHT * 7 / 30);
        g.drawString("开火：SPACE", GameConfig.GAME_WIDTH + 60, GameConfig.GAME_HEIGHT * 8 / 30);
        g.drawString("重新开始：F1", GameConfig.GAME_WIDTH + 60, GameConfig.GAME_HEIGHT * 9 / 30);

        g.drawString("第" + (screen + 1) + "关", GameConfig.GAME_WIDTH + 70, GameConfig.GAME_HEIGHT * 15 / 30);
        g.drawString("我方坦克的血量："+myTank.getBlood().getBloodNum(), GameConfig.GAME_WIDTH + 50, GameConfig.GAME_HEIGHT * 37 / 50);
        g.drawString("剩余敌人的数量：" + enemyTanks.size(), GameConfig.GAME_WIDTH + 50, GameConfig.GAME_HEIGHT * 39 / 50);
        g.drawString("已经消灭敌人的数量：" + (GameConfig.INIT_ENEMY_TANK - enemyTanks.size()), GameConfig.GAME_WIDTH + 50,
                GameConfig.GAME_HEIGHT * 41 / 50);
        g.drawString("分数：" + (GameConfig.INIT_ENEMY_TANK - enemyTanks.size()) * 10, GameConfig.GAME_WIDTH + 50,
                GameConfig.GAME_HEIGHT * 43 / 50);
        //绘制迷雾消失
        miwu.getBlood().minus(1);
        // 绘制老鹰
        if (myHome.isAlive()) {
            myHome.draw(g);
        }
        // 游戏结束Gameover
        if ( !myHome.isAlive() || !myTank.isAlive() || myTank == null) {
            myHome = null;
            myTank = null;
            g.clearRect(0, 0, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
            isGameover = true;
            Font font = new Font(null, 2, 40);
            g.setFont(font);
            g.setColor(Color.CYAN);
            g.drawString("Game Over !!!", GameConfig.GAME_WIDTH * 2 / 5 - 100, GameConfig.GAME_HEIGHT / 2);
            g.drawString("请按F1重新开始游戏", GameConfig.GAME_WIDTH * 2 / 5 - 100, GameConfig.GAME_HEIGHT / 2+60);
            return;
        }
        // 敌方坦克全部死亡，按enter键进入下一关
        if (enemyTanks.size() == 0) {
            isGameover = true;
            g.clearRect(0, 0, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
            Font font = new Font(null, 2, 40);
            g.setFont(font);
            g.setColor(Color.CYAN);
            g.drawString("You Win!!!  按enter键进入下一关", GameConfig.GAME_WIDTH * 1 / 5 + 20, GameConfig.GAME_HEIGHT / 2);
            return;
        }

        // 绘制河
        for (int i = 0; i < rivers.size(); i++) {
            River t = rivers.get(i);
            t.draw(g);
        }

        // 碰撞问题
        // 我方子弹与地方坦克之间的碰撞
        for (int i = 0; i < mybullets.size(); i++) {
            Bullet b = mybullets.get(i);
            // 我方子弹与家的碰撞
            if (b.getRect().intersects(myHome.getRect())) {
                b.setAlive(false);
                myHome.setAlive(false);
            }
            // 遍历地方坦克
            for (int j = 0; j < enemyTanks.size(); j++) {
                Tank t = enemyTanks.get(j);
                if (b.getRect().intersects(t.getRect())) {
                    // 子弹死亡
                    b.setAlive(false);
                    // 坦克扣血
                    t.getBlood().minus(1);
                    {
                        if (!t.getBlood().isAlive())
                            // 坦克消亡
                            t.setAlive(false);
                    }
                    // 获取碰撞的中心点
                    Rectangle r = b.getRect().intersection(t.getRect());
                    // 载入爆炸效果
                    Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                    bombs.add(bomb);
                    break;
                }
            }
        }
        for (int i = 0; i < enemybullets.size(); i++) {
            Bullet b = enemybullets.get(i);
            // 敌方子弹与家之间的碰撞
            if (b.getRect().intersects(myHome.getRect())) {
                b.setAlive(false);
                myHome.setAlive(false);
            }
            // 敌方子弹与我方坦克之间的碰撞
            if (b.getRect().intersects(myTank.getRect())) {
                // 子弹死亡
                b.setAlive(false);
                // 我方坦克扣血
                myTank.getBlood().minus(1);
                if (!myTank.getBlood().isAlive()) {
                    // 我方坦克死亡
                    myTank.setAlive(false);
                }
                if (!myTank.isAlive()) {
                    // 获取我方坦克与敌方子弹碰撞的中心焦点
                    Rectangle r = b.getRect().intersection(myTank.getRect());
                    // 载入爆炸效果
                    Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                    bombs.add(bomb);
                    return;
                }
            }
        }

        // 敌方子弹与我方子弹的碰撞
        for (int i = 0; i < mybullets.size(); i++) {
            Bullet b1 = mybullets.get(i);
            for (int j = 0; j < enemybullets.size(); j++) {
                Bullet b2 = enemybullets.get(j);
                // 判断是否发生碰撞
                if (b1.getRect().intersects(b2.getRect())) {
                    // 我方子弹与敌方子弹均死亡
                    b1.setAlive(false);
                    b2.setAlive(false);
                    if (!b1.isAlive() && !b2.isAlive()) {
                        // 获取碰撞中心的焦点
                        Rectangle r = b1.getRect().intersection(b2.getRect());
                        Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                        bombs.add(bomb);
                    }
                }
            }
        }

        // 我方子弹与金属墙之间的碰撞
        for (int i = 0; i < mybullets.size(); i++) {
            Bullet b = mybullets.get(i);
            for (int t = 0; t < metalwalls.size(); t++) {
                Metalwall w = metalwalls.get(t);
                if (b.getRect().intersects(w.getRect())) {
                    // 我方子弹死亡
                    b.setAlive(false);
                    Rectangle r = b.getRect().intersection(w.getRect());
                    // 载入爆炸效果
                    Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                    bombs.add(bomb);
                    break;
                }
            }
            // 我方子弹与可摧毁墙之间的碰撞
            for (int t = 0; t < commonwalls.size(); t++) {
                Commonwall w = commonwalls.get(t);
                if (b.getRect().intersects(w.getRect())) {
                    // 子弹、墙均消亡
                    b.setAlive(false);
                    w.setAlive(false);
                    Rectangle r = b.getRect().intersection(w.getRect());
                    // 载入爆炸效果
                    Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                    bombs.add(bomb);
                    break;
                }
            }
        }

        // 敌方子弹与金属墙之间的碰撞
        for (int i = 0; i < enemybullets.size(); i++) {
            Bullet b = enemybullets.get(i);
            for (int t = 0; t < metalwalls.size(); t++) {
                Metalwall w = metalwalls.get(t);
                if (b.getRect().intersects(w.getRect())) {
                    // 子弹死亡
                    b.setAlive(false);
                    Rectangle r = b.getRect().intersection(w.getRect());
                    // 载入爆炸效果
                    Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                    bombs.add(bomb);
                    break;
                }
            }
            // 敌方子弹与可摧毁墙之间的碰撞
            for (int t = 0; t < commonwalls.size(); t++) {
                Commonwall w = commonwalls.get(t);
                if (b.getRect().intersects(w.getRect())) {
                    // 子弹与墙均死亡
                    b.setAlive(false);
                    w.setAlive(false);
                    Rectangle r = b.getRect().intersection(w.getRect());
                    // 载入爆炸效果
                    Bomb bomb = new Bomb(r.x - GameConfig.BOMB_WIDTH / 2, r.y - GameConfig.BOMB_HEIGHT / 2);
                    bombs.add(bomb);
                    break;
                }
            }

        }

        // 绘制我方坦克
        if (myTank.isAlive()) {
            myTank.draw(g);
        } else {
            myTank = null;
        }

        // 判断是坦克之间是否发生碰撞
        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank t1 = enemyTanks.get(i);
            // 敌方坦克与家的碰撞
            if (t1.getRect().intersects(myHome.getRect())) {
                t1.load();
            }
            // 敌方坦克与河之间的
            for (int j = 0; j < rivers.size(); j++) {
                River b = rivers.get(j);
                if (t1.getRect().intersects(b.getRect())) {
                    t1.load();
                }
            }
            if (t1.isAlive()) {
                t1.draw(g);
                // 保存坦克移动前的位置
                t1.save();
                t1.move();
                // 判断敌方坦克之间是否发生碰撞
                for (int j = 0; j < enemyTanks.size() && j != i; j++) {
                    Tank t2 = enemyTanks.get(j);
                    // 判断是否发生碰撞
                    if (t1.getRect().intersects(t2.getRect())) {
                        // 回滚到之前的位置
                        t1.load();
                        t2.load();
                    }
                }
                // 判断敌方坦克与不可摧毁墙之间的碰撞
                for (int j = 0; j < metalwalls.size(); j++) {
                    Metalwall w = metalwalls.get(j);
                    if (w.getRect().intersects(t1.getRect())) {
                        // 回滚
                        t1.load();
                    }
                    if (w.getRect().intersects(myTank.getRect())) {
                        // 回滚
                        myTank.load();
                    }
                }
                // 判断敌方坦克与可摧毁墙之间的碰撞
                for (int j = 0; j < commonwalls.size(); j++) {
                    Commonwall w = commonwalls.get(j);
                    if (w.getRect().intersects(t1.getRect())) {
                        t1.load();
                    }
                    if (w.getRect().intersects(myTank.getRect())) {
                        myTank.load();
                    }
                }

                // 判断敌方坦克与我方坦克之间的碰撞
                if (t1.getRect().intersects(myTank.getRect())) {
                    t1.load();
                    myTank.load();
                }
            } else {
                enemyTanks.remove(t1);
                i--;
            }
        }

        // 敌方子弹的绘制
        for (int i = 0; i < enemybullets.size(); i++) {
            Bullet b = enemybullets.get(i);
            if (b.isAlive()) {
                b.move();
                b.draw(g);
            } else {
                enemybullets.remove(b);
                i--;
            }
        }

        // 绘制金属墙
        for (int i = 0; i < metalwalls.size(); i++) {
            Metalwall w = metalwalls.get(i);
            w.draw(g);
        }
        // 绘制可以摧毁的墙
        for (int i = 0; i < commonwalls.size(); i++) {
            Commonwall w = commonwalls.get(i);
            if (w.isAlive()) {
                w.draw(g);
            } else {
                commonwalls.remove(w);
            }
        }

        // 爆炸效果的绘制
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            if (b.isAlive()) {
                b.draw(g);
            } else {
                bombs.remove(b);
                i--;
            }
        }

        // 绘制树
        for (int i = 0; i < trees.size(); i++) {
            Tree t = trees.get(i);
            t.draw(g);
        }

        //绘制子弹道具
        {
            Prop t1 = prop_bullets.get(propnumber);
            t1.draw(g);
            if (myTank.getRect().intersects(t1.getRect())) {
                prop_bullets.remove(t1);
                myTank.setProp_bullet(true);
            }
            // 画我方子弹
            for (int i = 0; i < mybullets.size(); i++) {
                Bullet b = mybullets.get(i);
                if (b.isAlive()) {
                    b.move();
                    b.draw(g);
                } else {
                    mybullets.remove(b);
                    i--;
                }
            }
        }

        // 绘制道具——血
        {
            Prop t = prop_bloods.get(propnumber_blood);
            if (myTank.getRect().intersects(t.getRect())) {
                prop_bloods.remove(t);
                myTank.setBlood(new Blood(myTank, 5, 5));
            }
            t.draw(g);
        }
         //绘制迷雾
        
        miwu.draw(g);
    }

    // keyPressed按压的监控器
    public void keyPressed(KeyEvent e) {
        // 按压控制移动方向
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            b_left = true;
            checkDir();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            b_right = true;
            checkDir();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            b_up = true;
            checkDir();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            b_down = true;
            checkDir();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // 判断是否开火
            if(myTank != null){
                myTank.fire(false);
            }
            
        }

    }

    private void checkDir() {
        // 检查方向
        if (b_left && !b_right && !b_up && !b_down) {
            myTank.setDir(Direction.L); // 向左
        } else if (!b_left && b_right && !b_up && !b_down) {
            myTank.setDir(Direction.R); // 向右
        } else if (!b_left && !b_right && b_up && !b_down) {
            myTank.setDir(Direction.U); // 向上
        } else if (!b_left && !b_right && !b_up && b_down) {
            myTank.setDir(Direction.D); // 向下
        }
        // 保存我方坦克的位置
        // 我方坦克的移动
        myTank.save();
        myTank.move();

        // 我方坦克与敌方坦克之间的碰撞处理
        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank t = enemyTanks.get(i);
            if (myTank.getRect().intersects(t.getRect())) {
                // 回滚
                myTank.load();
                t.load();
            }
        }
        //金属墙与我方坦克之间的碰撞
        for (int i = 0; i < metalwalls.size(); i++) {
            Metalwall w = metalwalls.get(i);
            if (myTank.getRect().intersects(w.getRect())) {
                myTank.load();
            }
        }
        //公共墙与我方坦克之间的碰撞
        for (int i = 0; i < commonwalls.size(); i++) {
            Commonwall w = commonwalls.get(i);
            if (myTank.getRect().intersects(w.getRect())) {
                myTank.load();
            }
        }
        // 我方坦克与河之间的碰撞
        for (int j = 0; j < rivers.size(); j++) {
            River b = rivers.get(j);
            if (myTank.getRect().intersects(b.getRect())) {
                myTank.load();
            }
        }

        // 我方坦克与家的碰撞
        if (myTank.getRect().intersects(myHome.getRect())) {
            myTank.load();
        }
    }

    // 释放按键
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            b_left = false;
            checkDir();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            b_right = false;
            checkDir();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            b_up = false;
            checkDir();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            b_down = false;
            checkDir();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && enemyTanks.size() == 0) {
            screen++;
            new TankWar(screen);
        }
        if (e.getKeyCode() == KeyEvent.VK_F1 && (myHome == null||myTank == null)) {
            new TankWar(screen);
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    /**
     * 
     * 线程的运用
     */

    public void run() {
        while (!isGameover) {
            // 
            this.repaint();
            try {
                Thread.sleep(GameConfig.THREAD_UPDATE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}

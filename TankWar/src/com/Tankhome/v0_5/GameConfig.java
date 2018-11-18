package com.Tankhome.v0_5;
/**
 * 
 *
 *GameConfig.java 文件使用说明
 * 说明：常量配置<br/>
 *  公开方法：
 * <ul>
 * <li>GameConfig：</li>
 * </ul>
 *
 * @version ver 4.0.0
 * @author hebiao
 * @since 作成日期：2017年7月1日（hebiao）<br/>
 *        改修日期：
 */
public class GameConfig {
    public final static int THREAD_UPDATE_TIME=50;//主画面线程的刷新速度；
    
    public final static int GAME_WIDTH=910;//遊戲畫面寬度910；
    public final static int GAME_HEIGHT=725;//遊戲畫面高度725；
    
    public final static int INIT_ENEMY_TANK =9;//初始游戏的敌方坦克的数量 ；
    
    public final static int TANK_WIDTH = 33;//tank寬度；
    public final static int TANK_HEIGHT = 34;//tank高度；
    public final static int TANK_SPEED_X=4;//tank橫向移動速度；
    public final static int TANK_SPEED_Y=4;//tank縱向移動速度；        
    public final static int TANK_LENGTH = 40; //炮管的长度；
    
    public final static int INIT_BULLET_RATE=600;//坦克开火发射子弹的时间 
    public final static int BULLET_WIDTH=12;//子弹的高；
    public final static int BULLET_HEIGHT=12;//子弹的宽；
    public final static int BULLET_SPEED = 15;//子弹的速度；
    
    public final static int COMMONWALL_WIDTH = 20; //普通墙的宽度；
    public final static int COMMONWALL_HEIGHT = 20; //普通墙的高度；
    public final static int METALWALL_WIDTH = 34; //金属墙的宽度；
    public final static int METALWALL_HEIGHT = 34; //金属墙的高度；
    
    public final static int TREE_WIDTH = 30;//树的宽度；
    public final static int TREE_HEIGHT= 30;//树的高度；
    public final static int RIVER_WIDTH = 55;//河的宽度；
    public final static int RIVER_HEIGHT = 155;//河的高度；
    public static final int HOME_WIDTH = 30;  //家的高；
    public static final int HOME_HEIGHT = 30;  //家的宽度；
        
    public final static int BLOOD_HEIGHT=3;//血块的高度；
    
    public static final int PROP_WIDTH = 45;    //道具的宽；
    public static final int PROP_HEIGHT = 22;   //道具的长；
    public static final int PROP_SPACE = 5000;  //道具出来的时间；
    public static final int PROP_LAST_TIME =2000;   //道具的持续时间；
    public static final int PROP_NUMBER = 10000;    //道具的总量 ；   
    
    public static final int BOMB_WIDTH = 64;    //爆炸范围的宽度   
    public static final int BOMB_HEIGHT = 64;   //爆炸范围的高度
    
    
    
}

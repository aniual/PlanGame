package cn.aniu.game;

/*
* 游戏的父类
* @author aniu
* */


import java.awt.*;

public class GameObjetcts {
     Image img;
     double x,y;
     int speed;
     int width,height;

     public void drawSelf(Graphics g){
         g.drawImage(img, (int)x, (int)y, null);
     }

    public GameObjetcts(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObjetcts(Image img, double x, double y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public GameObjetcts() {
    }

    /*
    * 返回物体 所在的矩形,便于后续的碰撞检测
    * @return
    * */
    public Rectangle getRect(){
         return new Rectangle((int)x, (int)y, width, height);
    }
}

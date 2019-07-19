package cn.aniu.game;

/*
* 飞机游戏 的主窗口
* @author aniu
* */


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class PlanGameframe extends Frame {

    Image bg = GameUtil.getImage("images/bg.jpg");
    Image plane = GameUtil.getImage("images/plane.png");

    Plane plan = new Plane(plane, 250, 250);

    //Shell shell = new Shell();

    Shell[] shells = new Shell[50];
    //int  planeX = 250,planeY=250;

    Explode boo;

    Date startTime = new Date();
    Date endTime;

    int period; //游戏持续的时间


    @Override
    public void paint(Graphics g) {     //程序自动调用,g相当于一只画笔
        Color c = g.getColor();
        g.drawImage(bg,0,0,null);
        plan.drawSelf(g);
        for (int i = 0; i < shells.length; i++){
            shells[i].draw(g);

            boolean peng = shells[i].getRect().intersects(plan.getRect());
            if (peng){
                plan.live = false;
                if (boo == null){
                    boo = new Explode(plan.x, plan.y);
                    endTime = new Date();
                    period = (int)((endTime.getTime() - startTime.getTime())/1000);
                }
                boo.draw(g);
            }

            //计时功能给出提示
            if (!plan.live){
                g.setColor(Color.red);
                Font f = new Font("宋体",Font.BOLD,50);
                g.setFont(f);
                g.drawString("时间:" + period + "秒",(int)plan.x, (int)plan.y);
            }
        }
        g.setColor(c);

    }

    //帮助我们反复的重画窗口
    class PaintThead extends Thread{
        @Override
        public void run() {
            while(true){
                repaint();//重画

                try {
                    Thread.sleep(40);  //1s=1000ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //键盘监听的内部类
    class KeyMonitor extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            plan.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plan.minusDirection(e);
        }
    }


    /*
    * 初始化窗口
    * */
    public void launchFrame(){
        this.setTitle("阿牛小游戏");
        this.setVisible(true);
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setLocation(300,300);

        //关闭窗口的一个方法
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintThead().start();  //启动重画窗口的位置
        addKeyListener(new KeyMonitor());

        //初始化50个炮弹
        for (int i = 0; i < shells.length;i++){
            shells[i] = new Shell();
        }
    }

    public static void main(String[] args){
        PlanGameframe f = new PlanGameframe();
        f.launchFrame();
    }


    //添加双缓冲减少界面闪动
    private Image offScreenImage = null;

    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}

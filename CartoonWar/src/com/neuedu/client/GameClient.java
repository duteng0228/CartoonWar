package com.neuedu.client;


import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.constant.Constant;
import com.neuedu.entity.BackGround;
import com.neuedu.entity.Boom;
import com.neuedu.entity.Boss;
import com.neuedu.entity.Bullet;
import com.neuedu.entity.EnemyMouse;
import com.neuedu.entity.Mouse;
import com.neuedu.entity.Prop;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SoundPlayer;

/**
* @ClassName: GameClient
* @Description: 游戏客户端
* @author student_dt
* @date 2019年8月17日 下午3:17:59
*
*/
public class GameClient extends Frame{
	// 创建一个自身集合
	public List<Mouse> mouses = new ArrayList<>();
	// 创建一个mouse对象
//	Mouse mouse = new Mouse(100,100,"plane/01.png",this,true);
	// 创建道具类
	public List<Prop> props = new ArrayList<>();
	// 创建一个子弹类
//	Bullet bullet = new Bullet(400,100,"bullet/bullet_01.png");
	// 创建一个子弹的集合
    public List<Bullet> bullets = new ArrayList<Bullet>();
    // 创建一个背景对象
    BackGround backImg =  new BackGround(0, 0, "back.png");
	// 创建敌人
    EnemyMouse enemy1 = new EnemyMouse(700,200,1,this,false);
    EnemyMouse enemy2 = new EnemyMouse(700,600,2,this,false);
    EnemyMouse enemy3 = new EnemyMouse(900,200,2,this,false);
    EnemyMouse enemy4 = new EnemyMouse(900,600,2,this,false);
    // 创建敌人集合
    public List<Mouse> enemys = new ArrayList<>();
    // 创建一个爆炸集合
    public List<Boom> booms = new ArrayList<>();
    // 创建boss集合
    public List<Mouse> bosss = new ArrayList<>();
	// 解决图片闪烁问题
    @Override
    public void update(Graphics g) {
    	Image backImg = createImage(Constant.LAUNCHFRAME_WIDTH,Constant.LAUNCHFRAME_HEIGHT);
    	Graphics backg = backImg.getGraphics();
    	Color color = backg.getColor();
    	backg.setColor(color.WHITE);
    	backg.fillRect(0, 0, Constant.LAUNCHFRAME_WIDTH, Constant.LAUNCHFRAME_HEIGHT);
    	backg.setColor(color);
    	paint(backg);
    	g.drawImage(backImg, 0, 0, null);
    }
    Mouse mouse = null;
	// 生成客户端窗口的方法
	public void launchFrame() {
		// 启动背景音乐
		SoundPlayer soundPlayer = new SoundPlayer("com/neuedu/sound/background.mp3");
		soundPlayer.start();
		// 设置窗口大小
		this.setSize(Constant.LAUNCHFRAME_WIDTH, Constant.LAUNCHFRAME_HEIGHT);
		// 设置标题
		this.setTitle("飞机大作战");
		// 设置是否显示
		this.setVisible(true);
		Image img = GetImageUtil.getImg("icon.png");
		this.setIconImage(img);
		// 禁止最大化
		this.setResizable(false);
		// 窗口居中
		this.setLocationRelativeTo(null);
		// 关闭窗口
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 往我方容器添加内容
		mouse = new Mouse(100,100,"plane/01.png",this,true);
		mouses.add(mouse);
		// 添加鼠标监听
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("你点了一下鼠标");
			}
		});
		// 添加键盘监听
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				mouse.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				mouse.keyReleased(e);
			}
		});
		
		// 启动线程(多画)
		new paintThread().start();
		// 往敌人集合中添加敌人
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		// 往boss集合里添加boss
		bosss.add(new Boss(1300, 250, this,false));
	}
	// 重写paint 方法
	@Override
	public void paint(Graphics g) {
		// 画背景图
		backImg.draw(g);
		// 循环画飞机
		for(int i=0;i<mouses.size();i++) {
			Mouse mouse = mouses.get(i);
			mouse.draw(g);
			mouse.containProps(props);
		}
		// 循环出每个子弹
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitEnemys(enemys);
			bullet.hitEnemys(mouses);
			bullet.hitEnemys(bosss);
		}
		g.drawString("发射子弹的数量"+bullets.size(), 10, 50);
		g.drawString("当前敌机的数量"+enemys.size(), 10, 70);
		g.drawString("当前爆炸的数量"+booms.size(), 10, 90);
		g.drawString("当前我方的数量"+mouses.size(), 10, 110);
		g.drawString("当前道具的数量"+props.size(), 10, 130);
		// 循环出敌人
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		// 循环爆炸
		for(int i=0;i<booms.size();i++) {
			booms.get(i).draw(g);
		}
		// 循环道具
		for(int i=0;i<props.size();i++) {
			props.get(i).draw(g);
		}
		if(enemys.size() == 0) {
			// 循环boss
	        for(int i=0;i<bosss.size();i++) {
			    bosss.get(i).draw(g);
	        }
		}
		
	}
	// 内部类 移动多画
	class paintThread extends Thread {
		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}

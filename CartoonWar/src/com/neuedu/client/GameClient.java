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
* @Description: ��Ϸ�ͻ���
* @author student_dt
* @date 2019��8��17�� ����3:17:59
*
*/
public class GameClient extends Frame{
	// ����һ��������
	public List<Mouse> mouses = new ArrayList<>();
	// ����һ��mouse����
//	Mouse mouse = new Mouse(100,100,"plane/01.png",this,true);
	// ����������
	public List<Prop> props = new ArrayList<>();
	// ����һ���ӵ���
//	Bullet bullet = new Bullet(400,100,"bullet/bullet_01.png");
	// ����һ���ӵ��ļ���
    public List<Bullet> bullets = new ArrayList<Bullet>();
    // ����һ����������
    BackGround backImg =  new BackGround(0, 0, "back.png");
	// ��������
    EnemyMouse enemy1 = new EnemyMouse(700,200,1,this,false);
    EnemyMouse enemy2 = new EnemyMouse(700,600,2,this,false);
    EnemyMouse enemy3 = new EnemyMouse(900,200,2,this,false);
    EnemyMouse enemy4 = new EnemyMouse(900,600,2,this,false);
    // �������˼���
    public List<Mouse> enemys = new ArrayList<>();
    // ����һ����ը����
    public List<Boom> booms = new ArrayList<>();
    // ����boss����
    public List<Mouse> bosss = new ArrayList<>();
	// ���ͼƬ��˸����
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
	// ���ɿͻ��˴��ڵķ���
	public void launchFrame() {
		// ������������
		SoundPlayer soundPlayer = new SoundPlayer("com/neuedu/sound/background.mp3");
		soundPlayer.start();
		// ���ô��ڴ�С
		this.setSize(Constant.LAUNCHFRAME_WIDTH, Constant.LAUNCHFRAME_HEIGHT);
		// ���ñ���
		this.setTitle("�ɻ�����ս");
		// �����Ƿ���ʾ
		this.setVisible(true);
		Image img = GetImageUtil.getImg("icon.png");
		this.setIconImage(img);
		// ��ֹ���
		this.setResizable(false);
		// ���ھ���
		this.setLocationRelativeTo(null);
		// �رմ���
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// ���ҷ������������
		mouse = new Mouse(100,100,"plane/01.png",this,true);
		mouses.add(mouse);
		// ���������
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("�����һ�����");
			}
		});
		// ��Ӽ��̼���
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
		
		// �����߳�(�໭)
		new paintThread().start();
		// �����˼�������ӵ���
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		// ��boss���������boss
		bosss.add(new Boss(1300, 250, this,false));
	}
	// ��дpaint ����
	@Override
	public void paint(Graphics g) {
		// ������ͼ
		backImg.draw(g);
		// ѭ�����ɻ�
		for(int i=0;i<mouses.size();i++) {
			Mouse mouse = mouses.get(i);
			mouse.draw(g);
			mouse.containProps(props);
		}
		// ѭ����ÿ���ӵ�
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitEnemys(enemys);
			bullet.hitEnemys(mouses);
			bullet.hitEnemys(bosss);
		}
		g.drawString("�����ӵ�������"+bullets.size(), 10, 50);
		g.drawString("��ǰ�л�������"+enemys.size(), 10, 70);
		g.drawString("��ǰ��ը������"+booms.size(), 10, 90);
		g.drawString("��ǰ�ҷ�������"+mouses.size(), 10, 110);
		g.drawString("��ǰ���ߵ�����"+props.size(), 10, 130);
		// ѭ��������
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		// ѭ����ը
		for(int i=0;i<booms.size();i++) {
			booms.get(i).draw(g);
		}
		// ѭ������
		for(int i=0;i<props.size();i++) {
			props.get(i).draw(g);
		}
		if(enemys.size() == 0) {
			// ѭ��boss
	        for(int i=0;i<bosss.size();i++) {
			    bosss.get(i).draw(g);
	        }
		}
		
	}
	// �ڲ��� �ƶ��໭
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

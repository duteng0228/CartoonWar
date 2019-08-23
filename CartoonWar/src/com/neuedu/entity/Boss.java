package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: Boss
* @Description: Boss��
* @author student_dt
* @date 2019��8��22�� ����6:26:13
*
*/
public class Boss extends Mouse implements ActionAble{
	private boolean up;
	private int speed = 5;
	public Boss() {
	}
	public Boss(int x,int y,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 99999;
	}
	// ����һ��bossͼƬ����
	private static Image[] imgs = new Image[1];
	static {
		for(int i=0;i<imgs.length;i++) {
			imgs[i] = GetImageUtil.getImg("boss/0"+(i+1)+".png");
		}
	}
	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(count > 0) {
			count = 0;
		}
		g.drawImage(imgs[count++], x, y, null);
		move();
		g.drawString("��ǰѪ��"+blood, x, y);
	}
	@Override
	public void move() {
		x -= speed;
		if(x < 1150) {
			x = 1150;
			if(up) {
				y -= speed;
			}
			if(!up) {
				y += speed;
			}
			if(y > Constant.LAUNCHFRAME_HEIGHT-imgs[0].getHeight(null)) {
				up = true;
			}
			if(y < 30) {
				up = false;
			}
		}
		if(random.nextInt(500)>450) {
			fire();
		}
		
	}
	// �������
	Random random = new Random();
	// ��ȡboss�ľ���
	public Rectangle getRec() {
		return new Rectangle(x, y, imgs[0].getWidth(null),imgs[0].getHeight(null));
	}
	@Override
	public void fire() {
		play.play("com/neuedu/sound/firestar.mp3");
		Bullet bullet = new Bullet(x, y+imgs[0].getHeight(null)/4, "bullet/enemybullet_1.png",gc,false);
		gc.bullets.add(bullet);
	}

}

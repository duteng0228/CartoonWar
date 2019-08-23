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
* @Description: Boss类
* @author student_dt
* @date 2019年8月22日 下午6:26:13
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
	// 创建一个boss图片数组
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
		g.drawString("当前血量"+blood, x, y);
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
	// 随机数组
	Random random = new Random();
	// 获取boss的矩形
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

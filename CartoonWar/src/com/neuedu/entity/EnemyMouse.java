package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: EnemyMouse
* @Description: ������
* @author student_dt
* @date 2019��8��19�� ����7:42:24
*
*/
public class EnemyMouse extends Mouse implements ActionAble {
	// �з�����
	private Integer enemyType;
	private Integer speed;
	private GameClient gc;
	// �з�����
	private static Image[] imgs1 = {
		GetImageUtil.getImg("enemy/01.png"),
		GetImageUtil.getImg("enemy/02.png"),
		GetImageUtil.getImg("enemy/03.png"),
		GetImageUtil.getImg("enemy/04.png"),
		GetImageUtil.getImg("enemy/05.png"),
	};
	public EnemyMouse() {
		
	}
	public EnemyMouse(int x,int y,int enemyType,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.speed = 0;
		this.enemyType = enemyType;
		this.gc = gc;
		this.isGood = isGood;
	}
	@Override
	public void move() {
		x -= speed;
	}
	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(count > 4) {
			count = 0;
		}
		g.drawImage(imgs1[count++], x, y, null);
		move();
		
		
		if(random.nextInt(500)>490) {
			fire();
		}
	}
	// �����
	Random random = new Random();
	
	// �з�����
	public void fire() {
		Bullet bullet = new Bullet(x, y+this.imgs1[0].getHeight(null)/2, "bullet/enemybullet_1.png", gc,false);
		gc.bullets.add(bullet);
	}
	// ��ȡͼƬ����
	public Rectangle getRec() {
		return new Rectangle(x, y, this.imgs1[0].getWidth(null),this.imgs1[0].getHeight(null));
	}

}

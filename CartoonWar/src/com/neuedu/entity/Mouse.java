package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SinglePlay;

/**
* @ClassName: Mouse
* @Description: ����һ��������
* @author student_dt
* @date 2019��8��19�� ����8:33:29
*
*/
public class Mouse extends GameObj implements ActionAble{
	// �����������������
	SinglePlay play = new SinglePlay();
	// �ٶ�
	private int speed;
	// ����
	private boolean left;
	private boolean up;
	private boolean right;
	private boolean down;
	public GameClient gc;
	// �ж��Ƿ�û�
	public boolean isGood;
	// �ж��ӵ��ȼ�
	public int BulletLevel = 1;
	// ���Ѫֵ
	public int blood;
	public Mouse() {
		
	}
	public Mouse(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 15;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 100;
	}
	// �ƶ�
	@Override
	public void move() {
		if(left) {
			x -= speed;
		}
		if(up) {
			y -= speed;
		}
		if(right) {
			x += speed;
		}
		if(down) {
			y += speed;
		}
		outOfBound();
		
	}
	// ��һ���ɻ�
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		g.drawString("��ǰѪ��"+blood, 10, 150);
	}
	// ����߽�����
	public void outOfBound() {
		if(y <= 0) {
			y = 0;
		}
		if(x <= 0) {
			x = 0;
		}
		if(x >= Constant.LAUNCHFRAME_WIDTH - img.getWidth(null)) {
			x = Constant.LAUNCHFRAME_WIDTH - img.getWidth(null);
		}
		if(y >= Constant.LAUNCHFRAME_HEIGHT - img.getHeight(null)) {
			y = Constant.LAUNCHFRAME_HEIGHT - img.getHeight(null);
		}
	}
	
	// ��������
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		default:
			break;
		}
		
	}
	// �����ͷ�
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_M:
			fire();
			break;

		default:
			break;
		}
		
	}
	// �ɻ����𷽷�
	public void fire() {
		play.play("com/neuedu/sound/firestar.mp3");
		Bullet bullet = new Bullet(x+this.img.getWidth(null), y+this.img.getHeight(null)/4+30, "bullet/bullet_0"+BulletLevel+".png",gc,true);
		gc.bullets.add(bullet);
	}
	// ��ȡ���︸��ľ��η���
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null),this.img.getHeight(null));
	}
	// ����ҷ���ɫ�Ƿ���ײ����
	public void containProp(Prop prop) {
		if(this.getRec().intersects(prop.getRec())) {
			// �Ƴ�����
			gc.props.remove(prop);
			// �����ӵ��ȼ�
			if(BulletLevel > 2) {
				BulletLevel = 3;
				return;
			}
			this.BulletLevel += 1;
		}
	}
	// ����ҷ���ɫ�Ƿ���ײ�������
	public void containProps(List<Prop> props) {
		if(props == null) {
			return;
		}else {
			for(int i=0;i<props.size();i++) {
				containProp(props.get(i));
			}
		}
	}

}

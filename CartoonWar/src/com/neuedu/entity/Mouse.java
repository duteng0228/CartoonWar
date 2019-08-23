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
* @Description: 创建一个仓鼠类
* @author student_dt
* @date 2019年8月19日 上午8:33:29
*
*/
public class Mouse extends GameObj implements ActionAble{
	// 创建单次声音类对象
	SinglePlay play = new SinglePlay();
	// 速度
	private int speed;
	// 方向
	private boolean left;
	private boolean up;
	private boolean right;
	private boolean down;
	public GameClient gc;
	// 判断是否好坏
	public boolean isGood;
	// 判断子弹等级
	public int BulletLevel = 1;
	// 添加血值
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
	// 移动
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
	// 画一个飞机
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		g.drawString("当前血量"+blood, 10, 150);
	}
	// 处理边界问题
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
	
	// 键盘摁下
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
	// 键盘释放
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
	// 飞机开火方法
	public void fire() {
		play.play("com/neuedu/sound/firestar.mp3");
		Bullet bullet = new Bullet(x+this.img.getWidth(null), y+this.img.getHeight(null)/4+30, "bullet/bullet_0"+BulletLevel+".png",gc,true);
		gc.bullets.add(bullet);
	}
	// 获取怪物父类的矩形方法
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null),this.img.getHeight(null));
	}
	// 检测我方角色是否碰撞道具
	public void containProp(Prop prop) {
		if(this.getRec().intersects(prop.getRec())) {
			// 移除道具
			gc.props.remove(prop);
			// 更改子弹等级
			if(BulletLevel > 2) {
				BulletLevel = 3;
				return;
			}
			this.BulletLevel += 1;
		}
	}
	// 检测我方角色是否碰撞多个道具
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

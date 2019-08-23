package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SinglePlay;

/**
* @ClassName: Bullet
* @Description: 子弹类
* @author student_dt
* @date 2019年8月19日 下午1:58:31
*
*/
public class Bullet extends GameObj implements ActionAble{
	// 创建单次声音类对象
	SinglePlay singlePlay = new SinglePlay();
	// 子弹速度
	private Integer speed;
	// 把客户端拿过来
	public GameClient gc;
	// 子弹类型
	public boolean isGood;
	public Bullet() {
		
	}
	public Bullet(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 20;
		this.gc = gc;
		this.isGood = isGood;
	}
	@Override
	public void move() {
		if(isGood) {
		    x += speed;
		}else {
			x -= speed;
		}
		outOfBound(); 
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage( img, x, y, null);
		move();
	}
	// 子弹出界销毁
	public void outOfBound() {
		if(x > Constant.LAUNCHFRAME_WIDTH || x < 0) {
			gc.bullets.remove(this);
		}
	}
	// 子弹碰撞怪物问题 矩形相交问题
	public boolean hitEnemy(Mouse mouse) {
		if(this.getRec().intersects(mouse.getRec()) && this.isGood != mouse.isGood) {
			Boom boom = new Boom(mouse.x, mouse.y,gc);
			if(mouse.isGood) {
				// 打中角色产生音效
				singlePlay.play("com/neuedu/sound/peng.mp3");
				mouse.blood -= 10;
				if(mouse.blood == 0) {
					// 移除自身
				    gc.mouses.remove(mouse);
				}
				gc.bullets.remove(this);
			}else {
				// 打中敌人产生音效
				singlePlay.play("com/neuedu/sound/peng.mp3");
				if(mouse instanceof Boss) {
					mouse.blood -= 100;
					if(mouse.blood <= 0) {
						// 移除boss
					    gc.bosss.remove(mouse);
					    // 移除子弹
					    gc.bullets.remove(this);
					}
					
				}else {
					// 移除集合中的敌人
				    gc.enemys.remove(mouse);
				    // 打中敌人移除子弹
				    gc.bullets.remove(this);
				    // 随机生成道具
					Random random = new Random();
				    // 打中敌人产生道具
				    if(random.nextInt(500) > 200) {
				    	Prop prop = new Prop(mouse.x, mouse.y, "prop/prop.png");
				        gc.props.add(prop);
				    }
				}
			}
			// 打中敌人产生爆炸效果
			gc.booms.add(boom);
			
			return true;
		}
		return false;
	}
	// 子弹打多个怪物
	public boolean hitEnemys(List<Mouse> enemys) {
		for(int i=0;i<enemys.size();i++) {
			if(hitEnemy(enemys.get(i))) {
				return true;
			}
		}
	    return false;
	}
	
	// 获取到子弹的矩形
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null),this.img.getHeight(null));
	}

}

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
* @Description: �ӵ���
* @author student_dt
* @date 2019��8��19�� ����1:58:31
*
*/
public class Bullet extends GameObj implements ActionAble{
	// �����������������
	SinglePlay singlePlay = new SinglePlay();
	// �ӵ��ٶ�
	private Integer speed;
	// �ѿͻ����ù���
	public GameClient gc;
	// �ӵ�����
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
	// �ӵ���������
	public void outOfBound() {
		if(x > Constant.LAUNCHFRAME_WIDTH || x < 0) {
			gc.bullets.remove(this);
		}
	}
	// �ӵ���ײ�������� �����ཻ����
	public boolean hitEnemy(Mouse mouse) {
		if(this.getRec().intersects(mouse.getRec()) && this.isGood != mouse.isGood) {
			Boom boom = new Boom(mouse.x, mouse.y,gc);
			if(mouse.isGood) {
				// ���н�ɫ������Ч
				singlePlay.play("com/neuedu/sound/peng.mp3");
				mouse.blood -= 10;
				if(mouse.blood == 0) {
					// �Ƴ�����
				    gc.mouses.remove(mouse);
				}
				gc.bullets.remove(this);
			}else {
				// ���е��˲�����Ч
				singlePlay.play("com/neuedu/sound/peng.mp3");
				if(mouse instanceof Boss) {
					mouse.blood -= 100;
					if(mouse.blood <= 0) {
						// �Ƴ�boss
					    gc.bosss.remove(mouse);
					    // �Ƴ��ӵ�
					    gc.bullets.remove(this);
					}
					
				}else {
					// �Ƴ������еĵ���
				    gc.enemys.remove(mouse);
				    // ���е����Ƴ��ӵ�
				    gc.bullets.remove(this);
				    // ������ɵ���
					Random random = new Random();
				    // ���е��˲�������
				    if(random.nextInt(500) > 200) {
				    	Prop prop = new Prop(mouse.x, mouse.y, "prop/prop.png");
				        gc.props.add(prop);
				    }
				}
			}
			// ���е��˲�����ըЧ��
			gc.booms.add(boom);
			
			return true;
		}
		return false;
	}
	// �ӵ���������
	public boolean hitEnemys(List<Mouse> enemys) {
		for(int i=0;i<enemys.size();i++) {
			if(hitEnemy(enemys.get(i))) {
				return true;
			}
		}
	    return false;
	}
	
	// ��ȡ���ӵ��ľ���
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null),this.img.getHeight(null));
	}

}

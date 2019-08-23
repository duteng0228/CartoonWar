package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.neuedu.action.ActionAble;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: Prop
* @Description: ������
* @author student_dt
* @date 2019��8��20�� ����8:26:01
*
*/
public class Prop extends GameObj implements ActionAble{
	private int speed;
	public Prop() {
		
	}
	public Prop(int x,int y,String imgName) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 5;
	}
	// �����ƶ����ǶȺͷ���
	private double theta = Math.PI/4;
	@Override
	public void move() {
		x +=speed*Math.cos(theta);
		y +=speed*Math.sin(theta);
		if(x<0 || x>Constant.LAUNCHFRAME_WIDTH-img.getWidth(null)) {
			theta = Math.PI-theta;
		}
		if(y<35 || y>Constant.LAUNCHFRAME_HEIGHT-img.getHeight(null)) {
			theta = -theta;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
	}
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getHeight(null), this.img.getHeight(null));
	}

}

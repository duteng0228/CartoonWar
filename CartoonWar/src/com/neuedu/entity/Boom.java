package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: Boom
* @Description:爆炸类
* @author student_dt
* @date 2019年8月20日 上午11:14:11
*
*/
public class Boom extends GameObj implements ActionAble{
	private boolean isLife;
	private GameClient gc;
	public static Image[] boomImgs = new Image[4];
	static {
		for(int i=0;i<4;i++) {
			boomImgs[i] = GetImageUtil.getImg("Boom/"+(i+1)+".png");
		}
	}
	public Boom() {
		
	}
	public Boom(int x,int y,GameClient gc) {
		this.x = x;
		this.y = y;
		this.isLife = true;
		this.gc = gc;
	}

	@Override
	public void move() {
		
	}
	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(isLife) {
		    if(count > 2) {
		    	isLife = false;
		    	return;
		    }
		    g.drawImage(boomImgs[count++], x, y, null);
		}else {
			gc.booms.remove(this); 
		}
		
	}

}

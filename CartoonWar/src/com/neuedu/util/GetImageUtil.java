package com.neuedu.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


/**
* @ClassName: getImageUtil
* @Description: 获取图片的工具类
* @author student_dt
* @date 2019年8月17日 下午3:46:09
*
*/
public class GetImageUtil {
	// 获取图片的方法
	public static Image getImg(String imgName) {
		URL resource = GetImageUtil.class.getClassLoader().getResource("com/neuedu/img/"+imgName);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufferedImage;
	} 

}

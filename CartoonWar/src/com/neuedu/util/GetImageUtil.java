package com.neuedu.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


/**
* @ClassName: getImageUtil
* @Description: ��ȡͼƬ�Ĺ�����
* @author student_dt
* @date 2019��8��17�� ����3:46:09
*
*/
public class GetImageUtil {
	// ��ȡͼƬ�ķ���
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

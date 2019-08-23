package com.neuedu.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @ClassName: Constant
* @Description: ������
* @author student_dt
* @date 2019��8��17�� ����3:23:06
*
*/
public class Constant {
	// ��ζ�ȡ�����ļ�
	public static Properties prop = new Properties();
	static Integer Launchframe_Width= null;
	static Integer Launchframe_Height= null;
	static {
		InputStream inStream = Constant.class.getResourceAsStream("/gameConfiguration.propertise");
		try {
			prop.load(inStream);
			Launchframe_Width = Integer.parseInt(prop.getProperty("Launchframe_Width"));
			Launchframe_Height = Integer.parseInt(prop.getProperty("Launchframe_Height"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// ������
	public static final int LAUNCHFRAME_WIDTH = Launchframe_Width;
	// ����߶�
	public static final int LAUNCHFRAME_HEIGHT= Launchframe_Height;

}

package com.neuedu.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @ClassName: Constant
* @Description: 常量类
* @author student_dt
* @date 2019年8月17日 下午3:23:06
*
*/
public class Constant {
	// 如何读取配置文件
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
	
	
	
	// 定义宽度
	public static final int LAUNCHFRAME_WIDTH = Launchframe_Width;
	// 定义高度
	public static final int LAUNCHFRAME_HEIGHT= Launchframe_Height;

}

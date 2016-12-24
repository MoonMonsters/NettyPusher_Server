/**
 * 
 */
package edu.csuft.chentao.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.csuft.chentao.controller.HeadImageHandler;
import edu.csuft.chentao.pojo.req.HeadImage;

/**
 * @author csuft.chentao
 *
 *         2016年12月10日 下午8:10:06
 */
public class OperationUtil {

	/**
	 * 把头像保存到本地
	 * 
	 * @param headImage
	 *            头像类
	 */
	public static void saveHeadImage(HeadImage headImage) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new HeadImageHandler().handle(null, headImage);
			}
		}).start();
	}

	/** 关闭数据库的PreparedStatement和ResultSet连接 */
	public static void closeDataConnection(PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件名获得HeadImage对象
	 * @param filename 文件名
	 */
	public static HeadImage getHeadImage(String filename) {
		HeadImage headImage = new HeadImage();

		try {
			//在headimage目录下，获得filename文件
			File file = new File("./headimage", filename);
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();
			byte[] buf = new byte[length];
			fis.read(buf, 0, length);
			headImage.setBuf(buf);
			headImage.setFilename(filename);
			headImage.setLength(length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return headImage;
	}
}

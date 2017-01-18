/**
 * 
 */
package edu.csuft.chentao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
	public static void saveHeadImage(byte[] headImage,int fileId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					File file = new File("./headimage",String.valueOf(fileId));
					
					//如果已经存在，则删除
					if(file.exists()){
						file.delete();
					}
					
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(headImage);
					fos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
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
	 * 根据用户id从文件夹中得到该文件
	 * @param userId 用户id
	 * @return 文件的byte数组
	 */
	public static byte[] getHeadImage(int fileId){
		byte[] buf = null;
		try{
			File file = new File(Constant.PATH,String.valueOf(fileId));
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();
			buf = new byte[length];
			fis.read(buf, 0, length);
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return buf;
	}
}

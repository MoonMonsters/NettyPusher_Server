/**
 * 
 */
package edu.csuft.chentao.util;

import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;

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
	public static void saveHeadImage(final byte[] headImage, final int fileId) {
		new Thread(new Runnable() {
			public void run() {
				try {
					File file = new File("./headimage", String.valueOf(fileId));

					// 如果已经存在，则删除
					if (file.exists()) {
						file.delete();
					}

					FileOutputStream fos = new FileOutputStream(file);
					fos.write(headImage);
					fos.close();
				} catch (Exception e) {
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
	 * 
	 * @param userId
	 *            用户id
	 * @return 文件的byte数组
	 */
	public static byte[] getHeadImage(int fileId) {
		byte[] buf = null;
		try {
			File file = new File(Constant.PATH, String.valueOf(fileId));
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();
			buf = new byte[length];
			fis.read(buf, 0, length);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buf;
	}

	/**
	 * 休眠0.5秒
	 */
	public static void sleepFor500() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 休眠5秒
	 */
	public static void sleepFor5000() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据群id得到群名
	 * 
	 * @param groupId
	 *            群id
	 * @return 群名称
	 */
	public static String getGroupNameWithGroupId(int groupId) {

		return GroupTableOperate.getGroupNameWithGroupId(groupId);
	}

	/**
	 * 发送ReturnInfoResp对象信息
	 * 
	 * @param chc
	 *            发送消息对象
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	public static void sendReturnInfoResp(ChannelHandlerContext chc, int type,
			String description) {
		ReturnInfoResp resp = new ReturnInfoResp();
		resp.setType(type);
		resp.setObj(description);
		chc.writeAndFlush(resp);
		Logger.log("发送ReturnInfoResp消息----"+type);
	}

}

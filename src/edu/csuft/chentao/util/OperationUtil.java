/**
 * 
 */
package edu.csuft.chentao.util;

import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	 * 创建文件夹
	 * 
	 * @param path
	 *            路径
	 */
	private static void createDirectory(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

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
					createDirectory(Constant.PATH_HEAD_IMAGE);
					File file = new File(Constant.PATH_HEAD_IMAGE,
							String.valueOf(fileId));

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
			File file = new File(Constant.PATH_HEAD_IMAGE,
					String.valueOf(fileId));
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
	 * 根据图片序列号，得到图片资源
	 * 
	 * @param imageSerialNumber
	 *            图片序列号
	 * @return 图片资源
	 */
	public static byte[] getMessageImage(int groupId, String imageSerialNumber) {
		byte[] buf = null;
		try {
			File file = new File(Constant.PATH_MESSAGE_IMAGE + "/" + groupId,
					imageSerialNumber);
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
	 * 保存图片资源
	 * 
	 * @param groupId
	 * @param imageSerialNumber
	 * @param image
	 */
	public static void saveMessageImage(final int groupId,
			final String imageSerialNumber, final byte[] image) {
		new Thread(new Runnable() {
			public void run() {
				try {

					createDirectory(Constant.PATH_MESSAGE_IMAGE + "/" + groupId);

					File file = new File(Constant.PATH_MESSAGE_IMAGE + "/"
							+ groupId, imageSerialNumber);

					// 如果文件不存在，则创建
					if (!file.exists()) {
						file.createNewFile();
					}

					FileOutputStream fos = new FileOutputStream(file);
					fos.write(image);
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 保存上传的群文件
	 * 
	 * @param groupId
	 *            群ID
	 * @param serialNumber
	 *            文件序列号
	 * @param buf
	 *            资源
	 */
	public static void saveGroupFile(int groupId, String serialNumber,
			byte[] buf) {

		createDirectory(Constant.PATH_FILE + "/" + groupId);

		// 创建文件
		File file = new File(Constant.PATH_FILE + "/" + groupId, serialNumber);
		// 如果不存在，则新建
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileOutputStream fos = null;
		try {
			// 写入
			fos = new FileOutputStream(file);
			fos.write(buf);
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO流错误");
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得群文件
	 * 
	 * @param groupId
	 *            群ID
	 * @param serialNumber
	 *            文件序列号
	 * 
	 */
	public static byte[] getGroupFile(int groupId, String serialNumber) {
		byte[] buf = null;

		File file = new File(Constant.PATH_FILE + "/" + groupId, serialNumber);
		FileInputStream fis = null;
		try {
			buf = new byte[(int) file.length()];
			fis = new FileInputStream(file);
			// 读取文件内容
			fis.read(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		Logger.log("发送ReturnInfoResp消息----" + type);
	}

	/**
	 * 得到随机16位序列号
	 * 
	 * @return 随机序列号
	 */
	public static String getSerialNumber() {
		String serial = "abcdefghijklmnopqrstuvwxyz0123456";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append(serial.charAt((int) (Math.random() * serial.length())));
		}

		return sb.toString();
	}
}

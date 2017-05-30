/**
 * 
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.csuft.chentao.pojo.req.Announcement;
import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 * 
 *         2017年4月16日 下午3:53:07
 */
public class AnnouncementTableOperate {

	/**
	 * 插入数据
	 * 
	 * @param announcement
	 *            公告数据对象
	 * @return 是否插入成功
	 */
	public static boolean insert(Announcement announcement) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "insert into " + AnnouncementTable.TABLE_ALL_FIELD
					+ " values(?,?,?,?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, announcement.getSerialnumber());
			ps.setString(2, announcement.getTitle());
			ps.setString(3, announcement.getContent());
			ps.setString(4, announcement.getUsername());
			ps.setString(5, announcement.getTime());
			ps.setInt(6, announcement.getUserid());
			ps.setInt(7, announcement.getGroupid());

			if (ps.executeUpdate() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return false;
	}

	public static void delete() {

	}

	public static void query() {

	}

	/**
	 * 根据序列号，得到公告数据
	 * 
	 * @param serialNumber
	 *            公告序列号
	 * @return 公告对象
	 */
	public static Announcement queryAnnouncementBySerialNumber(
			String serialNumber) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Announcement announcement = new Announcement();
		try {
			// serial_number,title,content,username,time,user_id,group_id
			String sql = "select serial_number,title,content,username,time,user_id,group_id from "
					+ AnnouncementTable.TABLE_NAME
					+ " where "
					+ AnnouncementTable.SERIAL_NUMBER + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, serialNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				announcement.setContent(rs.getString(3));
				announcement.setGroupid(rs.getInt(7));
				announcement.setSerialnumber(rs.getString(1));
				announcement.setTime(rs.getString(5));
				announcement.setTitle(rs.getString(2));
				announcement.setUserid(rs.getInt(6));
				announcement.setUsername(rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return announcement;
	}

}

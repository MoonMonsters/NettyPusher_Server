/**
 * 
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 *
 *         2017年4月16日 下午4:44:07
 */
public class AnnouncementReaderOperate {

	/**
	 * 插入数据
	 * 
	 * @return 是否插入数据成功
	 */
	public static boolean insert(AnnouncementReader reader) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "insert into " + AnnouncementReader.TABLE_ALL_FIELD
					+ " values(?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, reader.getSerialnumber());
			ps.setInt(2, reader.getUserid());
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return false;
	}

	/**
	 * 删除
	 * 
	 * @return 是否删除成功
	 */
	public static boolean delete(String serialnumber) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "delete from " + AnnouncementReader.TABLE_NAME
					+ " where " + AnnouncementReader.SERIAL_NUMBER + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, serialnumber);
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return false;
	}

}

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
 *         2017年2月7日 下午10:30:01
 */
public class GroupOperationTableOperate {

	/**
	 * 插入
	 */
	public static void insert(GroupOperationTable table) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "insert into" + GroupOperationTable.ALLFIELD
					+ GroupOperationTable.VALUES;
			ps = connection.prepareStatement(sql);
			ps.setInt(1, table.getType());
			ps.setInt(2, table.getUserId());
			ps.setInt(3, table.getGroupId());
			ps.setString(4, table.getDescription());
			ps.setInt(5, table.getReaderId());

			if (ps.executeUpdate() > 0) { // 插入成功
				//TODO
			} else { // 插入失败

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}
	}

	/**
	 * 根据readerId查询数据
	 * 
	 * @param readerId
	 *            阅读此条消息的用户
	 * @return
	 */
	public static GroupOperationTable queryByReaderId(int readerId) {
		GroupOperationTable table = null;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupOperationTable.ID + ","
					+ GroupOperationTable.TYPE + ","
					+ GroupOperationTable.USERID + ","
					+ GroupOperationTable.GROUPID + ","
					+ GroupOperationTable.DESCRIPTION + " from "
					+ GroupOperationTable.GROUPOPERATIONTABLE + " where "
					+ GroupOperationTable.READERID + " = ? limit 0,1";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, readerId);
			rs = ps.executeQuery();
			if (rs.next()) {
				table = new GroupOperationTable();
				table.setType(rs.getInt(2));
				table.setUserId(rs.getInt(3));
				table.setGroupId(rs.getInt(4));
				table.setDescription(rs.getString(5));

				/*
				 * 消息读取完毕，删除
				 */
				int id = rs.getInt(1);
				delete(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return table;
	}

	/**
	 * 删除已阅读的消息
	 * 
	 * @param id
	 *            编号
	 */
	public static void delete(int id) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "delete from "
					+ GroupOperationTable.GROUPOPERATIONTABLE + " where "
					+ GroupOperationTable.ID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}
	}
}

/**
 * groupuser数据表相关操作
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.pojo.resp.UserCapitalResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 *
 *         2016年12月8日 下午9:39:40
 */
public class GroupUserTableOperate {

	/**
	 * 插入数据
	 * 
	 * @param groupId
	 *            群id
	 * @param userId
	 *            用户id
	 * @param capital
	 *            身份值
	 * @return
	 */
	public static synchronized boolean insert(int groupId, int userId,
			int capital) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// 插入数据
			ps = connection.prepareCall("insert into "
					+ GroupUserTable.GROUPUSERTABLE_ALL_FIELD
					+ " values(?,?,?)");
			ps.setInt(1, groupId);
			ps.setInt(2, userId);
			ps.setInt(3, capital);
			System.out.println(groupId + "-->" + userId + "-->" + capital);
			if (ps.executeUpdate() > 0) { // 执行成功
				return true;
			} else { // 执行失败
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return false;
	}

	/**
	 * 移除数据
	 */
	public static synchronized ReturnInfoResp remove(GroupOperationReq req) {

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnInfoResp resp = new ReturnInfoResp();

		try {

			if (isExit(req.getGroupId(), req.getUserId1())) { // 如果群id和用户id存在
				ps = connection.prepareStatement("delete from "
						+ GroupUserTable.GROUPUSERTABLE + " where "
						+ GroupUserTable.GROUPID + "=? and "
						+ GroupUserTable.USERID + "=?");
				ps.setInt(1, req.getGroupId());
				ps.setInt(2, req.getUserId1());
				rs = ps.executeQuery();
				if (rs.next()) {
					resp.setType(Constant.TYPE_RETURN_INFO_SUCCESS);
					resp.setDescription("退出群成功");
				} else {
					resp.setType(Constant.TYPE_RETURN_INFO_FAIL);
					resp.setDescription("退出群失败");
				}
			} else {
				resp.setType(Constant.TYPE_RETURN_INFO_FAIL);
				resp.setDescription("退出群失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;

	}

	/**
	 * 判断该条数据在数据表中是否存在
	 * 
	 * @param connection
	 *            Connection对象，连接数据库
	 * @param groupid
	 *            群id
	 * @param userid
	 *            用户id
	 * @return 该数据是否存在
	 */
	public static boolean isExit(int groupid, int userid) {
		boolean isExit = false;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 在邀请用户加入群之前，先判断用户和群是否存在，如果不存在，则直接返回
		if (!UserTableOperate.isExitUserWithUserId(userid)
				|| !GroupTableOperate.isExitGroupWithGroupId(groupid)) {
			return isExit;
		}

		try {
			ps = connection.prepareStatement("select * from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.GROUPID + "= ? and "
					+ GroupUserTable.USERID + "= ?");
			ps.setInt(1, groupid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) { // 在群里已经存在
				Logger.log("isExit-->"+userid+"已经存在");
				isExit = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return isExit;
	}

	/**
	 * 根据群id得到该群中所有的成员id及其身份信息
	 * 
	 * @param groupid
	 *            群id
	 * @return 成员id列表
	 */
	public static List<UserCapitalResp> selectIdAndCapitalInUserWithGroupId(
			int groupid) {
		List<UserCapitalResp> userCapitalList = new ArrayList<UserCapitalResp>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select userid,capital from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.GROUPID + "=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				userCapitalList.add(new UserCapitalResp(rs.getInt(1), rs
						.getInt(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return userCapitalList;
	}

	/**
	 * 根据群id获取该群中所有用户的id
	 * 
	 * @param groupId
	 *            群id
	 */
	public static List<Integer> selectAllUserIdsWithGroupId(int groupId) {
		List<Integer> userIdList = new ArrayList<Integer>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select userid from " + GroupUserTable.GROUPUSERTABLE
					+ " where " + GroupUserTable.GROUPID + "=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userIdList.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return userIdList;
	}

	/**
	 * 根據用戶id得到所有的群的id
	 * 
	 * @param userId
	 *            用戶id
	 * @return 該用戶所在群的id集合
	 */
	public static List<Integer> selectGroupIdsWithUserId(int userId) {
		List<Integer> groupIdList = new ArrayList<Integer>();
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupUserTable.GROUPID + " from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.USERID + " = ? ";
			ps = connection.prepareStatement(sql);
			// 设置用户id
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				groupIdList.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return groupIdList;
	}

	/**
	 * 更新用户的身份
	 * 
	 * @param userId
	 *            需要更新的用户id
	 * @param groupId
	 *            群id
	 * @param capital
	 *            新的身份
	 */
	public static ReturnInfoResp updateCapital(int userId, int groupId,
			int capital) {
		ReturnInfoResp resp = new ReturnInfoResp();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "update " + GroupUserTable.GROUPUSERTABLE + " set "
					+ GroupUserTable.CAPITAL + "=? where "
					+ GroupUserTable.USERID + "=? and "
					+ GroupUserTable.GROUPID + "=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, capital);
			ps.setInt(2, userId);
			ps.setInt(3, groupId);
			if (ps.executeUpdate() > 0) {
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS);
				resp.setDescription("更新成功");
			} else {
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_FAIL);
				resp.setDescription("更新失败，请稍后再试");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 退出群
	 * 
	 * @return 如果操作成功，则返回true，否则返回false
	 */
	public static boolean exitGroup(int groupId, int userId) {

		boolean result = false;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		/**
		 * 如果不存在，则直接返回
		 */
		if (!isExit(groupId, userId)) {
			return result;
		}

		try {
			String sql = "delete from " + GroupUserTable.GROUPUSERTABLE
					+ " where " + GroupUserTable.GROUPID + " = ? and "
					+ GroupUserTable.USERID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			ps.setInt(2, userId);
			if (ps.executeUpdate() > 0) { // 执行删除操作
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return result;
	}

	/**
	 * 根据群id和身份值，读取对应的用户id（群主或者管理员）
	 */
	public static List<Integer> getCapital_0_WithGroupId(int groupId) {
		List<Integer> userIdList = new ArrayList<Integer>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupUserTable.USERID + " from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.GROUPID + " = ? and ("
					+ GroupUserTable.CAPITAL + " = ? or "
					+ GroupUserTable.CAPITAL + " = ?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			ps.setInt(2, Constant.TYPE_GROUP_CAPITAL_ADMIN);
			ps.setInt(3, Constant.TYPE_GROUP_CAPITAL_OWNER);
			rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				userIdList.add(userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return userIdList;
	}

	/**
	 * 根据用户id获得身份值
	 * 
	 * @param userId
	 *            用户id
	 * @return 身份值
	 */
	public static int getCapitalWithUserIdAndGroupId(int groupId, int userId) {
		int capital = -1;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupUserTable.CAPITAL + " from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.USERID + " = ? and "
					+ GroupUserTable.GROUPID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				capital = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return capital;
	}

	/**
	 * 根据群id删除所有相关数据
	 * 
	 * @param groupId
	 *            群id
	 * @return 是否删除成功
	 */
	public static boolean removeAllUserWithGroupId(int groupId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;

		try {
			String sql = "delete from " + GroupUserTable.GROUPUSERTABLE
					+ " where " + GroupUserTable.GROUPID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			if (ps.executeUpdate() > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return result;
	}

	/**
	 * 根据群id得到所有用户的id
	 * 
	 * @param groupId
	 *            群id
	 * @return 相应群用户id集合
	 */
	public static List<Integer> getAllUserIdWithGroupId(int groupId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Integer> userIdList = new ArrayList<Integer>();

		try {
			String sql = "select " + GroupUserTable.USERID + " from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.GROUPID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				userIdList.add(userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return userIdList;
	}

}

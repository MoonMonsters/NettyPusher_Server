/**
 * 
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.csuft.chentao.pojo.req.RegisterReq;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 *
 *         2016年12月8日 下午9:39:50
 */
public class UserTableOperate {

	/**
	 * 插入注册信息
	 * 
	 * @param registerReq
	 *            注册用户信息
	 * @return 是否注册成功
	 */
	@SuppressWarnings("resource")
	public static synchronized RegisterResp insert(RegisterReq req) {
		RegisterResp resp = new RegisterResp();

		Connection connection = DaoConnection.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			/*
			 * 判断用户名是否存在
			 */
			ps = connection.prepareStatement("select " + UserTable.USERNAME
					+ " from " + UserTable.USERTABLE + " where username = ?");
			ps.setString(1, req.getUsername());
			rs = ps.executeQuery();
			if (rs.next()) {
				Logger.log("用户名已经存在");
				resp.setType(Constant.TYPE_REGISTER_REPEAT_USERNAME);
				resp.setDescription(req.getUsername() + "注册失败");
				return resp;
			}

			Logger.log("用户名不存在，可以注册");

			// 用户的id值，从数据库中取出最大值，然后+1
			int userid = Constant.DEFAULT_USERID;
			ps = connection.prepareStatement("select max(userid) from "
					+ UserTable.USERTABLE);
			rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				userid = id == 0 ? userid : id + 1;
			}

			// 保存头像
			OperationUtil.saveHeadImage(req.getHeadImage(), userid);

			ps = connection.prepareStatement("insert into "
					+ UserTable.USERTABLE_ALL_FIELD + " values(?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setString(2, req.getUsername());
			ps.setString(3, req.getPassword());
			ps.setString(4, req.getNickname());
			ps.setString(5, req.getSignature());

			// 注册成功
			if (!ps.execute()) {
				Logger.log("注册成功");
				resp.setType(Constant.TYPE_REGISTER_SUCCESS);
				resp.setDescription(req.getUsername() + "注册成功");
				resp.setUserid(userid);
			} else {
				Logger.log("注册失败");
				resp.setType(Constant.TYPE_REGISTER_REPEAT_USERNAME);
				resp.setDescription(req.getUsername() + "注册失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据用户id更新昵称
	 * 
	 * @param userid
	 *            用户id
	 * @param nickname
	 *            待更新的昵称
	 */
	public static synchronized ReturnInfoResp updateUserNickname(int userid,
			String nickname) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnInfoResp resp = new ReturnInfoResp();

		try {
			String sql = "update " + UserTable.USERTABLE + " set "
					+ UserTable.NICKNAME + " = ?" + " where "
					+ UserTable.USERID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, nickname);
			ps.setInt(2, userid);
			if (ps.executeUpdate() >= 1) {
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_SUCCESS);
				resp.setDescription("更新昵称成功");
			} else {
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_FAIL);
				resp.setDescription("更新昵称失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据用户id更新签名
	 * 
	 * @param userid
	 *            用户id
	 * @param signature
	 *            待更新签名
	 */
	public static synchronized ReturnInfoResp updateUserSignature(int userid,
			String signature) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnInfoResp resp = new ReturnInfoResp();

		try {
			ps = connection.prepareStatement("update " + UserTable.USERTABLE
					+ " set " + UserTable.SIGNATURE + "= ?" + " where "
					+ UserTable.USERID + "=?");
			ps.setString(1, signature);
			ps.setInt(2, userid);
			if (ps.executeUpdate() >= 1) {
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_SIGNATURE_SUCESS);
				resp.setDescription("更新签名成功");
			} else {
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_SIGNATURE_FAIL);
				resp.setDescription("更新签名失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据用户名和密码取得用户的id值
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 用户的id
	 */
	public static int selectUserId(String username, String password) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		int userid = -1;

		try {
			ps = connection.prepareStatement("select " + UserTable.USERID
					+ " from " + UserTable.USERTABLE + " where "
					+ UserTable.USERNAME + "=? and " + UserTable.PASSWORD
					+ "=?");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				userid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return userid;
	}

	/**
	 * 获得用户的所有数据
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @return 用户数据
	 */
	public static UserInfoResp selectUserInfo(String username, String password) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		UserInfoResp resp = new UserInfoResp();

		try {
			ps = connection
					.prepareStatement("select userid,nickname,signature from "
							+ UserTable.USERTABLE + " where "
							+ UserTable.USERNAME + "=? and "
							+ UserTable.PASSWORD + "=?");
			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				int userid = rs.getInt(1);
				String nickname = rs.getString(2);
				byte[] headImage = OperationUtil.getHeadImage(userid);
				String signature = rs.getString(3);
				resp.setNickname(nickname);
				resp.setHeadImage(headImage);
				resp.setSignature(signature);
				resp.setUserid(userid);
			} else {
				resp.setUserid(-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据用户id得到用户信息
	 * 
	 * @param userId
	 *            用户id
	 * @return 用户信息
	 */
	public static UserInfoResp selectUserInfoWithUserId(int userId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		UserInfoResp resp = new UserInfoResp();
		try {
			String sql = "select userid,nickname,signature from "
					+ UserTable.USERTABLE + " where " + UserTable.USERID
					+ " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				// 用户id
				resp.setUserid(rs.getInt(1));
				// 昵称
				resp.setNickname(rs.getString(2));
				// 签名
				resp.setSignature(rs.getString(3));
				// 头像
				resp.setHeadImage(OperationUtil.getHeadImage(resp.getUserid()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据用户id得到用户名
	 */
	public static String getUsernameWithUserId(int userId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String name = null;

		try {
			String sql = "select " + UserTable.USERNAME + " from "
					+ UserTable.USERTABLE + " where " + UserTable.USERID
					+ " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) { // 如果查询到数据
				name = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return name;
	}

	/**
	 * 判断用户名在数据表中是否存在
	 * 
	 * @param userId
	 *            用户id
	 * @return 是否存在
	 */
	public static boolean isExitUserWithUserId(int userId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;

		try {
			String sql = "select "+UserTable.USERNAME+" from " + UserTable.USERTABLE
					+ " where " + UserTable.USERID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				Logger.log("isExitUserWithUserId-->" + username);
				if (username != null) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return result;
	}
}

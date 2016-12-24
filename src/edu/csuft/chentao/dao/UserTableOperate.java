/**
 * 
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.csuft.chentao.pojo.req.HeadImage;
import edu.csuft.chentao.pojo.req.RegisterReq;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
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
	public static synchronized RegisterResp insert(RegisterReq registerReq) {
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
			ps.setString(1, registerReq.getUsername());
			rs = ps.executeQuery();
			if (rs.next()) {
				Logger.log("用户名已经存在");
				resp.setType(Constant.REGISTER_TYPE_REPEAT_USERNAME);
				resp.setDescription(registerReq.getUsername() + "注册失败");
				return resp;
			}

			// 用户的id值，从数据库中取出最大值，然后+1
			int userid = Constant.DEFAULT_USERID;
			ps = connection.prepareStatement("select max(userid) from "
					+ UserTable.USERTABLE);
			rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				userid = id == 0 ? userid : id + 1;
			}

			String filename = registerReq.getHeadImage().getFilename();
			// 重新拼凑名字，用用户的id值作为用户头像图片名称
			filename = userid + filename.substring(filename.lastIndexOf("."));
			registerReq.getHeadImage().setFilename(filename);

			// 在修改完图片名称后，保存文件
			OperationUtil.saveHeadImage(registerReq.getHeadImage());

			ps = connection.prepareStatement("insert into "
					+ UserTable.USERTABLE_ALL_FIELD + " values(?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setString(2, registerReq.getUsername());
			ps.setString(3, registerReq.getPassword());
			ps.setString(4, registerReq.getNickname());
			ps.setString(5, registerReq.getHeadImage().getFilename());
			ps.setString(6, registerReq.getSignature());

			// 注册成功
			if (ps.execute()) {
				resp.setType(Constant.REGISTER_TYPE_SUCCESS);
				resp.setDescription(registerReq.getUsername() + "注册成功");
				resp.setUserid(userid);
			} else {
				resp.setType(Constant.REGISTER_TYPE_REPEAT_USERNAME);
				resp.setDescription(registerReq.getUsername() + "注册失败");
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
	public static synchronized ReturnMessageResp updateUserNickname(int userid,
			String nickname) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnMessageResp resp = new ReturnMessageResp();

		try {
			String sql = "update " + UserTable.USERTABLE + " set "
					+ UserTable.NICKNAME + " = ?" + " where "
					+ UserTable.USERID + " = ?";
			Logger.log(sql);
			ps = connection.prepareStatement(sql);
			ps.setString(1, nickname);
			ps.setInt(2, userid);
			if (ps.executeUpdate() >= 1) {
				resp.setType(Constant.TYPE_RETURN_MESSAGE_SUCCESS);
				resp.setDescription("更新昵称成功");
			} else {
				resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
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
	public static synchronized ReturnMessageResp updateUserSignature(
			int userid, String signature) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnMessageResp resp = new ReturnMessageResp();

		try {
			ps = connection.prepareStatement("update " + UserTable.USERTABLE
					+ " set " + UserTable.SIGNATURE + "= ?" + " where "
					+ UserTable.USERID + "=?");
			ps.setString(1, signature);
			ps.setInt(2, userid);
			if (ps.executeUpdate() >= 1) {
				resp.setType(Constant.TYPE_RETURN_MESSAGE_SUCCESS);
				resp.setDescription("更新签名成功");
			} else {
				resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
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
	 * @param username 用户名
	 * @param password 用户密码
	 * @return 用户数据
	 */
	public static UserInfoResp selectUserInfo(String username, String password) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		UserInfoResp resp = new UserInfoResp();
		
		try {
			ps = connection.prepareStatement("select userid,nickname,headimage,signature from "
					+ UserTable.USERTABLE + " where " + UserTable.USERNAME
					+ "=? and " + UserTable.PASSWORD + "=?");
			ps.setString(1, username);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			if(rs.next()){
				int userid = rs.getInt(1);
				String nickname = rs.getString(2);
				HeadImage headImage = OperationUtil.getHeadImage(rs.getString(3));
				String signature = rs.getString(4);
				resp.setNickname(nickname);
				resp.setHeadImage(headImage);
				resp.setSignature(signature);
				resp.setUserid(userid);
			}else{
				resp.setUserid(-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			OperationUtil.closeDataConnection(ps, rs);
		}
		
		return resp;
	}

}

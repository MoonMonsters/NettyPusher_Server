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
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
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
	 */
	public static synchronized ReturnMessageResp insert(GroupOperationReq req) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnMessageResp resp = new ReturnMessageResp();

		try {
			if (isExit(connection, req.getGroupid(), req.getUserid())) { // 加入群失败
				Logger.log("该用户已在群中");
				resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
				resp.setDescription("加入群失败");
				return resp;
			}

			// 插入数据
			ps = connection.prepareCall("insert into "
					+ GroupUserTable.GROUPUSERTABLE_ALL_FIELD + " values(?,?)");
			ps.setInt(1, req.getGroupid());
			ps.setInt(2, req.getUserid());
			if (ps.execute()) { // 执行成功
				resp.setType(Constant.TYPE_RETURN_MESSAGE_SUCCESS);
				resp.setDescription("加入群成功");
			} else { // 执行失败
				resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
				resp.setDescription("加入群失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;

	}

	/**
	 * 移除数据
	 */
	public static synchronized ReturnMessageResp remove(GroupOperationReq req) {

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		ReturnMessageResp resp = new ReturnMessageResp();

		try {

			if (isExit(connection, req.getGroupid(), req.getUserid())) {
				ps = connection.prepareStatement("delete from "
						+ GroupUserTable.GROUPUSERTABLE + " where "
						+ GroupUserTable.GROUPID + "=? and "
						+ GroupUserTable.USERID + "=?");
				ps.setInt(1, req.getGroupid());
				ps.setInt(2, req.getUserid());
				if (ps.execute()) {
					resp.setType(Constant.TYPE_RETURN_MESSAGE_SUCCESS);
					resp.setDescription("退出群成功");
				} else {
					resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
					resp.setDescription("退出群失败");
				}
			} else {
				resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
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
	public static boolean isExit(Connection connection, int groupid, int userid) {
		boolean isExit = false;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("select * from "
					+ GroupUserTable.GROUPUSERTABLE + " where "
					+ GroupUserTable.GROUPID + "=? and "
					+ GroupUserTable.USERID + "=?");
			ps.setInt(1, groupid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) { // 加入群失败
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
	 * 根据群id得到该群中所有的成员
	 * @param groupid 群id
	 * @return 成员id列表
	 */
	public static List<Integer> selectUserIdsWithGroupId(int groupid) {
		List<Integer> list = new ArrayList<Integer>();
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select userid from " + GroupUserTable.GROUPUSERTABLE
					+ " where " + GroupUserTable.GROUPID + "=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return list;
	}

	/**
	 * 根據用戶id得到所有的群的id
	 * @param userId 用戶id
	 * @return 該用戶所在群的id集合
	 */
	public static List<Integer> selectGroupIdsWithUserId(int userId){
		List<Integer> groupIdList = new ArrayList<Integer>();
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			String sql = "select " + GroupUserTable.GROUPID+" from " +
					GroupUserTable.GROUPUSERTABLE + " where "+GroupUserTable.USERID + " = ? ";
			ps = connection.prepareStatement(sql);
			//设置用户id
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while(rs.next()){
				groupIdList.add(rs.getInt(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			OperationUtil.closeDataConnection(ps, rs);
		}
		
		return groupIdList;
	}
	
}

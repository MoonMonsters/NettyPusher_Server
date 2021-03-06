/**
 * 群chatgroup相关操作
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.pojo.req.CreateGroupReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 *
 *         2016年12月8日 下午9:39:20
 */
public class GroupTableOperate {

	/**
	 * 插入需要创建的群信息
	 * 
	 * @param req
	 *            保存群信息的对象
	 * @return 返回群id
	 */
	@SuppressWarnings("resource")
	public static synchronized int insert(CreateGroupReq req) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnGroupId = -1;

		try {
			// 得到当前最大的群id
			ps = connection.prepareStatement("select max("
					+ GroupTable.GROUP_ID + ") from " + GroupTable.TABLE_NAME);
			rs = ps.executeQuery();
			// 设置群id
			int groupid = Constant.DEFAULT_GROUPID;
			// 取得或者重新开始群id
			if (rs.next()) {
				int id = rs.getInt(1);
				groupid = id == 0 ? groupid : id + 1;
			}

			// 保存群头像
			OperationUtil.saveHeadImage(req.getHeadImage(), groupid);

			// 设置群属性
			ps = connection.prepareStatement("insert into "
					+ GroupTable.TABLE_ALL_FIELD + "values(?,?,?,?)");
			ps.setInt(1, groupid);
			ps.setString(2, req.getGroupname());
			ps.setString(3, req.getTag());
			ps.setInt(4, 0);

			rs = null;
			int count = ps.executeUpdate();

			// 是否执行成功
			if (count > 0) {
				returnGroupId = groupid;
				// 将身份标识和用户id存入表中
				GroupUserTableOperate.insert(groupid, req.getCreatorId(),
						Constant.TYPE_GROUP_CAPITAL_OWNER);
			} else {
				System.out.println("GroupTableOperation-->执行失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return returnGroupId;
	}

	/**
	 * 根据群id，得到所有群的信息
	 * 
	 * @param groupIdList
	 *            群id集合
	 * @return 群信息集合
	 */
	public static List<GroupInfoResp> selectAllGroupInfosWithGroupIds(
			List<Integer> groupIdList) {
		List<GroupInfoResp> respList = new ArrayList<GroupInfoResp>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupTable.GROUP_ID + ","
					+ GroupTable.GROUP_NAME + "," + GroupTable.TAG + ","
					+ GroupTable.NUMBER + " from " + GroupTable.TABLE_NAME
					+ " where " + GroupTable.GROUP_ID + " = ?";
			ps = connection.prepareStatement(sql);
			for (int id : groupIdList) {
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					// 群id
					int groupId = rs.getInt(1);
					// 群名称
					String groupName = rs.getString(2);
					// 群标签
					String tag = rs.getString(3);
					// 群里人数
					int number = rs.getInt(4);

					// 从文件中把头像读取出来
					byte[] buf = OperationUtil.getHeadImage(groupId);

					GroupInfoResp resp = new GroupInfoResp();
					resp.setGroupid(groupId);
					resp.setGroupname(groupName);
					resp.setHeadImage(buf);
					resp.setNumber(number);
					resp.setTag(tag);

					respList.add(resp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return respList;
	}

	/**
	 * 根据群id获取群数据
	 * 
	 * @param groupId
	 *            群id
	 * @return GroupInfoResp对象
	 */
	public static GroupInfoResp getGroupInfoWithId(int groupId) {
		GroupInfoResp resp = null;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupTable.GROUP_ID + ","
					+ GroupTable.GROUP_NAME + "," + GroupTable.TAG + ","
					+ GroupTable.NUMBER + " from " + GroupTable.TABLE_NAME
					+ " where " + GroupTable.GROUP_ID + " = ? and "
					+ GroupTable.NUMBER + " > 0";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				resp = new GroupInfoResp();
				// 群id
				int id = rs.getInt(1);
				// 群名称
				String groupName = rs.getString(2);
				// 群标签
				String tag = rs.getString(3);
				// 群里人数
				int number = rs.getInt(4);

				// 从文件中把头像读取出来
				byte[] buf = OperationUtil.getHeadImage(id);

				resp.setGroupid(groupId);
				resp.setGroupname(groupName);
				resp.setHeadImage(buf);
				resp.setNumber(number);
				resp.setTag(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据群id获得数据，在该方法中，即使人数为0，也可以取出数据
	 */
	public static GroupInfoResp getGroupInfoWithId2(int groupId) {
		GroupInfoResp resp = null;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupTable.GROUP_ID + ","
					+ GroupTable.GROUP_NAME + "," + GroupTable.TAG + ","
					+ GroupTable.NUMBER + " from " + GroupTable.TABLE_NAME
					+ " where " + GroupTable.GROUP_ID + " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				resp = new GroupInfoResp();
				// 群id
				int id = rs.getInt(1);
				// 群名称
				String groupName = rs.getString(2);
				// 群标签
				String tag = rs.getString(3);
				// 群里人数
				int number = rs.getInt(4);

				// 从文件中把头像读取出来
				byte[] buf = OperationUtil.getHeadImage(id);

				resp.setGroupid(groupId);
				resp.setGroupname(groupName);
				resp.setHeadImage(buf);
				resp.setNumber(number);
				resp.setTag(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据群名获得数据集合
	 * 
	 * @param groupName
	 *            群名关键字
	 * @return 群数据集合
	 */
	public static List<GroupInfoResp> getGroupInfoListWithGroupName(
			String groupName) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<GroupInfoResp> list = new ArrayList<GroupInfoResp>();

		try {
			String sql = "select " + GroupTable.GROUP_ID + ","
					+ GroupTable.GROUP_NAME + "," + GroupTable.TAG + ","
					+ GroupTable.NUMBER + " from " + GroupTable.TABLE_NAME
					+ " where " + GroupTable.GROUP_NAME + " like '%"
					+ groupName + "%' and " + GroupTable.NUMBER
					+ " > 0 order by rand() limit 0,9";
			ps = connection.prepareStatement(sql);
			// ps.setString(1, groupName);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 群id
				int id = rs.getInt(1);
				// 群名称
				String name = rs.getString(2);
				// 群标签
				String tag = rs.getString(3);
				// 群里人数
				int number = rs.getInt(4);

				// 从文件中把头像读取出来
				byte[] buf = OperationUtil.getHeadImage(id);

				GroupInfoResp resp = new GroupInfoResp();
				resp.setGroupid(id);
				resp.setGroupname(name);
				resp.setHeadImage(buf);
				resp.setNumber(number);
				resp.setTag(tag);
				list.add(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return list;
	}

	/**
	 * 根据群标签得到群数据集合
	 * 
	 * @param tag
	 *            群标签
	 * @return 群数据集合
	 */
	public static List<GroupInfoResp> getGroupInfoListWithGroupTag(String tag) {
		List<GroupInfoResp> list = new ArrayList<GroupInfoResp>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupTable.GROUP_ID + ","
					+ GroupTable.GROUP_NAME + "," + GroupTable.TAG + ","
					+ GroupTable.NUMBER + " from " + GroupTable.TABLE_NAME
					+ " where " + GroupTable.TAG + " like '%" + tag + "%' and "
					+ GroupTable.NUMBER + " > 0 order by rand() limit 0,9";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 群id
				int id = rs.getInt(1);
				// 群名称
				String name = rs.getString(2);
				// 群标签
				String tag2 = rs.getString(3);
				// 群里人数
				int number = rs.getInt(4);

				// 从文件中把头像读取出来
				byte[] buf = OperationUtil.getHeadImage(id);

				GroupInfoResp resp = new GroupInfoResp();
				resp.setGroupid(id);
				resp.setGroupname(name);
				resp.setHeadImage(buf);
				resp.setNumber(number);
				resp.setTag(tag2);
				list.add(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 判断群是否存在
	 * 
	 * @param groupId
	 *            要查找的群
	 * @return 存在为true，不存在为false
	 */
	public static boolean isExitGroupWithGroupId(int groupId) {
		boolean result = false;

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select " + GroupTable.GROUP_NAME + " from "
					+ GroupTable.TABLE_NAME + " where " + GroupTable.GROUP_ID
					+ " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String groupName = rs.getString(1);
				if (groupName != null) {
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

	/**
	 * 根据群id解散该群（删除数据）
	 * 
	 * @param groupId
	 *            群id
	 * @return 是否删除成功
	 */
	public static boolean removeGroupDataWithGroupId(int groupId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;

		try {
			String sql = "delete from " + GroupTable.TABLE_NAME + " where "
					+ GroupTable.GROUP_ID + " = ?";
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
	 * 根据群id获得群名称
	 * 
	 * @param groupId
	 *            群id
	 * @return 群名称
	 */
	public static String getGroupNameWithGroupId(int groupId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String groupName = null;

		try {
			String sql = "select " + GroupTable.GROUP_NAME + " from "
					+ GroupTable.TABLE_NAME + " where " + GroupTable.GROUP_ID
					+ " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				groupName = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return groupName;
	}

	/**
	 * 根据群id得到群人数
	 * 
	 * @param groupId
	 *            群id
	 * @return 群中用户人数
	 */
	public static int getGroupUserNumberByGroupId(int groupId) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		int number = -1;

		try {
			String sql = "select " + GroupTable.NUMBER + " from "
					+ GroupTable.TABLE_NAME + " where " + GroupTable.GROUP_ID
					+ " = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				// 得到数据
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return number;
	}

}

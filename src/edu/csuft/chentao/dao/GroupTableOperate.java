/**
 * 群chatgroup相关操作
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.pojo.req.CreateGrourpReq;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 *
 *         2016年12月8日 下午9:39:20
 */
public class GroupTableOperate {

	@SuppressWarnings("resource")
	public static synchronized CreateGroupResp insert(CreateGrourpReq req) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		CreateGroupResp resp = new CreateGroupResp();

		try {
			// 得到当前最大的群id
			ps = connection.prepareStatement("select max(groupid) from "
					+ GroupTable.GROUPTABLE);
			rs = ps.executeQuery();
			// 设置群id
			int groupid = Constant.DEFAULT_GROUPID;
			// 取得或者重新开始群id
			if (rs.next()) {
				int id = rs.getInt(1);
				groupid = id == 0 ? groupid : id + 1;
			}

			// 保存群头像
			OperationUtil.saveHeadImage(req.getHeadImage(),
					groupid);

			// 设置群属性
			ps = connection.prepareStatement("insert into "
					+ GroupTable.GROUPTABLE_ALL_FIELD + "values(?,?,?,?)");
			ps.setInt(1, groupid);
			ps.setString(2, req.getGroupname());
			ps.setString(3, req.getTag());
			ps.setInt(4, 0);

			// 是否执行成功
			if (ps.execute()) {
				resp.setType(Constant.CREATE_GROUP_SUCCESS);
				resp.setDescription(req.getGroupname() + "创建成功");
				resp.setGroupid(groupid);
			} else {
				resp.setType(Constant.CREATE_GROUP_FAIL);
				resp.setDescription(req.getGroupname() + "创建失败，稍后再试");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return resp;
	}

	/**
	 * 根据群id，得到所有群的信息
	 * @param groupIdList 群id集合
	 * @return 群信息集合
	 */
	public static List<GroupInfoResp> selectAllGroupInfosWithGroupIds(
			List<Integer> groupIdList) {
		List<GroupInfoResp> respList = new ArrayList<GroupInfoResp>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select groupid,groupname,tag,number from "
					+ GroupTable.GROUPTABLE + " where " + GroupTable.GROUPID
					+ " = ?";
			ps = connection.prepareStatement(sql);
			for (int id : groupIdList) {
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					//群id
					int groupId = rs.getInt(1);
					//群名称
					String groupName = rs.getString(2);
					//群标签
					String tag = rs.getString(3);
					//群里人数
					int number = rs.getInt(4);

					//从文件中把头像读取出来
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

}

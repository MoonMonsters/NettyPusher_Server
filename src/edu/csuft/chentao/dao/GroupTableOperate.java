/**
 * 群chatgroup相关操作
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.csuft.chentao.pojo.req.CreateGrourpReq;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
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
			//取得或者重新开始群id
			if (rs.next()) {
				int id = rs.getInt(1);
				groupid = id == 0 ? groupid : id + 1;
			}

			// 修改群头像名字
			String filename = req.getHeadImage().getFilename();
			filename = groupid + filename.substring(filename.lastIndexOf("."));
			req.getHeadImage().setFilename(filename);
			// 保存群头像
			OperationUtil.saveHeadImage(req.getHeadImage());

			//设置群属性
			ps = connection.prepareStatement("insert into "
					+ GroupTable.GROUPTABLE_ALL_FIELD + "values(?,?,?,?,?)");
			ps.setInt(1, groupid);
			ps.setString(2, req.getGroupname());
			ps.setString(3, req.getTag());
			ps.setInt(4, 0);
			ps.setString(5, filename);

			//是否执行成功
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
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resp;
	}

}

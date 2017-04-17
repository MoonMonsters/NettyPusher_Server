/**
 * 处理创建群操作
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.pojo.req.CreateGroupReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:03:06
 */
public class CreateGroupHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {
		Logger.log("CreateGroupHandler-->创建群操作");
		CreateGroupReq req = (CreateGroupReq) object;
		// 执行插入操作，并且返回结果
		int groupId = GroupTableOperate.insert(req);

		Logger.log("创建群是否成功-->" + (groupId == -1 ? false : true));

		/**
		 * 返回创建的群
		 */
		if (groupId != -1) {
			OperationUtil.sendReturnInfoResp(chc,
					Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS,
					req.getGroupname() + "创建成功");

			GroupInfoResp resp2 = new GroupInfoResp();
			resp2.setGroupid(groupId);
			resp2.setGroupname(req.getGroupname());
			resp2.setHeadImage(req.getHeadImage());
			resp2.setNumber(1);
			resp2.setTag(req.getTag());
			resp2.setType(Constant.TYPE_GROUP_INFO_OWNER);
			chc.writeAndFlush(resp2);
		} else {
			OperationUtil.sendReturnInfoResp(chc,
					Constant.TYPE_RETURN_INFO_CREATE_GROUP_FAIL,
					req.getGroupname() + "创建失败，请稍后再试");
		}

		Logger.log("CreateGroupHandler-->返回创建群的群信息");
	}
}

/**
 * 
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.pojo.req.GetUserAndGroupInfoReq;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * new1.从客户端发送该指令，获得用户信息或者群中成员信息
 * 
 * @author csuft.chentao
 *
 *         2017年1月18日 下午3:21:19
 */
public class GetUserAndGroupInfoHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {
		GetUserAndGroupInfoReq req = (GetUserAndGroupInfoReq) object;
		if (req.getType() == Constant.TYPE_USER_GROUP_INFO_USER) { // 请求用户信息
			int userId = req.getId();
			UserInfoResp resp = UserTableOperate
					.selectUserInfoWithUserId(userId);
			resp.setType(Constant.TYPE_LOGIN_USER_INFO);
			chc.writeAndFlush(resp);
			Logger.log("GetUserAndGroupInfoHandler->返回请求的用户信息");
		} else if (req.getType() == Constant.TYPE_USER_GROUP_INFO_GROUP) {
			int groupId = req.getId();
			UserIdsInGroupResp resp = new UserIdsInGroupResp();
			resp.setGroupId(groupId);
			resp.setUserIdCapitalList(GroupUserTableOperate
					.selectIdAndCapitalInUserWithGroupId(groupId));
			Logger.log("GetUserAndGroupInfoHandler->groupId="+resp.getGroupId());
			chc.writeAndFlush(resp);
			Logger.log("GetUserAndGroupInfoHandler->返回用户请求的群信息");
		}
	}
}

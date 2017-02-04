/**
 * 
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2017年2月1日 下午9:59:51
 */
public class GetInfoHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {
		GetInfoReq req = (GetInfoReq) object;

		Logger.log("用户请求数据GetInfoReq->" + req.toString());

		if (req.getType() == Constant.TYPE_GET_INFO_USERINFO) { // 请求用户信息
			// 得到用户id
			int userId = req.getArg1();
			Logger.log("用户请求的userId->" + userId);
			// 根据用户id得到用户信息
			UserInfoResp resp = UserTableOperate
					.selectUserInfoWithUserId(userId);
			resp.setType(Constant.TYPE_LOGIN_USER_INFO);
			Logger.log("返回请求的UserInfo数据-->" + resp.toString());
			// 返回
			chc.writeAndFlush(resp);
		} else if (req.getType() == Constant.TYPE_GET_INFO_UNLOGIN) { // 退出登录
			int userId = req.getArg1();
			NettyCollections.removeWithUserId(userId); // 移除
		} else if (req.getType() == Constant.TYPE_GET_INFO_SEARCH_GROUP_ID) { // 根据群id搜索

		} else if (req.getType() == Constant.TYPE_GET_INFO_SEARCH_GROUP_NAME) { // 根据群名搜索

		} else if (req.getType() == Constant.TYPE_GET_INFO_SEARCH_GROUP_TAG) { // 根据群标签搜索

		}
	}

}

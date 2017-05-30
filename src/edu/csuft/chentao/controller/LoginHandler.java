/**
 * 处理登录信息
 */
package edu.csuft.chentao.controller;

import java.util.List;

import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:22:25
 */
public class LoginHandler implements Handler {

	public void handle(final ChannelHandlerContext chc, Object object) {

		LoginReq req = (LoginReq) object;

		//如果有同时有两个相同的账号登录，则顶掉前面一个账号
		int userId = UserTableOperate.selectUserId(req.getUsername(), req.getPassword());
		if(NettyCollections.getChannelHandlerContextByUserId(userId) != null){
			Logger.log("重复登录，移除掉前面一个客户端");
			//得到前一个用户登录的连接对象
			ReturnInfoResp resp = new ReturnInfoResp();
			resp.setType(Constant.TYPE_RETURN_INFO_CLIENT_EXIT);
			ChannelHandlerContext ctx = NettyCollections.getChannelHandlerContextByUserId(userId);
			//发送消息
			ctx.writeAndFlush(resp);
			
			//从服务端将客户端连接对象移除掉
			NettyCollections.removeWithUserId(userId);
		
		}
		
		// 返回消息
		UserInfoResp resp = new UserInfoResp();

		/*
		 * 自动登录与新的登录相比，新的登录会携带完整的用户数据回去
		 */
		// 自动登录
		if (req.getType() == Constant.TYPE_LOGIN_AUTO) {
			resp.setType(Constant.TYPE_LOGIN_AUTO);
			resp.setUserid(UserTableOperate.selectUserId(req.getUsername(),
					req.getPassword()));
			// 新的登录
		} else if (req.getType() == Constant.TYPE_LOGIN_NEW) {
			resp = UserTableOperate.selectUserInfo(req.getUsername(),
					req.getPassword());
			resp.setType(Constant.TYPE_LOGIN_NEW);
		}

		if (resp.getUserid() >= Constant.DEFAULT_USERID) { // 如果登录成功，即返回的数据中，用户id是存在的

			// 在登录时，把该ChannelHandlerContext对象加入集合中
			NettyCollections.add(resp.getUserid(), chc);

			// 如果登录成功，并且是新的登录
			if (req.getType() == Constant.TYPE_LOGIN_NEW) {
				// 得到该用户参与的所有群信息
				// 该用户所在群的id
				List<Integer> groupIdList = GroupUserTableOperate
						.selectGroupIdsWithUserId(resp.getUserid());
				// 该用户所在群的所有群信息
				final List<GroupInfoResp> groupInfoList = GroupTableOperate
						.selectAllGroupInfosWithGroupIds(groupIdList);

				// 在子线程中发送所有数据
				new Thread(new Runnable() {
					public void run() {
						// 把所有群信息发送到客户端
						for (GroupInfoResp gir : groupInfoList) {
							gir.setType(Constant.TYPE_GROUP_INFO_OWNER);
							OperationUtil.sleepFor500();
							chc.writeAndFlush(gir);
						}

					}
				}).start();
			}
		}

		Logger.log("LoginHandler-->username = "+resp.getUsername() + ",userId = " + resp.getUserid());
		chc.writeAndFlush(resp);
	}
}

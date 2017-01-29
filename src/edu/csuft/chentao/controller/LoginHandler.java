/**
 * 处理登录信息
 */
package edu.csuft.chentao.controller;

import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:22:25
 */
public class LoginHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {

		Logger.log("LoginHandler-->登录操作");

		LoginReq req = (LoginReq) object;

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
				List<GroupInfoResp> groupInfoList = GroupTableOperate
						.selectAllGroupInfosWithGroupIds(groupIdList);

				/**
				 * 得到所有用户信息
				 */
				List<Integer> userIdList = new ArrayList<Integer>();
				List<UserInfoResp> userInfoList = new ArrayList<>();
				for (int groupId : groupIdList) {
					userIdList.addAll(GroupUserTableOperate
							.selectAllUserIdsWithGroupId(groupId));
				}
				for (int userId : userIdList) {
					userInfoList.add(UserTableOperate
							.selectUserInfoWithUserId(userId));
				}

				// 在子线程中发送所有数据
				new Thread(new Runnable() {
					@Override
					public void run() {
						// 把所有群信息发送到客户端
						for (GroupInfoResp gir : groupInfoList) {
							chc.writeAndFlush(gir);
						}

						/**
						 * 发送所有用户数据
						 */
						for (UserInfoResp resp : userInfoList) {
							chc.writeAndFlush(resp);
						}
					}
				}).start();
			}
		}

		Logger.log("UserInfoResp-->返回用户信息");

		chc.writeAndFlush(resp);
	}
}

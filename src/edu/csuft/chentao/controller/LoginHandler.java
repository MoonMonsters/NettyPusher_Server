/**
 * 处理登录信息
 */
package edu.csuft.chentao.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

		if (resp.getUserid() >= Constant.DEFAULT_USERID) {
			
			// 在登录时，把该ChannelHandlerContext对象加入集合中
			NettyCollections.add(resp.getUserid(), chc);
			
			//如果登录成功，并且是新的登录
			if(req.getType() == Constant.TYPE_LOGIN_NEW){
				//1.得到该用户参与的所有群信息
				//该用户所在群的id
				List<Integer> groupIdList = GroupUserTableOperate.selectGroupIdsWithUserId(resp.getUserid());
				//该用户所在群的所有群信息
				List<GroupInfoResp> groupInfoList = GroupTableOperate.selectAllGroupInfosWithGroupIds(groupIdList);
				
				//2.从群中把所有的用户信息发送过去
				Set<Integer> userIdSet = new HashSet<Integer>();
				//根据群id得到所有的用户id
				for(int groupId : groupIdList){
					userIdSet.addAll(GroupUserTableOperate.selectUserIdsWithGroupId(groupId));
				}
				List<UserInfoResp> userInfoList = new ArrayList<UserInfoResp>();
				Iterator<Integer> it = userIdSet.iterator();
				//根据用户id得到用户信息
				while(it.hasNext()){
					int userId = it.next();
					UserInfoResp userInfo = UserTableOperate.selectUserInfoWithUserId(userId);
					//设置信息类型
					userInfo.setType(Constant.TYPE_LOGIN_USER_INFO);
					userInfoList.add(userInfo);
				}
				
				//在子线程中发送所有数据
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						//把所有群信息发送到客户端
						for(GroupInfoResp gir : groupInfoList){
							chc.writeAndFlush(gir);
						}
						//把所有的用户信息发送到客户端
						for(UserInfoResp resp : userInfoList){
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

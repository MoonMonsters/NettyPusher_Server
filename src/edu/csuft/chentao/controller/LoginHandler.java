/**
 * 处理登录信息
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:22:25
 */
public class LoginHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {
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
		}

		if (resp.getUserid() >= Constant.DEFAULT_USERID) {
			// 在登录时，把该ChannelHandlerContext对象加入集合中
			NettyCollections.add(resp.getUserid(), chc);
		}

		chc.writeAndFlush(resp);
	}
}

/**
 * 处理注册信息
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.pojo.req.RegisterReq;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:24:30
 */
public class RegisterHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {
		
		Logger.log("RegisterHandler-->注册");
		
		RegisterReq registerReq = (RegisterReq) object;
		//注册
		RegisterResp resp = UserTableOperate.insert(registerReq);
		
		Logger.log("RegisterResp-->返回注册后的用户信息");
		
		//返回注册后的信息
		chc.writeAndFlush(resp);
	}
}

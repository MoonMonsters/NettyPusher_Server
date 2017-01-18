/**
 * 处理创建群操作
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.pojo.req.CreateGrourpReq;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:03:06
 */
public class CreateGroupHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {
		Logger.log("CreateGroupHandler-->创建群操作");
		CreateGrourpReq req = (CreateGrourpReq) object;
		// 执行插入操作，并且返回结果
		CreateGroupResp resp = GroupTableOperate.insert(req);

		Logger.log("CreateGroupResp-->返回创建群的群信息");

		chc.writeAndFlush(resp);
	}
}

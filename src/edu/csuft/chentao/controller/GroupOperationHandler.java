/**
 * 处理用户对群的操作，例如添加或退出等
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:14:10
 */
public class GroupOperationHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {

		Logger.log("GroupOperationHandler-->加入或者退出群操作");

		GroupOperationReq req = (GroupOperationReq) object;
		ReturnMessageResp resp = null;
		if (req.getType() == Constant.TYPE_GROUP_ENTER) { // 加群
			resp = GroupUserTableOperate.insert(req.getGroupid(),
					req.getUserid(), Constant.TYPE_GROUP_CAPITAL_USER);
		} else if (req.getType() == Constant.TYPE_GROUP_EXIT) { // 退群
			resp = GroupUserTableOperate.remove(req);
		}

		Logger.log("ReturnMessage-->返回是否加入/退出群成功操作");

		chc.writeAndFlush(resp);
	}

}

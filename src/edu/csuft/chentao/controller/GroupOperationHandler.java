/**
 * 处理用户对群的操作，例如添加或退出等
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.util.Constant;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 * 2016年12月9日 下午9:14:10
 */
public class GroupOperationHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {
		GroupOperationReq req = (GroupOperationReq)object;
		ReturnMessageResp resp = null;
		if(req.getType() == Constant.TYPE_GROUP_ENTER){	//加群
			resp = GroupUserTableOperate.insert(req);
		}else if(req.getType() == Constant.TYPE_GROUP_EXIT){ //退群
			resp = GroupUserTableOperate.remove(req);
		}
		chc.writeAndFlush(resp);
	}

}

/**
 * 处理聊天信息
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:23:31
 */
public class MessageHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {

		Logger.log("MessageHandler-->发送群消息操作");

		Message message = (Message) object;

		// 将类型改变，发过来的时候，是发送类型，需要改成接收类型
		message.setType(Constant.TYPE_MSG_RECV);

		/*
		 * 消息发送成功，返回对象告知客户端
		 */
		ReturnInfoResp resp = new ReturnInfoResp();
		resp.setType(Constant.TYPE_RETURN_INFO_SEND_MESSAGE_SUCCESS);
		resp.setObj(message.getSerial_number());

		chc.writeAndFlush(resp);

		// 遍历所有需要发送的对象
		NettyCollections.traverse(message.getGroupid(), message);
	}

}

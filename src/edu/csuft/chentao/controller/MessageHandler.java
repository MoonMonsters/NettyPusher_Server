/**
 * 处理聊天信息
 */
package edu.csuft.chentao.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

		//消息时间设置为到达服务端时间，而不再由客户端产生
		String currentTime = getCurrentTime();
		
		Message message = (Message) object;

		// 将类型改变，发过来的时候，是发送类型，需要改成接收类型
		message.setType(Constant.TYPE_MSG_RECV);
		//重新设置时间
		message.setTime(currentTime);
		
		/*
		 * 消息发送成功，返回对象告知客户端
		 */
		ReturnInfoResp resp = new ReturnInfoResp();
		resp.setType(Constant.TYPE_RETURN_INFO_SEND_MESSAGE_SUCCESS);
		//将到达服务端的时间返回给客户端
		resp.setObj(currentTime);
		resp.setArg1(message.getSerial_number());

		chc.writeAndFlush(resp);

		// 遍历所有需要发送的对象
		NettyCollections.traverse(message.getGroupid(), message);
	}
	
	/**
	 * 得到当前服务端时间
	 */
	private String getCurrentTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = sdf.format(date);
		
		return time;
	}

}

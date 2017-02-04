/**
 * 处理聊天信息
 */
package edu.csuft.chentao.controller;

import java.util.List;

import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:23:31
 */
public class MessageHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {

		Logger.log("MessageHandler-->发送群消息操作");

		Message message = (Message) object;

		// 将类型改变，发过来的时候，是发送类型，需要改成接收类型
		message.setType(Constant.TYPE_MSG_RECV);

		// 获得一个群中所有用户的id
		List<Integer> useridList = GroupUserTableOperate
				.selectAllUserIdsWithGroupId(message.getGroupid());
		
		// 因为是发送给其他人，所以需要移除掉自己本身的对象
		int index = useridList.indexOf(message.getUserid());
		if (index > -1) {
			useridList.remove(index);
		}

//		message.setType(Constant.TYPE_MSG_RECV);
		chc.writeAndFlush(message);
		
		// 遍历所有需要发送的对象
		NettyCollections.traverse(useridList, message);
	}

}

package edu.csuft.chentao.netty;

import edu.csuft.chentao.controller.AllMessageHandler;
import edu.csuft.chentao.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 数据处理
 * 
 * @author cusft.chentao
 *
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void messageReceived(ChannelHandlerContext chc, Object object)
			throws Exception {
		Logger.log("ServerHandler-->messageReceived");
	}

	/** 客户端连接 */
	@Override
	public void channelActive(ChannelHandlerContext chc) throws Exception {
		Logger.log("ServerHandler-->channelActive");
	}

	/** 接收到客户端的消息，并进行相应处理 */
	@Override
	public void channelRead(ChannelHandlerContext chc, Object object)
			throws Exception {
		Logger.log("ServerHandler-->channelRead");
		AllMessageHandler.handleMessage(chc, object);
	}

	/** 客户端断开与服务端的连接 */
	@Override
	public void channelInactive(ChannelHandlerContext chc) throws Exception {
		//关闭
		chc.close();
		// 断开连接，从集合中移除掉
		NettyCollections.removeWithCHC(chc);
		Logger.log("ServerHandler-->channelInactive");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
		Logger.log("SererHandler-->exceptionCaught---->"+cause.getMessage());
	}
}

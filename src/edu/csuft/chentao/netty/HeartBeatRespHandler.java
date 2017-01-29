package edu.csuft.chentao.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatRespHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage message = (NettyMessage)msg;
		if (message.getHeader() != null && message.getHeader().getType() == 5) {
			System.out.println("server receive client heart message " + message);
			NettyMessage heartBeat = buildHeartBeat();
			ctx.writeAndFlush(heartBeat);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	public NettyMessage buildHeartBeat() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType((byte)6);  //心跳应答消息
		message.setHeader(header);
		return message;
	}
	
}
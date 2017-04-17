package edu.csuft.chentao.netty;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

public class HeartBeatReqHandler extends  ChannelHandlerAdapter{

	private volatile ScheduledFuture<?> heartBeat;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage message = (NettyMessage)msg;
		if (message.getHeader() != null && message.getHeader().getType() == 4) {
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		} else if (message.getHeader() != null && message.getHeader().getType() == 6) {
			System.out.println("client receive server heart message : " + message);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private class HeartBeatTask implements Runnable {
		
		private final ChannelHandlerContext ctx;
		
		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		public void run() {
			NettyMessage message = buildHeatBeat();
			System.out.println("client send heart message :銆�" + message);
			ctx.writeAndFlush(message);
		}
		
		private NettyMessage buildHeatBeat() {
			NettyMessage message = new NettyMessage();
			Header header = new Header();
			header.setType((byte)5); //蹇冭烦璇锋眰娑堟伅
			message.setHeader(header);
			return message;
		}
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if (heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}
	
}
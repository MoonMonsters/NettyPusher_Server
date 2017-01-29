package edu.csuft.chentao.netty;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class LoginAuthRespHandler extends ChannelHandlerAdapter{

	private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
	private String[] whitekList = {"127.0.0.1", "192.168.1.104"}; //白名单
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage message = (NettyMessage)msg;
		if (message.getHeader() != null && message.getHeader().getType() == 3) {
			String nodeIndex = ctx.channel().remoteAddress().toString();
			NettyMessage loginResult = null;
			if (nodeCheck.containsKey(nodeIndex)) {
				loginResult = buildRespon((byte)-1); //验证重复登录
			} else {
				InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
				String ip = address.getAddress().getHostAddress();
				boolean isOK = false;
				for (String WIP : whitekList) {
					if (WIP.equals(ip)) {
						isOK = true;
						break;
					}
				}
				loginResult = isOK ? buildRespon((byte)0) : buildRespon((byte)-1);
				if (isOK) {
					nodeCheck.put(nodeIndex, true);
				}
			}
			System.out.println("the login response is : " + loginResult);
			ctx.writeAndFlush(loginResult);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private NettyMessage buildRespon(byte result) {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType((byte)4); //握手应答消息
		message.setHeader(header);
		message.setBody(result);
		return message;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		nodeCheck.remove(ctx.channel().remoteAddress().toString()); //出现异常删除缓存
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}
}
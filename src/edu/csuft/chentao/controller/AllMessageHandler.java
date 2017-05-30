/**
 * 所有信息处理入口
 */
package edu.csuft.chentao.controller;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 * 2016年12月9日 下午8:48:12
 */
public class AllMessageHandler {

	public static void handleMessage(ChannelHandlerContext chc, Object object){
		
		//根据相应的object类型，得到对应的处理对象
		Handler handler = MessageHandlerFactory.getHandlerInstance(object);
		
		//处理操作
		handler.handle(chc, object);
	}
}

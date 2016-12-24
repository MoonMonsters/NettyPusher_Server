/**
 * 处理，所以处理类都必须实现该接口
 * 处理的对象是pojo包中带req的类
 */
package edu.csuft.chentao.controller;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午8:47:32
 */
public interface Handler {

	/**
	 * 处理具体命令
	 * 
	 * @param chc
	 *            交互
	 * @param object
	 *            命令对象
	 */
	void handle(ChannelHandlerContext chc, Object object);

}

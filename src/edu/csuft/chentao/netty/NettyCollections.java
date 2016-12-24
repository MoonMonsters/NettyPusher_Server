package edu.csuft.chentao.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理客户端连接
 * 
 * @author cusft.chentao
 *
 */
public class NettyCollections {

	/** 管道集合，用来与客户端进行沟通 */
	private static final Map<Integer, ChannelHandlerContext> chcMap = new HashMap<Integer, ChannelHandlerContext>();

	/**
	 * 将与服务端正在连接的客户端保存起来
	 * 
	 * @param username
	 *            用用户名挡住关键字
	 * @param chc
	 *            ChannelHandlerContext对象
	 */
	public static void add(Integer userid, ChannelHandlerContext chc) {
		chcMap.put(userid, chc);
	}

	/**
	 * 根据用户名移除
	 * 
	 * @param username
	 *            用户名
	 */
	public static void removeWithUserId(int userid) {
		chcMap.remove(userid);
	}

	/**
	 * 根据ChannelHandlerContext对象移除
	 * 
	 * @param chc
	 */
	public static void removeWithCHC(ChannelHandlerContext chc) {
		// Set<String> set = chcMap.keySet();
		// Iterator<String> it = set.iterator();
		// String username = null;
		// while(it.hasNext()){
		// username = it.next();
		// if(chcMap.get(username).equals(chc)){
		// break;
		// }
		// }
		// chcMap.remove(username);

		Collection<ChannelHandlerContext> collection = chcMap.values();
		if (collection.contains(chc)) {
			collection.remove(chc);
		}
	}

	/**
	 * 发送数据
	 * 
	 * @param usernameList
	 *            需要发送给的客户端
	 * @param object
	 *            发送对象
	 */
	public static void traverse(List<Integer> useridList, Object object) {
		if (useridList.size() > 0) {
			for (int userid : useridList) {
				ChannelHandlerContext chc = chcMap.get(userid);
				if(chc != null){
					chc.writeAndFlush(object);
				}
			}
		}
	}
}
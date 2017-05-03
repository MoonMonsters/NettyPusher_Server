package edu.csuft.chentao.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.csuft.chentao.dao.GroupOperationTable;
import edu.csuft.chentao.dao.GroupOperationTableOperate;
import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;

/**
 * 管理客户端连接
 * 
 * @author cusft.chentao
 *
 */
public class NettyCollections {

	/** 管道集合，用来与客户端进行沟通 */
	private static final Map<Integer, ChannelHandlerContext> sCtxMap = new HashMap<Integer, ChannelHandlerContext>();
	private static final ExecutorService sThreadPool = Executors
			.newFixedThreadPool(100);

	/**
	 * 将与服务端正在连接的客户端保存起来
	 * 
	 * @param username
	 *            用用户名挡住关键字
	 * @param chc
	 *            ChannelHandlerContext对象
	 */
	public static synchronized void add(Integer userid,
			ChannelHandlerContext chc) {
		sCtxMap.put(userid, chc);
		Logger.log("NettyCollections-->当前在线人数" + sCtxMap.size());
		Logger.log("NettyCollections-->当前在线userId=" + userid);
	}

	/**
	 * 根据用户名移除
	 * 
	 * @param username
	 *            用户名
	 */
	public static synchronized void removeWithUserId(int userid) {
		// 关闭流
		ChannelHandlerContext chc = sCtxMap.get(userid);
		if (chc != null) {
			chc.close();
			chc = null;
		}
		//移除
		sCtxMap.remove(userid);
	}

	/**
	 * 根据ChannelHandlerContext对象移除
	 * 
	 * @param chc
	 */
	public static synchronized void removeWithCHC(ChannelHandlerContext chc) {
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

		Collection<ChannelHandlerContext> collection = sCtxMap.values();
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
				ChannelHandlerContext chc = sCtxMap.get(userid);
				if (chc != null) {
					Logger.log("应该收到消息的userId->" + userid);
					chc.writeAndFlush(object);
				}
			}
		}
	}

	/**
	 * 从
	 */
	public static void readMessageFromDatabase() {

		new Thread(new Runnable() {

			public void run() {
				Iterator<Integer> it = sCtxMap.keySet().iterator();
				try {
					while (it.hasNext()) {
						final int userId = it.next();

						final GroupOperationTable table = GroupOperationTableOperate
								.queryByReaderId(userId);
						if (table != null) {
							Logger.log("table不为空，" + userId + "有数据可以读取");
							sThreadPool.execute(new Runnable() {

								public void run() {
									// 此数据暂时为空
									GroupReminderResp resp = new GroupReminderResp();
									// 得到群数据
									GroupInfoResp group = GroupTableOperate
											.getGroupInfoWithId2(table
													.getGroupId());

									resp.setType(table.getType());
									resp.setUserId(table.getUserId());
									resp.setGroupId(table.getGroupId());
									resp.setGroupName(group.getGroupname());
									resp.setDescription(table.getDescription());
									resp.setImage(group.getHeadImage());

									// 将消息发送到服务端
									sCtxMap.get(userId).writeAndFlush(resp);

									if (resp.getType() == Constant.TYPE_GROUP_REMINDER_ADD_GROUP) {
										group.setType(Constant.TYPE_GROUP_INFO_OWNER);
										// 加入群，把群的相关信息发送到客户端
										sCtxMap.get(userId)
												.writeAndFlush(group);
									}
								}
							});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					// 休眠5秒
					OperationUtil.sleepFor5000();
					// 重复执行
					readMessageFromDatabase();
				}
			}
		}).start();
	}

	public static Set<Integer> getConnectionUerIdList() {
		return sCtxMap.keySet();
	}

}
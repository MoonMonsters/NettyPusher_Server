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


import edu.csuft.chentao.dao.AnnouncementReaderOperate;
import edu.csuft.chentao.dao.AnnouncementTableOperate;
import edu.csuft.chentao.dao.GroupOperationTable;
import edu.csuft.chentao.dao.GroupOperationTableOperate;
import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.pojo.req.Announcement;
import edu.csuft.chentao.pojo.req.Message;
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
	/** key值为群id，value值为群中用户id集合 */
	private static final Map<Integer, List<Integer>> sUserIdListInMap = new HashMap<Integer, List<Integer>>();;

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
		Logger.log("NettyCollections-->当前在线人数=" + sCtxMap.size());
		Logger.log("NettyCollections-->当前在线userId=" + userid);
	}

	/**
	 * 根据用户名移除
	 * 
	 * @param username
	 *            用户名
	 */
	public static synchronized void removeWithUserId(int userId) {
		Logger.log("NettyCollections.removeWithUserId--->退出登录-->" + userId);
		// 关闭流
		ChannelHandlerContext chc = sCtxMap.get(userId);
		if (chc != null) {
			Logger.log("NettyCollections.removeWithUserId--->chc对象不为空，移除掉");
			chc.close();
			chc = null;
			// 移除
			sCtxMap.remove(userId);
			Logger.log("NettyCollections.removeWithUserId--->当前在线人数->"
					+ sCtxMap.size());
		}
	}

	/**
	 * 根据ChannelHandlerContext对象移除
	 * 
	 * @param chc
	 */
	public static synchronized void removeWithCHC(ChannelHandlerContext chc) {

		Logger.log("NettyCollections.removeWithCHC-->客户端断开连接");

		Collection<ChannelHandlerContext> collection = sCtxMap.values();
		if (collection.contains(chc)) {
			collection.remove(chc);
			Logger.log("NettyCollections.removeWithCHC--->当前在线人数-->"
					+ sCtxMap.size());
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
	public static void traverse(final int groupId, final Object object) {
		new Thread(new Runnable() {

			public void run() {
				// 得到群id得到群用户id集合
				List<Integer> userIdList = inputUserIdList(groupId);
				// 如果发送的是消息，则需要移除掉发送者id
				if (object instanceof Message) {
					Message msg = (Message) object;
					int userId = msg.getUserid();
					// 如果list中的值都是int类型，那么他以下标为主，所以这儿需要先得到值的下标
					int index = userIdList.indexOf(userId);
					userIdList.remove(index);
				}
				if (userIdList.size() > 0) {
					for (int userid : userIdList) {
						ChannelHandlerContext chc = sCtxMap.get(userid);
						if (chc != null) {
							chc.writeAndFlush(object);
						}
					}
				}
			}
		}).start();
	}

	/**
	 * 从数据表中读取数据，如果有，则读取出来发送到客户端
	 */
	public static void readMessageFromDatabase() {

		new Thread(new Runnable() {

			public void run() {
				Iterator<Integer> it = sCtxMap.keySet().iterator();
				try {
					while (it.hasNext()) {
						final int userId = it.next();

						readFromGroupOperationTable(userId);
						readFromAnnouncementReaderTable(userId);
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

	/**
	 * 从announcement_reader表中读取数据
	 * 
	 * @param userId 可读用户id
	 */
	private static void readFromAnnouncementReaderTable(int userId) {
		ChannelHandlerContext chc = sCtxMap.get(userId);
		if(chc != null){
			// 得到可读取公告集合
			List<String> serialNumberList = AnnouncementReaderOperate
					.queryByUserId(userId);
			// 从announcement_reader表中删除数据
			AnnouncementReaderOperate.delete(userId);
			for (String serialNumber : serialNumberList) {
				// 根据序列号得到公告数据
				Announcement announcement = AnnouncementTableOperate
						.queryAnnouncementBySerialNumber(serialNumber);
				// 发送
				chc.writeAndFlush(announcement);
			}
		}
	}

	/**
	 * 从group_operation表中读取数据
	 * 
	 * @param userId
	 *            用户id
	 */
	private static void readFromGroupOperationTable(final int userId) {
		final GroupOperationTable table = GroupOperationTableOperate
				.queryByReaderId(userId);
		if (table != null) {
			sThreadPool.execute(new Runnable() {

				public void run() {
					// 此数据暂时为空
					GroupReminderResp resp = new GroupReminderResp();
					// 得到群数据
					GroupInfoResp group = GroupTableOperate
							.getGroupInfoWithId2(table.getGroupId());

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
						sCtxMap.get(userId).writeAndFlush(group);
					}
				}
			});
		}
	}

	/**
	 * 得到所有连接客户端的用户id集合
	 */
	public static Set<Integer> getConnectionUerIdList() {
		return sCtxMap.keySet();
	}

	/**
	 * 获得ChannelHandlerContext
	 * 
	 * @param userId
	 *            用户id
	 */
	public static ChannelHandlerContext getChannelHandlerContextByUserId(
			int userId) {
		return sCtxMap.get(userId);
	}

	/**
	 * 根据群id得到该群中所有用户的id值
	 * 
	 * @param groupId
	 *            群id
	 * @return 用户id值集合
	 */
	private static List<Integer> inputUserIdList(int groupId) {

		// 根据群id得到群人数
		int groupUserNumber = GroupTableOperate
				.getGroupUserNumberByGroupId(groupId);
		List<Integer> userIdList = null;
		// 如果map中没有该群用户数据集合,或者集合中的用户人数和数据库中的用户人数不一致，则重新获取
		if (sUserIdListInMap.get(groupId) == null
				|| sUserIdListInMap.get(groupId).size() != groupUserNumber) {
			// 取出
			userIdList = GroupUserTableOperate.getAllUserIdWithGroupId(groupId);
			// 插入进去
			sUserIdListInMap.put(groupId, userIdList);
		} else {
			// 如果存在，则直接取出
			userIdList = sUserIdListInMap.get(groupId);
		}

		return userIdList;
	}
}
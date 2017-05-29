/**
 * 处理GetInfoHandler数据，该类包括各种各样的从客户端发过来的数据，用type来区分开来
 */
package edu.csuft.chentao.controller;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.csuft.chentao.dao.GroupFileZipTableOperation;
import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.dao.MessageTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2017年2月1日 下午9:59:51
 */
public class GetInfoHandler implements Handler {

	/**
	 * 主要用来解决同步与同步问题，因为每次收到命令时，都是不同的线程中 所以需要能跨线程的hashmap来解决问题
	 */
	private static ConcurrentHashMap<Integer, Boolean> mIsSyncMessageMap = new ConcurrentHashMap<Integer, Boolean>();

	public void handle(final ChannelHandlerContext chc, Object object) {
		final GetInfoReq req = (GetInfoReq) object;

		Logger.log("用户请求数据GetInfoReq->" + req.toString());

		switch (req.getType()) {
		case Constant.TYPE_GET_INFO_USERINFO: // 获取用户信息
			getUserInfo(chc, req);
			break;
		case Constant.TYPE_GET_INFO_UNLOGIN: // 退出登录
			userExitLogin(req);
			break;
		case Constant.TYPE_GET_INFO_SEARCH_GROUP_ID: // 根据群id搜索群
			searchGroupById(chc, req);
			break;
		case Constant.TYPE_GET_INFO_SEARCH_GROUP_NAME: // 根据群名搜索群
			searchGroupByName(chc, req);
			break;
		case Constant.TYPE_GET_INFO_SEARCH_GROUP_TAG: // 根据群标签搜索群
			searchGroupByTag(chc, req);
			break;
		case Constant.TYPE_GET_INFO_GROUP_FILE_LIST: // 获得群中的文件列表
			getGroupFileList(chc, req);
			break;
		case Constant.TYPE_GET_INFO_DOWNLOAD_FILE: // 下载文件
			downloadFile(chc, req);
			break;
		case Constant.TYPE_GET_INFO_REMOVE_FILE: // 删除文件
			removeGroupFile(chc, req);
			break;
		case Constant.TYPE_GET_INFO_START_SYNC_GROUP_MESSAGE: // 同步聊天信息
			// 需要放到子线程中去处理，1.发送大量数据很耗时
			// 2.需要用到停止同步功能，而如果不放到子线程中，会与同步功能在一个线程中，按照先后顺序执行，无法实现停止功能
			new Thread(new Runnable() {
				public void run() {
					startSyncMessage(chc, req);
				}
			}).start();
			break;
		case Constant.TYPE_GET_INFO_STOP_SYNC_GROUP_MESSAGE:
			stopSyncMessage(req);
			break;
		}
	}

	/**
	 * 获得group文件列表
	 */
	private void getGroupFileList(ChannelHandlerContext chc, GetInfoReq req) {
		List<FileZip> fileZipList = GroupFileZipTableOperation.queryAll(req
				.getArg1());
		if (fileZipList.size() == 0) {
			ReturnInfoResp resp = new ReturnInfoResp();
			resp.setType(Constant.TYPE_RETURN_INFO_FILE_LIST_SIZE_0);
			resp.setObj("该群中暂没有任何文件");
			chc.writeAndFlush(resp);
		} else {
			for (FileZip fz : fileZipList) { // 将所有数据发送到客户端
				chc.writeAndFlush(fz);
			}
		}
	}

	/**
	 * 根据群标签搜索群 搜索到的可能会有很多，所以一个一个的发送到客户端去
	 */
	private void searchGroupByTag(ChannelHandlerContext chc, GetInfoReq req) {
		// 得到标签
		String tag = (String) req.getObj();
		// 数据集合
		List<GroupInfoResp> groupInfoList = GroupTableOperate
				.getGroupInfoListWithGroupTag(tag);
		Logger.log("给群标签读取到数据条数-->" + groupInfoList.size());
		// 逐条发送
		for (GroupInfoResp gir : groupInfoList) {
			// 设置类型
			gir.setType(Constant.TYPE_GROUP_INFO_SEARCH);
			// 休眠0.5秒
			OperationUtil.sleepFor500();
			chc.writeAndFlush(gir);
		}
		if (groupInfoList.size() == 0) {
			sendRespForSearchGroupSize0(chc);
		}
	}

	/**
	 * 根据群名称搜索群
	 */
	private void searchGroupByName(ChannelHandlerContext chc, GetInfoReq req) {
		// 得到群名中的关键字
		String groupName = (String) req.getObj();
		// 得到随机的不多于10条数据
		List<GroupInfoResp> groupInfoList = GroupTableOperate
				.getGroupInfoListWithGroupName(groupName);
		Logger.log("给群名读取到数据条数-->" + groupInfoList.size());
		for (GroupInfoResp gir : groupInfoList) {
			// 设置类型
			gir.setType(Constant.TYPE_GROUP_INFO_SEARCH);
			// 休眠0.5秒
			OperationUtil.sleepFor500();
			chc.writeAndFlush(gir);
		}
		if (groupInfoList.size() == 0) {
			sendRespForSearchGroupSize0(chc);
		}
	}

	/**
	 * 根据群id搜索群
	 */
	private void searchGroupById(ChannelHandlerContext chc, GetInfoReq req) {
		// 群id
		int groupId = req.getArg1();
		// 从数据表中读取群数据
		GroupInfoResp resp = GroupTableOperate.getGroupInfoWithId(groupId);
		if (resp != null) {
			// 设置返回类型
			resp.setType(Constant.TYPE_GROUP_INFO_SEARCH);
			// 发送
			chc.writeAndFlush(resp);
		} else {
			sendRespForSearchGroupSize0(chc);
		}
	}

	/**
	 * 用户退出登录
	 */
	private void userExitLogin(GetInfoReq req) {
		int userId = req.getArg1();
		NettyCollections.removeWithUserId(userId); // 移除
	}

	/**
	 * 获得用户
	 */
	private void getUserInfo(ChannelHandlerContext chc, GetInfoReq req) {
		// 得到用户id
		int userId = req.getArg1();
		Logger.log("用户请求的userId->" + userId);
		// 根据用户id得到用户信息
		UserInfoResp resp = UserTableOperate.selectUserInfoWithUserId(userId);
		resp.setType(Constant.TYPE_LOGIN_USER_INFO);
		// 返回
		chc.writeAndFlush(resp);
	}

	/**
	 * 搜索群时，搜索到的数量为0
	 */
	private void sendRespForSearchGroupSize0(ChannelHandlerContext chc) {

		OperationUtil.sendReturnInfoResp(chc,
				Constant.TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0,
				"没有搜索到相关群数据，请查证后再尝试");
	}

	/**
	 * 下载文件
	 */
	private void downloadFile(ChannelHandlerContext chc, GetInfoReq req) {
		String serialNumber = (String) req.getObj();

		Logger.log(req.toString());

		byte[] content = OperationUtil.getGroupFile(req.getArg1(),
				(String) req.getObj());

		// 从数据表中获得数据
		FileZip fz = GroupFileZipTableOperation
				.queryBySerialNumber(serialNumber);
		fz.setZip(content);

		// 发送到客户端
		chc.writeAndFlush(fz);

	}

	/**
	 * 删除群文件
	 */
	private void removeGroupFile(ChannelHandlerContext chc, GetInfoReq req) {

		// 文件序列号
		String serialNumber = (String) req.getObj();
		// 群id
		int groupId = req.getArg1();
		// 用户id
		int userId = req.getArg2();

		int capital = GroupUserTableOperate.getCapitalWithUserIdAndGroupId(
				groupId, userId);
		int owerId = GroupFileZipTableOperation.getFileOwnerBySerialNumber(
				serialNumber, groupId);

		ReturnInfoResp resp = new ReturnInfoResp();

		// 如果要删除该文件的用户是，管理员，群主或者文件的上传者，那么则允许执行删除操作
		if (capital == Constant.TYPE_GROUP_CAPITAL_ADMIN
				|| capital == Constant.TYPE_GROUP_CAPITAL_OWNER
				|| owerId == userId) {
			// 删除表中数据
			boolean result1 = GroupFileZipTableOperation.deleteBySerialNumber(
					serialNumber, groupId);
			boolean result2 = false;
			File file = new File(serialNumber);
			// 删除文件
			if (file.exists()) {
				result2 = file.delete();
			}
			if (result1 && result2) {
				resp.setType(Constant.TYPE_RETURN_INFO_REMOVE_FILE_SUCCESS);
				resp.setObj(serialNumber);
			}
		} else {
			resp.setType(Constant.TYPE_RETURN_INFO_REMOVE_FILE_FAIL);
			resp.setObj("删除失败，请稍后再试");
		}

		// 发送执行结果到客户端
		chc.writeAndFlush(resp);
	}

	/**
	 * 从数据库中读取消息数据，发送到客户端
	 */
	private void syncGroupMessage(ChannelHandlerContext chc, GetInfoReq req) {

		int groupId = req.getArg1();
		int from = 0;
		int to = 20;

		List<Message> messageList = null;
		do {
			messageList = MessageTableOperate.queryAllByGroupId(groupId, from,
					to);
			// 分页查询，从from读取到to项
			from = to;
			to = to + 20;

			// 从Map中取出值，如果停止同步，则直接返回
			if (!mIsSyncMessageMap.get(req.getArg1())) {
				System.out.println("结束同步");
				return;
			}

			for (Message message : messageList) {

				chc.writeAndFlush(message);
			}

			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 如果集合不为空，并且大小不为0，则继续从数据表中取数据
		} while (messageList != null && messageList.size() != 0);

		/*
		 * 同步聊天记录完成，发送通知给客户端
		 */
		ReturnInfoResp resp = new ReturnInfoResp();
		resp.setType(Constant.TYPE_RETURN_INFO_SYNC_COMPLETE);
		resp.setArg1(req.getArg1());
		chc.writeAndFlush(resp);
	}

	/**
	 * 开始同步
	 */
	private void startSyncMessage(ChannelHandlerContext chc, GetInfoReq req) {

		int groupId = req.getArg1();
		// 开始同步，将groupId作为key值传入，同时将value值置为true
		mIsSyncMessageMap.put(groupId, true);
		syncGroupMessage(chc, req);
	}

	/**
	 * 停止同步
	 */
	private void stopSyncMessage(GetInfoReq req) {

		int groupId = req.getArg1();
		// 停止同步，将groupId对应的值置为false
		mIsSyncMessageMap.put(groupId, false);
	}
}

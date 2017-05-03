/**
 * 处理GetInfoHandler数据，该类包括各种各样的从客户端发过来的数据，用type来区分开来
 */
package edu.csuft.chentao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import edu.csuft.chentao.dao.GroupFileZipTableOperation;
import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.pojo.req.GetInfoReq;
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

	public void handle(ChannelHandlerContext chc, Object object) {
		GetInfoReq req = (GetInfoReq) object;

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
		}
	}

	/**
	 * 获得group文件列表
	 */
	private void getGroupFileList(ChannelHandlerContext chc, GetInfoReq req) {
		List<FileZip> fileZipList = GroupFileZipTableOperation.queryAll(req
				.getArg1());
		if (fileZipList.size() == 0) {
			ReturnInfoResp resp = new ReturnInfoResp(
					Constant.TYPE_RETURN_INFO_FILE_LIST_SIZE_0, "该群中暂没有任何文件");
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

		File file = new File(serialNumber);
		FileInputStream fis = null;
		try {
			byte[] content = new byte[(int) file.length()];
			fis = new FileInputStream(file);
			// 读取文件内容
			fis.read(content);

			//从数据表中获得数据
			FileZip fz = GroupFileZipTableOperation
					.queryBySerialNumber(serialNumber);
			fz.setZip(content);

			// 发送到客户端
			chc.writeAndFlush(fz);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

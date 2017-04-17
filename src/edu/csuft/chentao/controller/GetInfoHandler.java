/**
 * 处理GetInfoHandler数据，该类包括各种各样的从客户端发过来的数据，用type来区分开来
 */
package edu.csuft.chentao.controller;

import java.util.List;

import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
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

		if (req.getType() == Constant.TYPE_GET_INFO_USERINFO) { // 请求用户信息
			// 得到用户id
			int userId = req.getArg1();
			Logger.log("用户请求的userId->" + userId);
			// 根据用户id得到用户信息
			UserInfoResp resp = UserTableOperate
					.selectUserInfoWithUserId(userId);
			resp.setType(Constant.TYPE_LOGIN_USER_INFO);
			// 返回
			chc.writeAndFlush(resp);
		} else if (req.getType() == Constant.TYPE_GET_INFO_UNLOGIN) { // 退出登录
			int userId = req.getArg1();
			NettyCollections.removeWithUserId(userId); // 移除
		} else if (req.getType() == Constant.TYPE_GET_INFO_SEARCH_GROUP_ID) { // 根据群id搜索
			// 群id
			int groupId = req.getArg1();
			// 从数据表中读取群数据
			GroupInfoResp resp = GroupTableOperate.getGroupInfoWithId(groupId);
			if(resp != null){
				// 设置返回类型
				resp.setType(Constant.TYPE_GROUP_INFO_SEARCH);
				// 发送
				chc.writeAndFlush(resp);
			}else{
				sendRespForSearchGroupSize0(chc);
			}
		} else if (req.getType() == Constant.TYPE_GET_INFO_SEARCH_GROUP_NAME) { // 根据群名搜索
			// 得到群名中的关键字
			String groupName = (String) req.getObj();
			// 得到随机的不多于10条数据
			List<GroupInfoResp> groupInfoList = GroupTableOperate
					.getGroupInfoListWithGroupName(groupName);
			Logger.log("给群名读取到数据条数-->"+groupInfoList.size());
			for (GroupInfoResp gir : groupInfoList) {
				// 设置类型
				gir.setType(Constant.TYPE_GROUP_INFO_SEARCH);
				// 休眠0.5秒
				OperationUtil.sleepFor500();
				chc.writeAndFlush(gir);
			}
			if(groupInfoList.size() == 0){
				sendRespForSearchGroupSize0(chc);
			}
		} else if (req.getType() == Constant.TYPE_GET_INFO_SEARCH_GROUP_TAG) { // 根据群标签搜索
			//得到标签
			String tag = (String) req.getObj();
			//数据集合
			List<GroupInfoResp> groupInfoList = GroupTableOperate
					.getGroupInfoListWithGroupTag(tag);
			Logger.log("给群标签读取到数据条数-->"+groupInfoList.size());
			//逐条发送
			for (GroupInfoResp gir : groupInfoList) {
				// 设置类型
				gir.setType(Constant.TYPE_GROUP_INFO_SEARCH);
				// 休眠0.5秒
				OperationUtil.sleepFor500();
				chc.writeAndFlush(gir);
			}
			if(groupInfoList.size() == 0){
				sendRespForSearchGroupSize0(chc);
			}
		}
	}

	private void sendRespForSearchGroupSize0(ChannelHandlerContext chc) {
		
		OperationUtil.sendReturnInfoResp(chc, Constant.TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0, "没有搜索到相关群数据，请查证后再尝试");
	}
}

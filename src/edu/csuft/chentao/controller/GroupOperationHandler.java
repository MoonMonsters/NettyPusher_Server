/**
 * 处理用户对群的操作，例如添加或退出等
 */
package edu.csuft.chentao.controller;

import java.util.List;

import edu.csuft.chentao.dao.GroupOperationTable;
import edu.csuft.chentao.dao.GroupOperationTableOperate;
import edu.csuft.chentao.dao.GroupTableOperate;
import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:14:10
 */
public class GroupOperationHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {

		GroupOperationReq req = (GroupOperationReq) object;
		GroupInfoResp groupInfo = GroupTableOperate.getGroupInfoWithId(req
				.getGroupId());

		if (req.getType() == Constant.TYPE_GROUP_OPERATION_EXIT_BY_MYSELF) { // 自己退出

			Logger.log("自己退出群");

			int groupId = req.getGroupId();
			int userId = req.getUserId1();
			int capital = GroupUserTableOperate.getCapitalWithUserIdAndGroupId(
					groupId, userId);

			if (capital == 0) { // 如果是群主退出群，那么便解散该群

				List<Integer> userIdList = GroupUserTableOperate
						.getAllUserIdWithGroupId(groupId);
				// 移除该群中所有成员
				GroupUserTableOperate.removeAllUserWithGroupId(groupId);
				// 从数据表中删除群数据
				// GroupTableOperate.removeGroupDataWithGroupId(groupId);
				GroupOperationTable table = new GroupOperationTable();
				table.setDescription("该群已经解散");
				table.setGroupId(groupId);
				table.setType(Constant.TYPE_GROUP_REMINDER_REMOVE_USER);
				table.setUserId(userId);
				// 将群中所有人分别发送一条消息
				for (int readerId : userIdList) {
					table.setReaderId(readerId);
					GroupOperationTableOperate.insert(table);
				}
			} else if (capital != -1) { // 否则只是退出
				boolean result = GroupUserTableOperate.exitGroup(
						req.getGroupId(), req.getUserId1());
				if (result) { // 如果退出成功
					GroupReminderResp resp = new GroupReminderResp();
					resp.setType(Constant.TYPE_GROUP_REMINDER_EXIT_BY_MYSELF);
					resp.setGroupId(req.getGroupId());
					resp.setImage(groupInfo.getHeadImage());
					resp.setDescription("退出群成功");
					resp.setGroupName(groupInfo.getGroupname());
					resp.setUserId(req.getUserId1());

					chc.writeAndFlush(resp);
				} else { // 退出群失败
					ReturnInfoResp resp = new ReturnInfoResp();
					resp.setType(Constant.TYPE_RETURN_INFO_EXIT_GROUP_FAIL);
					resp.setDescription("退出群失败，请查证后再试");

					chc.writeAndFlush(resp);
				}
			}
		} else if (req.getType() == Constant.TYPE_GROUP_OPERATION_EXIT_BY_ADMIN) { // 被踢出

			Logger.log("踢出群");
			// 被踢出用户id
			int userId = req.getUserId1();
			// 被踢出的群
			int groupId = req.getGroupId();
			boolean result = GroupUserTableOperate.exitGroup(req.getGroupId(),
					req.getUserId1());
			if (result) { // 踢出成功
				GroupOperationTable table = new GroupOperationTable();
				table.setDescription("你已被踢出群");
				table.setGroupId(groupId);
				table.setReaderId(userId);
				table.setType(Constant.TYPE_GROUP_REMINDER_REMOVE_USER);
				table.setUserId(userId);

				GroupOperationTableOperate.insert(table);
				
				Logger.log("用户被踢出群成功----"+userId);
				
				// 踢出群成功，发送消息给客户端
				OperationUtil.sendReturnInfoResp(chc,
						Constant.TYPE_RETURN_INFO_REMOVE_USER_SUCCESS, "踢出群成功");
			} else { // 踢出失败
				Logger.log("用户被踢出群失败--"+userId);
				// 管理员把用户踢出群失败，发送消息提醒客户端
				OperationUtil.sendReturnInfoResp(chc,
						Constant.TYPE_RETURN_INFO_REMOVE_USER_FAIL,
						"踢出群失败，请稍后再试");
			}
		} else if (req.getType() == Constant.TYPE_GROUP_OPERATION_ADD_BY_MYSELF) { // 自己申请加入群

			int userId = req.getUserId1();
			int groupId = req.getGroupId();

			Logger.log(userId + "自己申请加入群" + groupId);

			// 如果申请的群不存在
			if (!GroupTableOperate.isExitGroupWithGroupId(groupId)) {
				Logger.log(groupId + " 群不存在");
				// 申请的群id不存在，向客户端发送返回消息
				OperationUtil.sendReturnInfoResp(chc,
						Constant.TYPE_RETURN_INFO_GROUP_NOT_EXIST, "申请的群不存在");
			} else if (!GroupUserTableOperate.isExit(groupId, userId)) { // 如果不是群成员

				Logger.log("在群里不存在");

				String name = UserTableOperate.getUsernameWithUserId(userId);
				List<Integer> readerIdList = GroupUserTableOperate
						.getCapital_0_WithGroupId(groupId);

				GroupOperationTable table = new GroupOperationTable();
				table.setDescription(name + " 申请加入群");
				table.setGroupId(groupId);
				table.setType(Constant.TYPE_GROUP_REMINDER_WANT_TO_ADD_GROUP);
				table.setUserId(userId);

				Logger.log("申请加入群时，群管理人员数量->" + readerIdList.size());

				for (int readerId : readerIdList) {
					// 设置不同的读取消息用户
					table.setReaderId(readerId);
					GroupOperationTableOperate.insert(table);
				}
			} else if (GroupUserTableOperate.isExit(groupId, userId)) { // 如果在群里已经存在
				Logger.log("在群里已经存在");
				// 当重复添加群时，返回此消息
				OperationUtil.sendReturnInfoResp(chc,
						Constant.TYPE_RETURN_INFO_GROUP_MUL_USER,
						"你已加入群，不要重复添加");
			}
		} else if (req.getType() == Constant.TYPE_GROUP_OPERATION_ADD_BY_INVITE) { // 被邀请加入群
			Logger.log("邀请加入群");

			// 被邀请人id
			int userId1 = req.getUserId1();
			// 群id
			int groupId = req.getGroupId();

			if (!UserTableOperate.isExitUserWithUserId(userId1)) { // 被邀请人用户id错误，不存在该用户
				OperationUtil
						.sendReturnInfoResp(chc,
								Constant.TYPE_RETURN_INFO_ERROR_USERID,
								"用户id错误，不存在该用户");
			} else if (!GroupUserTableOperate.isExit(groupId, userId1)) { // 如果该用户不在该群里
				Logger.log(userId1 + "不存在，可以插入");
				int userId2 = req.getUserId2();
				// 获得邀请人的姓名
				String userName2 = UserTableOperate
						.getUsernameWithUserId(userId2);
				GroupOperationTable table = new GroupOperationTable();
				table.setDescription(userName2 + "邀请你加入群");
				table.setGroupId(groupId);
				table.setType(Constant.TYPE_GROUP_REMINDER_INVITE_GROUP);
				table.setUserId(userId1);
				// 设置读取消息的用户id
				table.setReaderId(userId1);
				GroupOperationTableOperate.insert(table);

				OperationUtil.sendReturnInfoResp(chc,
						Constant.TYPE_RETURN_INFO_INVITE_SUCCESS, "邀请成功");
			} else if (GroupUserTableOperate.isExit(groupId, userId1)) { // 已经存在群里
				Logger.log(userId1 + "已经存在，不能插入");
				OperationUtil.sendReturnInfoResp(chc,
						Constant.TYPE_RETURN_INFO_INVITE_REPEAT,
						"用户已经在群里，请不要重复邀请");
			}

		} else if (req.getType() == Constant.TYPE_GROUP_OPERATION_AGREE_ADD_GROUP) { // 同意加入群
			Logger.log("同意加入群");

			if (!GroupUserTableOperate.isExit(req.getGroupId(),
					req.getUserId1())) { // 如果群里不存在， 才插入数据
				// 同意加入群
				boolean result = GroupUserTableOperate.insert(req.getGroupId(),
						req.getUserId1(), 2);
				if (result) { // 数据插入成功
					GroupOperationTable table = new GroupOperationTable();
					table.setDescription("成功加入群");
					table.setGroupId(req.getGroupId());
					table.setType(Constant.TYPE_GROUP_REMINDER_ADD_GROUP);
					table.setUserId(req.getUserId1());
					// 设置读取消息的用户id
					table.setReaderId(req.getUserId1());
					GroupOperationTableOperate.insert(table);
				}
			}
		} else if (req.getType() == Constant.TYPE_GROUP_OPERATION_REFUSE_ADD_GROUP) { // 拒绝加入群
			Logger.log("拒绝加入群");

			int userId = req.getUserId1();
			int groupId = req.getGroupId();

			if (!GroupUserTableOperate.isExit(groupId, userId)) { // 如果没有被允许加入群，那么便提示拒绝信息
				GroupOperationTable table = new GroupOperationTable();
				table.setDescription("你被拒绝加入群");
				table.setGroupId(req.getGroupId());
				table.setType(Constant.TYPE_GROUP_REMINDER_REFUSE_ADD_GROUP);
				table.setUserId(req.getUserId1());
				// 设置读取消息的用户id
				table.setReaderId(req.getUserId1());
				GroupOperationTableOperate.insert(table);
			}
		}
	}
}

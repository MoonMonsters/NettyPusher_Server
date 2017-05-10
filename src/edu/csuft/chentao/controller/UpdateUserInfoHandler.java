/**
 * 处理更新信息
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.pojo.req.UpdateUserInfoReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.Logger;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:25:49
 */
public class UpdateUserInfoHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {

		Logger.log("UpdateUserInfoHandler-->更新信息操作");

		UpdateUserInfoReq req = (UpdateUserInfoReq) object;
		ReturnInfoResp resp = null;

		/*
		 * req.getContent就包括了签名和昵称两种类型，在客户端就设置好
		 */
		if (req.getType() == Constant.TYPE_UPDATE_NICKNAME) { // 更新昵称
			resp = UserTableOperate.updateUserNickname(req.getUserid(),
					req.getContent());
		} else if (req.getType() == Constant.TYPE_UPDATE_SIGNATURE) { // 更新签名
			resp = UserTableOperate.updateUserSignature(req.getUserid(),
					req.getContent());
		} else if (req.getType() == Constant.TYPE_UPDATE_HEADIMAGE) { // 更新头像
			// 更改图片名
			// 重新保存一次头像
			resp = new ReturnInfoResp();
			try {
				OperationUtil
						.saveHeadImage(req.getHeadImage(), req.getUserid());
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS);
				resp.setObj("头像更改成功");
			} catch (Exception e) {
				e.printStackTrace();
				resp.setType(Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_FAIL);
				resp.setObj("头像更改失败");
			}
		}

		Logger.log("ReturnMessageResp-->返回更新后的信息");

		// 返回结果
		chc.writeAndFlush(resp);
	}

}

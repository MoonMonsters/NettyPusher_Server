/**
 * 处理更新信息
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.pojo.req.UpdateUserInfoReq;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:25:49
 */
public class UpdateUserInfoHandler implements Handler {

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {
		UpdateUserInfoReq req = (UpdateUserInfoReq) object;
		ReturnMessageResp resp = null;

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
			String filename = req.getHeadImage().getFilename();
			req.getHeadImage().setFilename(
					req.getUserid()
							+ filename.substring(filename.lastIndexOf(".")));
			// 重新保存一次头像
			resp = new ReturnMessageResp();
			try {
				OperationUtil.saveHeadImage(req.getHeadImage());
				resp.setType(Constant.TYPE_RETURN_MESSAGE_SUCCESS);
				resp.setDescription("头像更改成功");
			} catch (Exception e) {
				e.printStackTrace();
				resp.setType(Constant.TYPE_RETURN_MESSAGE_FAIL);
				resp.setDescription("头像更改失败");
			}
		}

		//返回结果
		chc.writeAndFlush(resp);
	}

}

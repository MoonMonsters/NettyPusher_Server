/**
 * 
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.UserTableOperate;
import edu.csuft.chentao.pojo.req.ChangePasswordReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.util.Constant;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2017年5月7日 上午9:40:28
 */
public class ChangePasswordHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {
		ChangePasswordReq req = (ChangePasswordReq) object;
		int userId = req.getUserId();
		String oldPassword = req.getOldPassword();
		String newPassword = req.getNewPassword();
		String newPassword2 = req.getNewPassword2();

		ReturnInfoResp resp = new ReturnInfoResp();
		// 判断密码和用户id是否匹配
		if (UserTableOperate.isRightByUserIdAndPassword(userId, oldPassword)) {
			// 判断新密码是否符合规则
			if (newPassword != null && !newPassword.equals("")
					&& newPassword.equals(newPassword2)
					&& newPassword.length() > 6 && newPassword.length() < 12) {
				boolean isSuccess = UserTableOperate.updateUserPassword(userId,
						newPassword);
				if (isSuccess) {
					resp.setType(Constant.TYPE_RETURN_INFO_CHANGE_PASSWORD_SUCCESS);
					resp.setObj(newPassword);
				}
			} else {
				resp.setType(Constant.TYPE_RETURN_INFO_CHANGE_PASSWORD_FAIL);
			}
		} else {
			resp.setType(Constant.TYPE_RETURN_INFO_CHANGE_PASSWORD_FAIL);
		}

		chc.writeAndFlush(resp);
	}

}

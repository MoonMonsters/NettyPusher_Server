/**
 * 工厂模式，返回Handler对象
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.pojo.req.Announcement;
import edu.csuft.chentao.pojo.req.CreateGroupReq;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.GetUserAndGroupInfoReq;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.pojo.req.ManagerUserReq;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.req.RegisterReq;
import edu.csuft.chentao.pojo.req.UpdateUserInfoReq;
import edu.csuft.chentao.util.Logger;

/**
 * @author csuft.chentao
 * 
 *         2016年12月9日 下午8:52:30
 */
public class MessageHandlerFactory {

	/**
	 * 根据object类型，返回对应处理对象
	 * 
	 * @param object
	 *            客户端发送的数据类型
	 */
	public static Handler getHandlerInstance(Object object) {
		Handler handler = null;

		String msg = "MessageHandlerFactory-->";

		if (object instanceof CreateGroupReq) { // 处理创建群消息
			msg += "CreateGroupReq";
			handler = new CreateGroupHandler();
		} else if (object instanceof GroupOperationReq) { // 群操作
			msg += "GroupOperationReq";
			handler = new GroupOperationHandler();
		} else if (object instanceof LoginReq) { // 登录
			msg += "LoginReq";
			handler = new LoginHandler();
		} else if (object instanceof Message) { // 发送消息
			msg += "MessageReq";
			handler = new MessageHandler();
		} else if (object instanceof RegisterReq) { // 注册
			msg += "RegisterReq";
			handler = new RegisterHandler();
		} else if (object instanceof UpdateUserInfoReq) { // 更新用户信息
			msg += "UpdateInfoReq";
			handler = new UpdateUserInfoHandler();
		} else if (object instanceof GetUserAndGroupInfoReq) { // 获得用户或者群信息
			msg += "GetUserAndGroupInfoReq";
			handler = new GetUserAndGroupInfoHandler();
		} else if (object instanceof ManagerUserReq) { // 修改用户身份信息
			msg += "ManagerUserReq";
			handler = new ManagerUserHandler();
		} else if (object instanceof GetInfoReq) { // 客户端向服务端请求数据
			msg += "GetInfoReq";
			handler = new GetInfoHandler();
		} else if (object instanceof Announcement) { // 处理公告数据
			msg += "Announcement";
			handler = new AnnouncementHandler();
		} else if (object instanceof FileZip) {
			msg += "FileZipHandler";
			handler = new FileZipHandler();
		}else{
			msg += "errorObject";
		}

		Logger.log(msg);

		return handler;
	}

}

/**
 * 
 */
package edu.csuft.chentao.util;

/**
 * @author csuft.chentao
 *
 *         2016年12月10日 上午10:22:54
 */
public interface Constant {

	/** 默认用户id值，从10000开始 */
	int DEFAULT_USERID = 10000;
	/** 默认群id从20000开始 */
	int DEFAULT_GROUPID = 100000;

	/** 注册失败，用户名重复 */
	int TYPE_REGISTER_REPEAT_USERNAME = 0;
	/** 注册成功 */
	int TYPE_REGISTER_SUCCESS = 1;

	/** 创建群成功 */
	int CREATE_GROUP_SUCCESS = 0;
	/** 创建群失败 */
	int CREATE_GROUP_FAIL = 1;

	/** 加入群 */
	int TYPE_GROUP_ENTER = 0;
	/** 退出群 */
	int TYPE_GROUP_EXIT = 1;

	/** 成功 */
	int TYPE_RETURN_MESSAGE_SUCCESS = 0;
	/** 失败 */
	int TYPE_RETURN_MESSAGE_FAIL = 3;

	/** 更新昵称 */
	int TYPE_UPDATE_NICKNAME = 0;
	/** 更新签名 */
	int TYPE_UPDATE_SIGNATURE = 1;
	/** 更新头像 */
	int TYPE_UPDATE_HEADIMAGE = 2;

	/** 自动登录 */
	int TYPE_LOGIN_AUTO = 0;
	/** 新的登录 */
	int TYPE_LOGIN_NEW = 1;
	/** 其他用户信息 */
	int TYPE_LOGIN_USER_INFO = 2;

	/** 发送消息 */
	int TYPE_MSG_SEND = 0;
	/** 接收消息 */
	int TYPE_MSG_RECV = 1;

	String PATH = "./headimage";

}

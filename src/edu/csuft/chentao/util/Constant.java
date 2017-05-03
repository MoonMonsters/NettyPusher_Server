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
	int TYPE_RETURN_INFO_SUCCESS = 0;
	/** 失败 */
	int TYPE_RETURN_INFO_FAIL = 1;
	/**
	 * 创建群成功
	 */
	int TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS = 2;
	/**
	 * 创建群失败
	 */
	int TYPE_RETURN_INFO_CREATE_GROUP_FAIL = 3;
	/**
	 * 更新头像成功
	 */
	int TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS = 4;
	/**
	 * 更新头像失败
	 */
	int TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_FAIL = 5;
	/**
	 * 更新签名成功
	 */
	int TYPE_RETURN_INFO_UPDATE_SIGNATURE_SUCESS = 6;
	/**
	 * 更新签名失败
	 */
	int TYPE_RETURN_INFO_UPDATE_SIGNATURE_FAIL = 7;
	/**
	 * 更新昵称成功
	 */
	int TYPE_RETURN_INFO_UPDATE_NICKNAME_SUCCESS = 8;
	/**
	 * 更新昵称失败
	 */
	int TYPE_RETURN_INFO_UPDATE_NICKNAME_FAIL = 9;
	/**
	 * 更新用户身份成功
	 */
	int TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS = 10;
	/**
	 * 更新用户身份失败
	 */
	int TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_FAIL = 11;
	/**
	 * 搜索群时，数据个数为0
	 */
	int TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0 = 12;

	/**
	 * 退出群失败
	 */
	int TYPE_RETURN_INFO_EXIT_GROUP_FAIL = 13;

	/*
	 * new
	 */
	/**
	 * 管理员把用户踢出群成功
	 */
	int TYPE_RETURN_INFO_REMOVE_USER_SUCCESS = 14;
	/**
	 * 管理员把用户踢出群失败
	 */
	int TYPE_RETURN_INFO_REMOVE_USER_FAIL = 15;
	/**
	 * 申请加入群，群不存在
	 */
	int TYPE_RETURN_INFO_GROUP_NOT_EXIST = 16;
	/**
	 * 申请加入群，用户已经在群中存在
	 */
	int TYPE_RETURN_INFO_GROUP_MUL_USER = 17;
	/**
	 * 错误的用户id
	 */
	int TYPE_RETURN_INFO_ERROR_USERID = 18;
	/**
	 * 邀请用户加入群，邀请成功
	 */
	int TYPE_RETURN_INFO_INVITE_SUCCESS = 19;
	/**
	 * 邀请用户时，用户已经在群里，重复邀请
	 */
	int TYPE_RETURN_INFO_INVITE_REPEAT = 20;
	/**
	 * 文件列表数据为0
	 */
	int TYPE_RETURN_INFO_FILE_LIST_SIZE_0 = 21;

	/**
	 * 获取用户数据
	 */
	int TYPE_GET_INFO_USERINFO = 0;
	/**
	 * 退出登录
	 */
	int TYPE_GET_INFO_UNLOGIN = 1;
	/**
	 * 根据群id搜索
	 */
	int TYPE_GET_INFO_SEARCH_GROUP_ID = 2;
	/**
	 * 根据群名搜索
	 */
	int TYPE_GET_INFO_SEARCH_GROUP_NAME = 3;
	/**
	 * 根据标签搜索
	 */
	int TYPE_GET_INFO_SEARCH_GROUP_TAG = 4;
	/**
	 * 得到群中所有的文件列表
	 */
	int TYPE_GET_INFO_GROUP_FILE_LIST = 5;
	/**
	 * 下载文件
	 */
	int TYPE_GET_INFO_DOWNLOAD_FILE = 6;
	
	/**
	 * 用户拥有的群
	 */
	int TYPE_GROUP_INFO_OWNER = 0;
	/**
	 * 用户搜索时获取的群
	 */
	int TYPE_GROUP_INFO_SEARCH = 1;

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

	/*
	 * 在群里的身份信息
	 */
	/**
	 * 群主
	 */
	int TYPE_GROUP_CAPITAL_OWNER = 0;
	/**
	 * 管理员
	 */
	int TYPE_GROUP_CAPITAL_ADMIN = 1;
	/**
	 * 普通用户
	 */
	int TYPE_GROUP_CAPITAL_USER = 2;

	/*
	 * 请求的数类型
	 */
	/**
	 * 请求用户信息
	 */
	int TYPE_USER_GROUP_INFO_USER = 0;
	/**
	 * 请求群信息
	 */
	int TYPE_USER_GROUP_INFO_GROUP = 1;

	/*
	 * 群操作
	 */
	/**
	 * 自己退出群
	 */
	int TYPE_GROUP_OPERATION_EXIT_BY_MYSELF = 1;
	/**
	 * 被管理员踢出群
	 */
	int TYPE_GROUP_OPERATION_EXIT_BY_ADMIN = 2;
	/**
	 * 自己加入群
	 */
	int TYPE_GROUP_OPERATION_ADD_BY_MYSELF = 3;
	/**
	 * 被邀请加入群
	 */
	int TYPE_GROUP_OPERATION_ADD_BY_INVITE = 4;
	/**
	 * 同意加入群
	 */
	int TYPE_GROUP_OPERATION_AGREE_ADD_GROUP = 5;
	/**
	 * 拒绝加入群
	 */
	int TYPE_GROUP_OPERATION_REFUSE_ADD_GROUP = 6;

	/*
	 * 群消息相应
	 */
	// 1.退出群
	int TYPE_GROUP_REMINDER_EXIT_BY_MYSELF = 0;
	// 2.踢出群
	int TYPE_GROUP_REMINDER_REMOVE_USER = 1;
	// 3.加入群,直接就是加入了群提示信息
	int TYPE_GROUP_REMINDER_ADD_GROUP = 2;
	// 4.邀请入群
	int TYPE_GROUP_REMINDER_INVITE_GROUP = 3;
	// 5.拒绝用户加入群
	int TYPE_GROUP_REMINDER_REFUSE_ADD_GROUP = 4;
	// 6.同意用户加入群
	int TYPE_GROUP_REMINDER_AGREE_ADD_GROUP = 5;
	// 7.某用户申请加入群
	int TYPE_GROUP_REMINDER_WANT_TO_ADD_GROUP = 6;
}
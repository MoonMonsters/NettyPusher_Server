package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * 发送的消息
 * 
 * @author cusft.chentao
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户id */
	int userid;
	/** 用户昵称 */
	String nickname;
	/** 头像，直接根据id值从数据表中读取，在客户端中，如果没有存储该图片，则从服务端取得 */
	String headimage;
	/** 群的id，表示在哪个群中发送的消息 */
	int groupid;
	/**
	 * TYPE_MSG_TEXT 0 TYPE_MSG_PIC 1
	 */
	int typeMsg; // 消息内容，是文字消息还是图片消息
	/**
	 * TYPE_MSG_SEND 0 TYPE_MSG_RECV 1
	 */
	int type; // 是发送还是接收
	/** 消息发送时间 */
	String time;
	/** 发送的文字内容 */
	String message;
	/** 发送的图片 */
	PicFile picFile;

	public Message(int userid, String nickname, String headimage,
			int groupid, int typeMsg, int type, String time, String message,
			PicFile picFile) {
		super();
		this.userid = userid;
		this.nickname = nickname;
		this.headimage = headimage;
		this.groupid = groupid;
		this.typeMsg = typeMsg;
		this.type = type;
		this.time = time;
		this.message = message;
		this.picFile = picFile;
	}

	public Message() {
		super();
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImageMd5() {
		return headimage;
	}

	public void setHeadImageMd5(String headimage) {
		this.headimage = headimage;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(int typeMsg) {
		this.typeMsg = typeMsg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PicFile getPicFile() {
		return picFile;
	}

	public void setPicFile(PicFile picFile) {
		this.picFile = picFile;
	}

	@Override
	public String toString() {
		return "MessageReq [userid=" + userid + ", nickname=" + nickname
				+ ", headimage=" + headimage + ", groupid=" + groupid
				+ ", typeMsg=" + typeMsg + ", type=" + type + ", time=" + time
				+ ", message=" + message + ", picFile=" + picFile + "]";
	}

}

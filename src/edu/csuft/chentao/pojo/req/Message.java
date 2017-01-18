package edu.csuft.chentao.pojo.req;

import java.io.Serializable;
import java.util.Arrays;

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
	byte[] picFile;

	public Message(int userid, int groupid, int typeMsg, int type, String time,
			String message, byte[] picFile) {
		super();
		this.userid = userid;
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

	public byte[] getPicFile() {
		return picFile;
	}

	public void setPicFile(byte[] picFile) {
		this.picFile = picFile;
	}

	@Override
	public String toString() {
		return "Message [userid=" + userid + ", groupid=" + groupid
				+ ", typeMsg=" + typeMsg + ", type=" + type + ", time=" + time
				+ ", message=" + message + ", picFile="
				+ Arrays.toString(picFile) + "]";
	}

}

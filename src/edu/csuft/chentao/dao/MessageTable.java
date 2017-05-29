package edu.csuft.chentao.dao;

/**
 * @author csuft.chentao
 *
 *         2017年5月29日 上午12:02:41
 */
public class MessageTable {

	public static String TABLE_ALL_FIELD = "message(user_id,group_id,type_msg,time,content,image_serial_number,message_serial_number)";
	public static String TABLE_NAME = "message";
	public static String ID = "id";
	public static String USER_ID = "user_id";
	public static String GROUP_ID = "group_id";
	public static String TYPE_MSG = "type_msg";
	public static String TIME = "time";
	public static String CONTENT = "content";
	public static String IMAGE_SERIAL_NUMBER = "image_serial_number";
	public static String MESSAGE_SERIAL_NUMBER = "message_serial_number";
	/**
	 * 消息发送者ID
	 */
	private int userId;
	/**
	 * 群ID
	 */
	private int groupId;
	/**
	 * 消息类型，图片或文字
	 */
	private int typeMsg;
	/**
	 * 发送时间
	 */
	private String time;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 图片存储序列号
	 */
	private String imageSerialNumber;
	/**
	 * 整个消息的序列号
	 */
	private int messageSerialNumber;
	
	public MessageTable(int userId, int groupId, int typeMsg, String time,
			String content, String imageSerialNumber, int messageSerialNumber) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.typeMsg = typeMsg;
		this.time = time;
		this.content = content;
		this.imageSerialNumber = imageSerialNumber;
		this.messageSerialNumber = messageSerialNumber;
	}

	public MessageTable() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(int typeMsg) {
		this.typeMsg = typeMsg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageSerialNumber() {
		return imageSerialNumber;
	}

	public void setImageSerialNumber(String image_serial_number) {
		this.imageSerialNumber = image_serial_number;
	}

	public int getMessageSerialNumber() {
		return messageSerialNumber;
	}

	public void setMessageSerialNumber(int messageSerialNumber) {
		this.messageSerialNumber = messageSerialNumber;
	}

	@Override
	public String toString() {
		return "MessageTable [userId=" + userId + ", groupId=" + groupId
				+ ", typeMsg=" + typeMsg + ", time=" + time + ", content="
				+ content + ", imageSerialNumber=" + imageSerialNumber
				+ ", messageSerialNumber=" + messageSerialNumber + "]";
	}

}

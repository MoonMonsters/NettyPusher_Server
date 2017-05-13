/**
 * 
 */
package edu.csuft.chentao.dao;

/**
 * @author csuft.chentao
 *
 *         2017年2月7日 下午10:29:23
 */
public class GroupOperationTable {
	private int type;
	private int userId;
	private int groupId;
	private String description;
	private int readerId;

	/**
	 * 插入用到
	 */
	public static String TABLE_ALL_FIELD = " group_operation(type,user_id,group_id,description,reader_id) ";
	public static String VALUES = " values(?,?,?,?,?) ";
	/**
	 * 表名称
	 */
	public static String TABLE_NAME = "group_operation";
	/**
	 * id
	 */
	public static String ID = "id";
	/**
	 * 类型
	 */
	public static String TYPE = "type";
	/**
	 * 用户id
	 */
	public static String USER_ID = "user_id";
	/**
	 * 群id
	 */
	public static String GROUP_ID = "group_id";
	/**
	 * 描述
	 */
	public static String DESCRIPTION = "description";
	/**
	 * 读取此消息的用户id
	 */
	public static String READER_ID = "reader_id";

	public GroupOperationTable(int type, int userId, int groupId,
			String description, int readerId) {
		super();
		this.type = type;
		this.userId = userId;
		this.groupId = groupId;
		this.description = description;
		this.readerId = readerId;
	}

	public GroupOperationTable() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
}

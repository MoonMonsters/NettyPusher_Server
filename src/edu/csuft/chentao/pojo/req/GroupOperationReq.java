package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * 群操作，加入群或者退出群
 * 
 * @author cusft.chentao
 *
 */
public class GroupOperationReq implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 对群的各种操作 */
	int type;
	/** 操作的群 */
	int groupId;
	/** 操作对象，用户的id */
	int userId1;
	/** 操作对象2，在执行邀请加入群操作时需要用上 */
	int userId2;

	public GroupOperationReq(int type, int groupId, int userId1, int userId2) {
		super();
		this.type = type;
		this.groupId = groupId;
		this.userId1 = userId1;
		this.userId2 = userId2;
	}

	public GroupOperationReq() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserId1() {
		return userId1;
	}

	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}

	public int getUserId2() {
		return userId2;
	}

	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}

	@Override
	public String toString() {
		return "GroupOperationReq [type=" + type + ", groupId=" + groupId
				+ ", userId1=" + userId1 + ", userId2=" + userId2 + "]";
	}

}

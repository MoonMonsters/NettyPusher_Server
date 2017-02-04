/**
 * 
 */
package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 * 2017年1月30日 下午10:33:01
 */
/**
 * 管理员或者群主对群员进行管理
 * 
 * @author csuft.chentao
 *
 *         2017年1月30日 下午10:34:19
 */
public class ManagerUserReq implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 用户id */
	private int userId;
	/** 群id */
	private int groupId;
	/** 设置身份 */
	private int capital;

	public ManagerUserReq(int userId, int groupId, int capital) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.capital = capital;
	}

	public ManagerUserReq() {
		super();
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

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

}

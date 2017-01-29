package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;
import java.util.List;

/**
 * new1.该群中所有的用户id，及对应的身份
 * 
 * @author csuft.chentao
 *
 *         2017年1月18日 下午3:12:38
 */
public class UserIdsInGroupResp implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 对应的群的id
	 */
	private int groupId;
	/**
	 * 该群中所有用户的id，及对应的身份
	 */
	private List<UserCapitalResp> userIdCapitalList;

	public UserIdsInGroupResp(int groupId,
			List<UserCapitalResp> userIdCapitalList) {
		super();
		this.groupId = groupId;
		this.userIdCapitalList = userIdCapitalList;
	}

	public UserIdsInGroupResp() {
		super();
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<UserCapitalResp> getUserIdCapitalList() {
		return userIdCapitalList;
	}

	public void setUserIdCapitalList(List<UserCapitalResp> userIdCapitalList) {
		this.userIdCapitalList = userIdCapitalList;
	}

}

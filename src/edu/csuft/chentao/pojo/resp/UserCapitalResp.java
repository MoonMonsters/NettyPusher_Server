package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 *         2017年1月20日 上午11:06:08
 */
/**
 * 携带了用户id和用户身份信息的类
 * 
 * @author csuft.chentao
 *
 *         2017年1月20日 上午11:07:22
 */
public class UserCapitalResp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 身份标识
	 */
	private int capital;

	public UserCapitalResp(int userId, int capital) {
		this.userId = userId;
		this.capital = capital;
	}

	public UserCapitalResp() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

}

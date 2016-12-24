package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * 登录信息
 * 
 * @author cusft.chentao
 *
 */
public class LoginReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 用户名 */
	String username;
	/** 密码 */
	String password;
	/**
	 * 类型，分成两种，一种是自动登录，一种是重新开始登录 自动登录的话，只返回登录成功标志 重新登录，需要把关于用户的数据返回过去
	 */
	/**
	 * TYPE_AUTOLOGIN 0 
	 * TYPE_RESTARTLOGIN 1
	 */
	int type;

	public LoginReq(String username, String password, int type) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
	}

	public LoginReq() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "LoginReq [username=" + username + ", password=" + password
				+ ", type=" + type + "]";
	}

}

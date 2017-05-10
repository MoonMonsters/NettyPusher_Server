package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用户信息，当加入群时，或者更新群联系人时，服务端存储用户数据并发送到客户端
 * 
 * @author cusft.chentao
 *
 */
public class UserInfoResp implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 类型，是自动登录还是重新登录 */
	private int type;
	/** 用户的id值 */
	private int userid;
	/** 用户昵称 */
	private String nickname;
	/** 头像 */
	private byte[] headImage;
	/** 签名 */
	private String signature;
	/**
	 * 用户名
	 */
	private String username;
	public UserInfoResp(int type, int userid, String nickname,
			byte[] headImage, String signature, String username) {
		super();
		this.type = type;
		this.userid = userid;
		this.nickname = nickname;
		this.headImage = headImage;
		this.signature = signature;
		this.username = username;
	}
	
	public UserInfoResp(){}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public byte[] getHeadImage() {
		return headImage;
	}

	public void setHeadImage(byte[] headImage) {
		this.headImage = headImage;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserInfoResp [type=" + type + ", userid=" + userid
				+ ", nickname=" + nickname + ", headImage="
				+ Arrays.toString(headImage) + ", signature=" + signature
				+ ", username=" + username + "]";
	}
}

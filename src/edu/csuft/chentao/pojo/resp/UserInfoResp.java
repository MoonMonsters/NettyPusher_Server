package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

import edu.csuft.chentao.pojo.req.HeadImage;

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
	private HeadImage headImage;
	/** 签名 */
	private String signature;

	public UserInfoResp(int type, int userid, String nickname,
			HeadImage headImage, String signature) {
		super();
		this.type = type;
		this.userid = userid;
		this.nickname = nickname;
		this.headImage = headImage;
		this.signature = signature;
	}

	public UserInfoResp() {
		super();
	}

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

	public HeadImage getHeadImage() {
		return headImage;
	}

	public void setHeadImage(HeadImage headImage) {
		this.headImage = headImage;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "UserInfoResp [type=" + type + ", userid=" + userid
				+ ", nickname=" + nickname + ", headImage=" + headImage
				+ ", signature=" + signature + "]";
	}

}

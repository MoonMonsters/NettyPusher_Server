package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * 更新用户信息
 * 
 * @author cusft.chentao
 *
 */
public class UpdateUserInfoReq implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 更新信息用户的id值 */
	int userid;
	/**
	 * TYPE_UPDATE_NICKNAME 0 更改用户昵称 
	 * TYPE_UPDATE_SIGNATURE 1 更改用户签名
	 * TYPE_UPDATE_HEADIMAGE 2 更改用户头像
	 */
	int type; // 更新信息的类型
	/** 更新的值，包括了昵称或者签名 */
	String content;
	/** 更新的头像 */
	byte[] headImage;

	public UpdateUserInfoReq(int userid, int type, String content,
			byte[] headImage) {
		super();
		this.userid = userid;
		this.type = type;
		this.content = content;
		this.headImage = headImage;
	}

	public UpdateUserInfoReq() {
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getHeadImage() {
		return headImage;
	}

	public void setHeadImage(byte[] headImage) {
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "UpdateInfoReq [userid=" + userid + ", type=" + type
				+ ", content=" + content + ", headImage=" + headImage + "]";
	}

}

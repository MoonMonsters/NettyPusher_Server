/**
 * 
 */
package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 *         2016年12月10日 上午10:46:01
 */
public class RegisterResp implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 类型 */
	private int type;
	/** 对类型的描述 */
	private String description;
	/**
	 * 用户id
	 */
	private int userid;

	public RegisterResp(int type, String description, int userid) {
		super();
		this.type = type;
		this.description = description;
		this.userid = userid;
	}

	public RegisterResp() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "RegisterResp [type=" + type + ", description=" + description
				+ ", userid=" + userid + "]";
	}

}

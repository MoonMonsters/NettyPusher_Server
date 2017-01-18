/**
 * 
 */
package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 *         2016年12月10日 下午8:51:31
 */
public class CreateGroupResp implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 类型，是否创建成功 */
	private int type;
	/** 对类型的描述 */
	private String description;
	/** 创建成功的群id */
	private int groupid;

	public CreateGroupResp(int type, String description, int groupid) {
		super();
		this.type = type;
		this.description = description;
		this.groupid = groupid;
	}

	public CreateGroupResp() {
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

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	@Override
	public String toString() {
		return "CreateGroupResp [type=" + type + ", description=" + description
				+ ", groupid=" + groupid + "]";
	}

}

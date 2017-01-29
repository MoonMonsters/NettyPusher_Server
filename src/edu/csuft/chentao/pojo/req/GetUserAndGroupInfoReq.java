/**
 * 
 */
package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 * 2017年1月18日 下午3:06:17
 */
/**
 * new1.获得用户信息和群信息的指令
 * 
 * @author csuft.chentao
 *
 *         2017年1月18日 下午3:06:51
 */
public class GetUserAndGroupInfoReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 需要请求的类型，是请求用户信息还是群信息
	 */
	private int type;
	/**
	 * 请求数据的id
	 */
	private int id;

	public GetUserAndGroupInfoReq(int type, int id) {
		super();
		this.type = type;
		this.id = id;
	}

	public GetUserAndGroupInfoReq() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GetUserAndGroupInfoReq [type=" + type + ", id=" + id + "]";
	}

}

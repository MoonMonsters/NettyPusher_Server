/**
 * 
 */
package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 *         2017年2月1日 下午9:55:57
 */
/**
 * 请求数据类
 * 
 * @author csuft.chentao
 *
 *         2017年2月1日 下午9:58:08
 */
public class GetInfoReq implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 请求类型
	 */
	private int type;
	/**
	 * 数据1
	 */
	private int arg1;
	/**
	 * 数据2
	 */
	private int arg2;
	/**
	 * 数据3
	 */
	private Object obj;

	public GetInfoReq(int type, int arg1, int arg2, Object obj) {
		super();
		this.type = type;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.obj = obj;
	}

	public GetInfoReq() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public int getArg2() {
		return arg2;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "GetInfoReq [type=" + type + ", arg1=" + arg1 + ", arg2=" + arg2
				+ ", obj=" + obj + "]";
	}

}

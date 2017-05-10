/**
 * 返回指令，当服务端接收到某些消息，需要返回结果到客户端时，使用该类
 */
package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 *         2016年12月11日 下午8:52:29
 */
public class ReturnInfoResp implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 类型，成功or失败 */
	private int type;
	/**
	 * 参数1
	 */
	private int arg1;
	/**
	 * 参数2
	 */
	private int arg2;
	/**
	 * 参数3
	 */
	private Object obj;

	public ReturnInfoResp(int type, int arg1, int arg2, Object obj) {
		super();
		this.type = type;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.obj = obj;
	}

	public ReturnInfoResp() {
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
		return "ReturnInfoResp [type=" + type + ", arg1=" + arg1 + ", arg2="
				+ arg2 + ", obj=" + obj + "]";
	}

}

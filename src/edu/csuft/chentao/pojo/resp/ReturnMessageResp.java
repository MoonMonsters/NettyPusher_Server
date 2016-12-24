/**
 * 
 */
package edu.csuft.chentao.pojo.resp;

/**
 * @author csuft.chentao
 *
 *         2016年12月11日 下午8:52:29
 */
public class ReturnMessageResp {

	/** 类型，成功or失败 */
	private int type;
	/** 对类型的描述 */
	private String description;

	public ReturnMessageResp(int type, String description) {
		super();
		this.type = type;
		this.description = description;
	}

	public ReturnMessageResp() {
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

}
